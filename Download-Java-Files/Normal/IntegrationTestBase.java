package pl.michal.olszewski.rssaggregator.integration;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import pl.michal.olszewski.rssaggregator.config.Profiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(Profiles.TEST)
public abstract class IntegrationTestBase {

  @LocalServerPort
  public int port;

  final protected WebTestClient webTestClient = WebTestClient
      .bindToServer()
      .baseUrl("http://localhost:" + port)
      .build();
}