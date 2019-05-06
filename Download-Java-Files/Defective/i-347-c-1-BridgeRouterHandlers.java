package life.genny.bridge;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.core.MultiMap;
import io.vertx.rxjava.core.Vertx;
import io.vertx.rxjava.core.eventbus.MessageProducer;
import io.vertx.rxjava.ext.web.RoutingContext;
import io.vertx.rxjava.ext.web.handler.CorsHandler;
import life.genny.channel.Producer;
import life.genny.qwanda.attribute.EntityAttribute;
import life.genny.qwanda.entity.BaseEntity;
import life.genny.qwandautils.GennySettings;
import life.genny.qwandautils.GitUtils;
import life.genny.qwandautils.KeycloakUtils;
import life.genny.qwandautils.QwandaUtils;
import life.genny.security.SecureResources;
import life.genny.utils.RulesUtils;
import life.genny.utils.VertxUtils;

public class BridgeRouterHandlers {

	private static String vertxUrl = System.getenv("REACT_APP_VERTX_URL");

	protected static final Logger log = org.apache.logging.log4j.LogManager
			.getLogger(MethodHandles.lookup().lookupClass().getCanonicalName());

	public static final String GIT_VERSION_PROPERTIES = "GitVersion.properties";

	public static final String PROJECT_DEPENDENCIES = "project_dependencies";

	public static CorsHandler cors() {
		return CorsHandler.create("*").allowedMethod(HttpMethod.GET).allowedMethod(HttpMethod.POST)
				.allowedMethod(HttpMethod.OPTIONS).allowedHeader("X-PINGARUNER").allowedHeader("Content-Type")
				.allowedHeader("X-Requested-With");
	}

	public static void apiGetInitHandler(final RoutingContext routingContext) {
		/*
		 * APP_NAME=Internmatch
		 * GENNY_HOST=https://bridge-internmatch-staging.outcome-hub.com/
		 * GENNY_INITURL=http://internmatch-staging.outcome-hub.com GENNY_BRIDGE_PORT=80
		 * GENNY_BRIDGE_VERTEX=frontend GENNY_BRIDGE_SERVICE=api/service
		 * GENNY_BRIDGE_EVENTS=api/events
		 * GOOGLE_MAPS_APIKEY="AIzaSyBwvr5m9CqFV4nW4AtnxAdT-_w_xOWufRE"
		 * GOOGLE_MAPS_APIURL=https://maps.googleapis.com/maps/api/js
		 * LAYOUT_PUBLICURL=https://layouts.fourdegrees.io/ UPPY_URL=""
		 * KEYCLOAK_REDIRECTURI="" APPCENTER_ANDROID_SECRET="" APPCENTER_IOS_SECRET=""
		 * ANDROID_CODEPUSH_KEY=""
		 * LAYOUT_PUBLICURL=https://layout-cache-staging.outcome-hub.com/
		 * LAYOUT_QUERY_DIRECTORY=layouts/internmatch-new GUEST_USERNAME=guest
		 * GUEST_PASSWORD=asdf1234 SIGNATURE_URL=""
		 */
		routingContext.request().bodyHandler(bodyy -> {
			final String fullurl = routingContext.request().getParam("url");
			String format = routingContext.request().getParam("format");
			if (format == null) {
				format = "json";
			}
			URL aURL = null;
			try {
				aURL = new URL(fullurl);
				final String url = aURL.getHost();
				String key = url + ".json";
				final String keycloakJsonText = SecureResources.getKeycloakJsonMap().get(key);
				if ((keycloakJsonText != null) && ("json".equalsIgnoreCase(format))) {
					final JsonObject retInit = new JsonObject(keycloakJsonText);
					String tokenRealm = retInit.getString("resource");
					String realm = "genny".equals(tokenRealm) ? GennySettings.mainrealm : tokenRealm; // clientId =
																										// realm by
																										// convention
					String serviceToken = RulesUtils.generateServiceToken(tokenRealm);
					retInit.put("vertx_url", vertxUrl);
					retInit.put("api_url", GennySettings.qwandaServiceUrl);
					final String kcUrl = retInit.getString("auth-server-url");
					retInit.put("url", kcUrl);
					final String kcClientId = retInit.getString("resource");
					retInit.put("clientId", kcClientId);
					retInit.put("ENV_GENNY_HOST", fullurl + ":" + GennySettings.apiPort); // The web bfrontend already
																							// knows this url on port
																							// 8088 (It uses it's own
																							// url with a port 8088)
					retInit.put("ENV_GENNY_INITURL", fullurl); // the web frontend knows this url. It passed it to us,
																// but the mobile may not know
					retInit.put("ENV_GENNY_BRIDGE_PORT", GennySettings.apiPort);
					retInit.put("ENV_GENNY_BRIDGE_VERTEX", "/frontend");
					retInit.put("ENV_GENNY_BRIDGE_SERVICE", "/api/service");
					retInit.put("ENV_GENNY_BRIDGE_EVENTS", "/api/events");

					retInit.put("ENV_GOOGLE_MAPS_APIKEY", fetchSetting(realm,"ENV_GOOGLE_MAPS_APIKEY",serviceToken,"NO_GOOGLE_MAPS_APIKEY"));
					retInit.put("ENV_GOOGLE_MAPS_APIURL", fetchSetting(realm,"ENV_GOOGLE_MAPS_APIURL",serviceToken,"NO_GOOGLE_MAPS_APIURL"));
					retInit.put("ENV_UPPY_URL", fetchSetting(realm,"ENV_UPPY_URL",serviceToken,"http://uppy.genny.life")); 
					retInit.put("ENV_KEYCLOAK_REDIRECTURI", kcUrl); 
					retInit.put("ENV_APPCENTER_ANDROID_SECRET", fetchSetting(realm,"ENV_APPCENTER_ANDROID_SECRET",serviceToken,"NO_APPCENTER_ANDROID_SECRET")); 
					retInit.put("ENV_APPCENTER_IOS_SECRET", fetchSetting(realm,"ENV_APPCENTER_IOS_SECRET",serviceToken,"NO_APPCENTER_IOS_SECRET")); 
					retInit.put("ENV_ANDROID_CODEPUSH_KEY", fetchSetting(realm,"ENV_ANDROID_CODEPUSH_KEY",serviceToken,"NO_ANDROID_CODEPUSH_KEY")); 
					retInit.put("ENV_LAYOUT_PUBLICURL", fetchSetting(realm,"ENV_LAYOUT_PUBLICURL",serviceToken,"http://layout-cache.genny.life")); 
					retInit.put("ENV_GUEST_USERNAME", fetchSetting(realm,"ENV_GUEST_USERNAME",serviceToken,"guest"));
					retInit.put("ENV_GUEST_PASSWORD", fetchSetting(realm,"ENV_GUEST_PASSWORD",serviceToken,"asdf1234"));
					retInit.put("ENV_SIGNATURE_URL", fetchSetting(realm,"ENV_SIGNATURE_URL",serviceToken,"http://signature.genny.life"));
					retInit.put("ENV_USE_CUSTOM_AUTH_LAYOUTS", fetchSetting(realm,"ENV_USE_CUSTOM_AUTH_LAYOUTS",serviceToken,"FALSE"));
					
					// To handle a quirky layout directory setting that is in format <realm>-new we hack this bit...
					String layout_query_dir = fetchSetting(realm,"ENV_LAYOUT_QUERY_DIRECTORY",serviceToken,"NO_LAYOUT_QUERY_DIRECTORY");
					String devrealm = System.getenv("PROJECT_REALM");
					if (devrealm==null) {
						devrealm = realm;
					}
					layout_query_dir = layout_query_dir.replaceAll("genny", devrealm.toLowerCase().trim());
					retInit.put("ENV_LAYOUT_QUERY_DIRECTORY", layout_query_dir);

					log.info("WEB API GET    >> SETUP REQ:" + url + " sending : " + kcUrl + " " + kcClientId);
					routingContext.response().putHeader("Content-Type", "application/json");
					routingContext.response().end(retInit.toString());
				} else if ((keycloakJsonText != null) && ("env".equalsIgnoreCase(format))) {
					final JsonObject retInit = new JsonObject(keycloakJsonText);
					String env = "";
					String tokenRealm = retInit.getString("resource");
					String realm = "genny".equals(tokenRealm) ? GennySettings.mainrealm : tokenRealm; // clientId =
																										// realm by
																										// convention
					String serviceToken = RulesUtils.generateServiceToken(tokenRealm);
					env = "realm=" + realm + "\n";
					env += "vertx_url=" + vertxUrl + "\n";
					env += "api_url=" + GennySettings.qwandaServiceUrl + "\n";
					final String kcUrl = retInit.getString("auth-server-url");
					env += "url=" + kcUrl + "\n";
					final String kcClientId = retInit.getString("resource");
					env += "clientId=" + kcClientId + "\n";
					env += "ENV_GENNY_HOST=" + fullurl + ":" + GennySettings.apiPort + "\n"; // The web bfrontend
																								// already knows this
																								// url on port 8088 (It
																								// uses it's own url
																								// with a port 8088)
					env += "ENV_GENNY_INITURL=" + fullurl + "\n"; // the web frontend knows this url. It passed it to
																	// us, but the mobile may not know
					env += "ENV_GENNY_BRIDGE_PORT=" + GennySettings.apiPort + "\n";
					env += "ENV_GENNY_BRIDGE_VERTEX=" + "/frontend" + "\n";
					env += "ENV_GENNY_BRIDGE_SERVICE=" + "/api/service" + "\n";
					env += "ENV_GENNY_BRIDGE_EVENTS=" + "/api/events" + "\n";
					env += "ENV_GOOGLE_MAPS_APIKEY="
							+ fetchSetting(realm, "ENV_GOOGLE_MAPS_APIKEY", serviceToken, "NO_GOOGLE_MAPS_APIKEY")
							+ "\n";
					env += "ENV_GOOGLE_MAPS_APIURL="
							+ fetchSetting(realm, "ENV_GOOGLE_MAPS_APIURL", serviceToken, "NO_GOOGLE_MAPS_APIURL")
							+ "\n";
					env += "ENV_UPPY_URL=" + fetchSetting(realm, "ENV_UPPY_URL", serviceToken, "http://uppy.genny.life")
							+ "\n";
					env += "ENV_KEYCLOAK_REDIRECTURI=" + kcUrl + "\n";
					env += "ENV_APPCENTER_ANDROID_SECRET=" + fetchSetting(realm, "ENV_APPCENTER_ANDROID_SECRET",
							serviceToken, "NO_APPCENTER_ANDROID_SECRET") + "\n";
					env += "ENV_APPCENTER_IOS_SECRET="
							+ fetchSetting(realm, "ENV_APPCENTER_IOS_SECRET", serviceToken, "NO_APPCENTER_IOS_SECRET")
							+ "\n";
					env += "ENV_ANDROID_CODEPUSH_KEY="
							+ fetchSetting(realm, "ENV_ANDROID_CODEPUSH_KEY", serviceToken, "NO_ANDROID_CODEPUSH_KEY")
							+ "\n";
					env += "ENV_LAYOUT_PUBLICURL=" + fetchSetting(realm, "ENV_LAYOUT_PUBLICURL", serviceToken,
							"http://layout-cache.genny.life:2224") + "\n";
					env += "ENV_LAYOUT_QUERY_DIRECTORY=" + fetchSetting(realm, "ENV_LAYOUT_QUERY_DIRECTORY",
							serviceToken, "NO_LAYOUT_QUERY_DIRECTORY") + "\n";
					env += "ENV_GUEST_USERNAME=" + fetchSetting(realm, "ENV_GUEST_USERNAME", serviceToken, "guest")
							+ "\n";
					env += "ENV_GUEST_PASSWORD=" + fetchSetting(realm, "ENV_GUEST_PASSWORD", serviceToken, "asdf1234")
							+ "\n";
					env += "ENV_SIGNATURE_URL="
							+ fetchSetting(realm, "ENV_SIGNATURE_URL", serviceToken, "http://signature.genny.life")
							+ "\n";
					env += "ENV_USE_CUSTOM_AUTH_LAYOUTS="
							+ fetchSetting(realm, "ENV_USE_CUSTOM_AUTH_LAYOUTS", serviceToken, "FALSE") + "\n";

					log.info("WEB API GET ENV   >> SETUP REQ:" + url + " sending : " + kcUrl + " " + kcClientId);
					routingContext.response().putHeader("Content-Type", "text/plain");
					routingContext.response().end(env);
				} else {

					log.error(key + " NOT FOUND IN KEYCLOAK-JSON-MAP");


					// Treat Inbound api call as a WEB SITE!!

					final JsonObject retInit = new JsonObject();
					retInit.put("realm", "www");
					retInit.put("vertx_url", vertxUrl);
					log.info("WEB API GETWWW >> SETUP REQ:" + url + " sending : WWW");
					routingContext.response().putHeader("Content-Type", "application/json");
					routingContext.response().end(retInit.toString());
				}
			} catch (final MalformedURLException e) {
				routingContext.response().end();
			}
			;
		});
	}

	private static String fetchSetting(final String realm, final String key, final String serviceToken,
			final String defaultValue) {
		String project_code = "PRJ_" + realm.toUpperCase();
		String retValue = null;

		// First look at the system env
		retValue = System.getenv(realm.toUpperCase() + "_" + key.toUpperCase());

		// else look at the project setting
		if (retValue == null) {
			BaseEntity project = VertxUtils.readFromDDT(realm, project_code, serviceToken);
			if (project == null) {
				log.error("Error: no Project Setting for " + key + " , ensure PRJ_" + realm.toUpperCase()
						+ " has entityAttribute value for " + key.toUpperCase());
				return defaultValue;
			}
			Optional<EntityAttribute> entityAttribute = project.findEntityAttribute(key.toUpperCase());
			if (entityAttribute.isPresent()) {

				retValue = entityAttribute.get().getValueString();
				if (retValue == null) {
					log.error(realm + " Bridge has " + key + " which is returning null so returning " + defaultValue);
					return defaultValue;
				} else {
					return retValue;
				}

			} else {
				log.error("Error: no Project Setting for " + key + " , ensure PRJ_" + realm.toUpperCase()
						+ " has entityAttribute value for ENV_" + key.toUpperCase() + " returning default:"
						+ defaultValue);
				return defaultValue;
			}
		} else {
			return retValue;
		}

	}

	public static void apiInitHandler(final RoutingContext routingContext) {

		routingContext.request().bodyHandler(body -> {
			final String bodyString = body.toString();
			final JsonObject j = new JsonObject(bodyString);
			log.info("WEB API POST   >> SESSION_INIT:");
			// + j.getJsonObject("headers").getString("Authorization").split("Bearer ")[1]);
			String tokenSt = j.getJsonObject("headers").getString("Authorization").split("Bearer ")[1];
			JSONObject tokenJSON = KeycloakUtils.getDecodedToken(tokenSt);
			String sessionState = tokenJSON.getString("session_state");
			String uname = QwandaUtils.getNormalisedUsername(tokenJSON.getString("preferred_username"));
			String userCode = "PER_" + uname.toUpperCase();

			Set<String> sessionStates = VertxUtils.getSetString("", "SessionStates", userCode);
			sessionStates.add(sessionState);
			VertxUtils.putSetString("", "SessionStates", userCode, sessionStates);
			final MessageProducer<JsonObject> toSessionChannel = Vertx.currentContext().owner().eventBus()
					.publisher(sessionState);
			VertxUtils.putMessageProducer(sessionState, toSessionChannel);
			routingContext.response().end();

		});
	}

	public static void apiServiceHandler(final RoutingContext routingContext) {
		String token = routingContext.request().getParam("token");
		String channel = routingContext.request().getParam("channel");
		routingContext.request().bodyHandler(body -> {
			String localToken = null;
			final JsonObject j = body.toJsonObject();
			if (token == null) {
				MultiMap headerMap = routingContext.request().headers();
				localToken = headerMap.get("Authorization");
				if (localToken == null) {
					log.error("NULL TOKEN!");
				} else {
					localToken = localToken.substring(7); // To remove initial [Bearer ]
				}
			} else {
				localToken = token;
			}
			// j.put("token", token);

			log.info("Incoming Service:" + j);
			final DeliveryOptions options = new DeliveryOptions();
			options.addHeader("Authorization", "Bearer " + localToken);


			if (j.getString("msg_type").equals("EVT_MSG") || "events".equals(channel) || "event".equals(channel)) {
				log.info("EVT API POST   >> EVENT-BUS EVENT:");
				j.put("token", localToken);
				Producer.getToEvents().deliveryOptions(options);
				Producer.getToEvents().send(j);

			} else if (j.getString("msg_type").equals("CMD_MSG") && "webcmd".equals(channel)) {
				log.info("WEBCMD API POST   >> WEB CMDS :" + j);
				j.put("token", localToken);
				//Producer.getToWebCmds().deliveryOptions(options);
				//Producer.getToWebCmds().send(j);
				EBCHandlers.sendToClientSessions(j.toString(), false);
			} else if (j.getString("msg_type").equals("CMD_MSG") || "cmds".equals(channel)) {
				log.info("CMD API POST   >> EVENT-BUS CMD  :" + j);

				j.put("token", localToken);
				Producer.getToCmds().deliveryOptions(options);
				Producer.getToCmds().send(j);
			} else if (j.getString("msg_type").equals("MSG_MESSAGE") || "messages".equals(channel)) {
				log.info("MESSAGES API POST   >> EVENT-BUS MSG DATA :");
				j.put("token", localToken);
				Producer.getToMessages().deliveryOptions(options);
				Producer.getToMessages().send(j);

			} else if ("webdata".equals(channel)) {
				log.info("WEBDATA API POST   >> EVENT-BUS DATA :" + j);

				j.put("token", localToken);
				Producer.getToWebData().deliveryOptions(options);
				Producer.getToWebData().send(j);
			} else if (j.getString("msg_type").equals("DATA_MSG") || "data".equals(channel)) {
				log.info("CMD API POST   >> EVENT-BUS DATA :");
				j.put("token", localToken);
				Producer.getToData().deliveryOptions(options);
				Producer.getToData().send(j);
			}

		});
		routingContext.response().end();
	}

	public static void apiHandler(final RoutingContext routingContext) {
		routingContext.request().bodyHandler(body -> {
			if (body.toJsonObject().getString("msg_type").equals("CMD_MSG"))
				log.info("EVENT-BUS CMD  >> WEBSOCKET CMD :" );
			Producer.getToClientOutbound().send(body.toJsonObject());
			if (body.toJsonObject().getString("msg_type").equals("DATA_MSG"))
				log.info("EVENT-BUS DATA >> WEBSOCKET DATA:" );
			Producer.getToData().send(body.toJsonObject());
		});
		routingContext.response().end();
	}

	public static void apiGetVersionHandler(final RoutingContext routingContext) {
		routingContext.request().bodyHandler(body -> {
			Properties properties = new Properties();
			String versionString = "";
			try {
				properties.load(Thread.currentThread().getContextClassLoader().getResource(GIT_VERSION_PROPERTIES)
						.openStream());
				String projectDependencies = properties.getProperty(PROJECT_DEPENDENCIES);
				versionString = GitUtils.getGitVersionString(projectDependencies);
			} catch (IOException e) {
				log.error("Error reading GitVersion.properties", e);
			}
			routingContext.response().putHeader("Content-Type", "application/json");
			routingContext.response().end(versionString);
		});

	}
}
