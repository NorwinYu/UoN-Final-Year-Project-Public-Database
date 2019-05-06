package wikipedia;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import akka.actor.ActorSystem;

import wikipedia.neo4j.Neo4jActor;

public class StartNeo4j  {
	
    public static void main(String[] args) {
    	
    	Config regularConfig = ConfigFactory.load();
        String path = regularConfig.getString("wikipedia.path");
    	
        System.setProperty("wikipedia.path", path);
        
        final ActorSystem system = ActorSystem.create("Neo4jStandalone");
    	system.actorOf(Neo4jActor.props(path), "Neo4jActor");

    }
        
}

