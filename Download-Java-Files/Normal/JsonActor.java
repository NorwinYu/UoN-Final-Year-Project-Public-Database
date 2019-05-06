package wikipedia.json;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import wikipedia.model.WikipediaRevision;
import wikipedia.parser.XMLActor.AddRevisions;
import wikipedia.parser.XMLActor.NewFile;

public class JsonActor extends AbstractActor {
	
	static public Props props(String path) {
		return Props.create(JsonActor.class, () -> new JsonActor(path));
	}
	
	private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

	ObjectMapper mapper = new ObjectMapper();

    int jsonCounter = 0;
	
    PrintWriter out;
    String path;
	
	public JsonActor(String path) {
		this.path = path;
	}
	
	@Override
	public Receive createReceive() {
		return receiveBuilder()
	    		.match(AddRevisions.class, ap -> {
					addWikipediaRevisions(ap.revisions);
//					jsonCounter++;
//					if (jsonCounter % Main.outputFreq == 0) {
//						log.info("Done storing {} pages. Current page id: {}.", jsonCounter, ap.page.getId());
//	            	}
				})
				.match(NewFile.class, nf -> {
					if (out != null)
						out.close();
					try {
						String[] splits = nf.filename.split("/")[5].split("-");
						String last = splits[4];
						String filename = splits[0] + "-" + splits[1] + "-" + last.substring(0, last.length() - 4);  
												
						File f = new File(path + "/json/" + filename + ".json");
						if(!f.exists()) { 
							f.getParentFile().mkdirs();
						}
						
						out = new PrintWriter(new BufferedWriter(new FileWriter(f, true)));
						registerShutdownHook(out);
					} catch (IOException e) {
						e.printStackTrace();
					} 
				})
				.matchAny(o -> log.info("received unknown message"))
				.build();
	}
	
	private void addWikipediaRevisions(Collection<WikipediaRevision> revisions) {
		
		for (WikipediaRevision revision: revisions) {
			try {
				String jsonInString = mapper.writeValueAsString(revision);
				out.println(jsonInString);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		out.flush();
	}


	private void registerShutdownHook( PrintWriter out )	{

	    Runtime.getRuntime().addShutdownHook( new Thread()
	    {
	        @Override
	        public void run(){
	        	if (out != null)
	        		out.close();
	        }
	    } );
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
		if (out != null)
    		out.close();
		log.debug("Stopping");
	}
}
