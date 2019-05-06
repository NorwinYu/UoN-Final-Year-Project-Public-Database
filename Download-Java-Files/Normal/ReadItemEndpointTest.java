package pl.michal.olszewski.rssaggregator.item;

import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

@WebFluxTest(ReadItemEndpoint.class)
class ReadItemEndpointTest {

  @MockBean
  private ReadItemService readItemService;

  @Autowired
  private WebTestClient webClient;

  @Test
  void shouldReturnNoContentWhenMarkItemSuccess() {
    var readItemDTO = ReadItemDTO.builder().itemId("itemId").read(true).build();

    given(readItemService.processRequest(Mockito.eq(readItemDTO), Mockito.anyString())).willReturn(Mono.just(true));

    webClient.post().uri("/api/v1/items/mark")
        .contentType(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromObject(readItemDTO))
        .exchange()
        .expectStatus()
        .isNoContent();
  }

  @Test
  void shouldReturnNotFoundWhenMarkItemThatNotExists() {
    var readItemDTO = ReadItemDTO.builder().itemId("itemId2").read(true).build();

    given(readItemService.processRequest(Mockito.eq(readItemDTO), Mockito.anyString())).willReturn(Mono.error(new ItemNotFoundException("", "")));

    webClient.post().uri("/api/v1/items/mark")
        .contentType(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromObject(readItemDTO))
        .exchange()
        .expectStatus()
        .isNotFound();
  }


}
