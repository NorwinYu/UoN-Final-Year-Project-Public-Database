package pl.michal.olszewski.rssaggregator.item;

import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController()
@Slf4j
class ReadItemEndpoint {

  private final ReadItemService readItemService;

  ReadItemEndpoint(ReadItemService readItemService) {
    this.readItemService = readItemService;
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, value = "/api/v1/items/mark")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Boolean> markItem(@RequestBody ReadItemDTO readItemDTO) {
    String correlationId = UUID.randomUUID().toString();
    log.debug("START markItem id {} read {} correlationId {}", readItemDTO.getItemId(), readItemDTO.isRead(), correlationId);
    return readItemService.processRequest(readItemDTO, correlationId)
        .doOnSuccess(result -> log.debug("END markItem {} result {} - correlationId {}", readItemDTO.getItemId(), result, correlationId))
        .doOnError(error -> log.error("ERROR markItem {} - correlationId {}", readItemDTO.getItemId(), correlationId, error));
  }
}
