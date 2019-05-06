package com.kamlesh.poc.jersey;

import java.io.IOException;
import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import com.kamlesh.poc.jersey.di.DependencyResolver;
import com.kamlesh.poc.jersey.di.MyDiscoverableFeature;
import com.kamlesh.poc.jersey.service.TestService;
import lombok.extern.log4j.Log4j2;

/**
 * Main class.
 *
 */
@Log4j2
public class Main extends ResourceConfig {
	// Base URI the Grizzly HTTP server will listen on
	public static final String BASE_URI = "http://0.0.0.0:8080/wds/";
	public static final String PACKAGE_NAME = Main.class.getPackage().getName();
	public static final String SERVICE_LOCATOR_NAME = "__HK2_Generated_0";
	private static ServiceLocator locator;

	/**
	 * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
	 * @return Grizzly HTTP server.
	 * @throws IOException 
	 */
	public static HttpServer startServer() throws IOException {
		// create a resource config that scans for JAX-RS resources and providers
		// in com.kamlesh.poc.jersey package
		final Main main = new Main();
		locator = ServiceLocatorUtilities.createAndPopulateServiceLocator("__HK2_Generated_0");
		System.out.println(locator);
		main.register(MyDiscoverableFeature.class);
		main.register(MultiPartFeature.class);
		main.packages(true, PACKAGE_NAME);
		HttpServer httpServer = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), main, locator);
		httpServer.start();
		// create and start a new instance of grizzly http server
		// exposing the Jersey application at BASE_URI
		return httpServer;
	}

	/**
	 * Main method.
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		final HttpServer server = startServer();
		System.out.println(String.format("Jersey app started with WADL available at " + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
		DependencyResolver dependencyResolver = locator.getService(DependencyResolver.class);
		TestService service = dependencyResolver.getService(TestService.class);
		System.out.println(service.doTask());
		//		server.shutdown();
	}
}
