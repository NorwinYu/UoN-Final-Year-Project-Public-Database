package wikipedia;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class DownloadActor extends AbstractActor {
	
	static public Props props(ActorRef xmlManager, String path, int lastPageId) {
		return Props.create(DownloadActor.class, () -> new DownloadActor(xmlManager, path, lastPageId));
	}
	
	// START: messages
	
	static public class LoadURL {
		public final String urlString;

	    public LoadURL(String urlString) {
	        this.urlString = urlString;
	    }
	}
	
	static public class LoadFile {
		public final String fileName;

	    public LoadFile(String fileName) {
	        this.fileName = fileName;
	    }
	}
	// END: messages

	
	private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);
	private String path;
	int lastPageId;
	
	private ActorRef xmlManager;
	
	public DownloadActor(ActorRef xmlManager, String path,  int lastPageId) {
		this.xmlManager = xmlManager;
		this.path = path + "/original/";
		this.lastPageId = lastPageId;
	}
	
	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(LoadURL.class, load -> {
					String fileName = path + load.urlString.split("/")[5];
					OutputStream outStream;
				    File f = new File(fileName);
				    
				    String[] splits = fileName.split("p");
					String lastPageString = splits[splits.length-1];
					int lastPage = Integer.parseInt(lastPageString.substring(0, lastPageString.length() - 4));
					
					if(!f.exists() && lastPage > lastPageId) {  
			    		f.getParentFile().mkdirs();
			    		f.createNewFile();
			    	
					    outStream = new FileOutputStream(f);
					    InputStream inStream = new URL(load.urlString).openStream();
					    byte[] buffer = new byte[8 * 1024];
						int bytesRead;
						while ((bytesRead = inStream.read(buffer)) != -1) {
							outStream.write(buffer, 0, bytesRead);
						}
						outStream.close();
						inStream.close();
			    	}
					
//					log.debug(fileName);
					xmlManager.tell(new LoadFile(fileName), self());
				})
				.matchAny(o -> log.info("received unknown message"))
				.build();
	}

}
