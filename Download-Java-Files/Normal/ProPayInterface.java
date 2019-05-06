package hhu.ausleihservice.propay;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Component
public class ProPayInterface {

	private String host;
	private static final int PORT = 8888;
	private static final String SCHEME = "http";
	private static final MediaType MIME_TYPE = MediaType.APPLICATION_JSON_UTF8;
	private static final Duration timeout = Duration.ofSeconds(1);

	public ProPayInterface(String host) {
		this.host = host;
	}

	public ProPayInterface() {
		host = "propay";
	}

	public ProPayAccount getAccountInfo(final String proPayName) {
		final Mono<ProPayAccount> mono
				= WebClient
				.create()
				.get()
				.uri(builder -> builder.scheme(SCHEME)
						.host(host)
						.port(PORT)
						.pathSegment("account", proPayName)
						.build())
				.accept(MIME_TYPE)
				.retrieve()
				.bodyToMono(ProPayAccount.class);
		return mono.block();
	}

	public boolean isAvailable() {
		try {
			WebClient.create()
					.get()
					.uri(builder -> builder.scheme(SCHEME)
							.host(host)
							.port(PORT)
							.build())
					.retrieve()
					.bodyToMono(ProPayAccount.class)
					.retryBackoff(3, timeout)
					.block();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public ProPayAccount addFunds(final String proPayName, final double amount) {
		String[] path = {"account", proPayName};
		Map<String, String> query = new HashMap<>();
		query.put("amount", String.valueOf(amount));
		return post(path, query, ProPayAccount.class);
	}

	public void transferFunds(final String sourceAccount, final String targetAccount, final double amount) {
		String[] path = {"account", sourceAccount, "transfer", targetAccount};
		Map<String, String> query = new HashMap<>();
		query.put("amount", String.valueOf(amount));
		post(path, query, String.class);
	}

	public ProPayReservation createReservation(final String account, final String targetAccount, final double amount) {
		String[] path = {"reservation", "reserve", account, targetAccount};
		Map<String, String> query = new HashMap<>();
		query.put("amount", String.valueOf(amount));
		return post(path, query, ProPayReservation.class);
	}

	public ProPayAccount releaseReservation(final long reservationId, final String account) {
		String[] path = {"reservation", "release", account};
		Map<String, String> query = new HashMap<>();
		query.put("reservationId", String.valueOf(reservationId));
		return post(path, query, ProPayAccount.class);
	}

	public ProPayAccount punishReservation(final long reservationId, final String account) throws WebClientException {
		String[] path = {"reservation", "punish", account};
		Map<String, String> query = new HashMap<>();
		query.put("reservationId", String.valueOf(reservationId));
		return post(path, query, ProPayAccount.class);
	}


	private <T> T post(final String[] path, final Map<String, String> query, final Class<T> monoClass) {
		final Mono<T> mono
				= WebClient
				.create()
				.post()
				.uri(genProPayUri(query, path))
				.accept(MIME_TYPE)
				.retrieve()
				.bodyToMono(monoClass);
		return mono.block();
	}

	private URI genProPayUri(final Map<String, String> query, final String[] path) {
		UriBuilder uriBuilder = new DefaultUriBuilderFactory()
				.builder()
				.scheme(SCHEME)
				.host(host)
				.port(PORT)
				.pathSegment(path);
		return setQueryParam(uriBuilder, query).build();
	}

	private UriBuilder setQueryParam(final UriBuilder builder, final Map<String, String> query) {
		for (Map.Entry<String, String> entry : query.entrySet()) {
			builder.queryParam(entry.getKey(), entry.getValue());
		}
		return builder;
	}
}
