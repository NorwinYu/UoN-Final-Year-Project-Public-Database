package wikipedia.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.xml.sax.SAXException;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.actor.ActorRef;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import wikipedia.model.WikipediaPage;
import wikipedia.model.WikipediaRevision;
import wikipedia.DownloadActor.LoadFile;

/**
 * using: https://stackoverflow.com/questions/26310595/how-to-parse-big-50-gb-xml-files-in-java
 * 
 * @author zschache
 *
 */
public class XMLActor extends AbstractActor {
	
	static public Props props(ActorRef neo4jActor, ActorRef jsonActor, int lastPageId) {
		return Props.create(XMLActor.class, () -> new XMLActor(neo4jActor, jsonActor, lastPageId));
	}
		
	// START: messages
	
	static public class ParseNextFile {
		
		public final String fileString;
		
	    public ParseNextFile() {
	    	this.fileString = null;
	    }
	    public ParseNextFile(String fileString) {
	    	this.fileString = fileString;
	    }
	}
	
	static public class AddRevisions {
		public final WikipediaPage page;
	    public final Collection<WikipediaRevision> revisions;

	    public AddRevisions(WikipediaPage page, Collection<WikipediaRevision> revisions) {
	        this.page = page;
	        this.revisions = revisions;
	    }
	}
	
	static public class NewFile {
	    public final String filename;

	    public NewFile(String filename) {
	        this.filename = filename;
	    }
	}

	// END: messages
	
	private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);
	private final SAXParserFactory factory = SAXParserFactory.newInstance();
	
//	int xmlCounter = 0;
	int lastPageId;
	
	boolean waitingForFirstFile = true;
    private Queue<String> fileStrings = new ArrayDeque<String>();
    
    ActorRef jsonActor;
    ActorRef neo4jActor;
    
    // this list stores pages that have been skipped during parsing
    // a page is skipped if the revisions are out of order
    private List<String> skippedPages = new ArrayList<String>();
    
	public XMLActor(ActorRef neo4jActor, ActorRef jsonActor, int lastPageId) {
		this.jsonActor = jsonActor;
		this.neo4jActor = neo4jActor;
		this.lastPageId = lastPageId;
	}
	
	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(LoadFile.class, load -> {
					fileStrings.add(load.fileName);
					if (waitingForFirstFile) {
						waitingForFirstFile = false;
						self().tell(new ParseNextFile(), self());
					}
						
				})
				.match(ParseNextFile.class, load -> {
					String fileString = load.fileString;
					if (fileString == null) { //not parsing the same file again
						fileString = fileStrings.poll();
					}
//					log.debug(fileString);
					final String fileStringFinal = fileString;
					if (fileString != null) {
						String[] splits = fileString.split("p");
						String lastPageString = splits[splits.length-1];
						int lastPage = Integer.parseInt(lastPageString.substring(0, lastPageString.length() - 4));
						int firstPage = Integer.parseInt(splits[splits.length-2]);
						if (lastPage > lastPageId) {
							log.info("Start loading pages {} until {} from: {}", firstPage, lastPage, fileString);
							jsonActor.tell(new NewFile(fileString), self());
							InputStream stream;
							try {
					        	SAXParser parser = factory.newSAXParser();
								stream = new FileInputStream(new File(fileString));
								BZip2CompressorInputStream bzip2 = new BZip2CompressorInputStream(stream);
								PageHandler pageHandler = new PageHandler(skippedPages, new PageProcessor() {
									PageManager pageManger;
									@Override
						            public void startPage(WikipediaPage page, boolean orderByDate) {
//										if (Integer.parseInt(page.getId()) > lastPageId)
//											log.info("Starting page " + page.getTitle());
										pageManger = new PageManager(neo4jActor, jsonActor, getContext());
									}
						            @Override
						            public void process(WikipediaRevision revision) {
//						            	if (Integer.parseInt(revision.getPage().getId()) > lastPageId)
//						            		log.info("Add revision " + revision.getTimestamp());
						            	pageManger.addRevision(revision);
						            }
						            @Override
						            public void endPage(WikipediaPage page) {
						            	if (Integer.parseInt(page.getId()) > lastPageId)
						            		pageManger.addPage(page);
//						            	xmlCounter++;
//										if (xmlCounter % Main.outputFreq == 0) {
//											log.info("Done parsing {} pages. Current page id: {}.", xmlCounter, page.getId());
//											if (!skippedPages.isEmpty())
//												log.warning("Skipped {} pages due to disorder.", skippedPages.size());
//						            	}
						            }
						            @Override
						            public void endDocument () {
						            	try {
											bzip2.close();
										} catch (IOException e) {
											e.printStackTrace();
										}
						            	if (skippedPages.isEmpty())
						            		self().tell(new ParseNextFile(), self());
						            	else {
						            		log.info("Starting another parsing of pages {} until {} from: {}", firstPage, lastPage, fileStringFinal);
						            		self().tell(new ParseNextFile(fileStringFinal), self());
						            	}
						            }
						        });
								parser.parse(bzip2, pageHandler);
							} catch (SAXException | IOException | ParserConfigurationException e) {
								e.printStackTrace();
							} finally {
								
							}
						} else { // lastPage <= lastPageId
							if (!fileStrings.isEmpty())
								self().tell(new ParseNextFile(), self());
							else
								waitingForFirstFile = true;
						}
					} else { // queue of fileStrings is empty
						jsonActor.tell(akka.actor.PoisonPill.getInstance(), ActorRef.noSender());
						neo4jActor.tell(akka.actor.PoisonPill.getInstance(), ActorRef.noSender());
						getContext().stop(getSelf());
					}
				})
				.matchAny(o -> log.info("received unknown message"))
				.build();
	}
	
	@Override
	public void preStart() {
		log.debug("Starting");
	}
	@Override
	public void preRestart(Throwable reason, Optional<Object> message) {
		log.error(reason, "Restarting due to [{}] when processing [{}]",
				reason.getMessage(), message.isPresent() ? message.get() : "");
	}
	
	public void postStop() {
		log.debug("Stopping");
	}
        
}