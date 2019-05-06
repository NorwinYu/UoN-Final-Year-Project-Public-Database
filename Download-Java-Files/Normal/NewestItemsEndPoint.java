package pl.michal.olszewski.rssaggregator.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/v1/items")
@Slf4j
class NewestItemsEndPoint {

  private final NewestItemService itemService;

  public NewestItemsEndPoint(NewestItemService itemService) {
    this.itemService = itemService;
  }

  @GetMapping
  public Flux<ItemDTO> getItemsOrderByPublishedDate(@RequestParam(value = "limit", required = false) Integer limit) {
    log.debug("GET ItemsOrderByPublishedDate with limit {}", limit);
    return itemService.getNewestItemsOrderByPublishedDate(limit == null ? 10 : limit);
  }

  @GetMapping("/createdAt")
  public Flux<ItemDTO> getItemsOrderByCreatedAt(@RequestParam(value = "limit", required = false) Integer limit) {
    log.debug("GET ItemsOrderByCreatedAt with limit {}", limit);
    return itemService.getNewestItemsOrderByCreatedAt(limit == null ? 10 : limit);
  }
}
