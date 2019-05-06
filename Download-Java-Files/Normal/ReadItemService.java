package pl.michal.olszewski.rssaggregator.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
class ReadItemService {

  private final ItemRepository itemRepository;

  ReadItemService(ItemRepository itemRepository) {
    this.itemRepository = itemRepository;
  }

  private Mono<Boolean> markItemAsRead(Item item) {
    return Mono.fromCallable(item::markAsRead)
        .flatMap(itemRepository::save)
        .map(result -> true)
        .onErrorReturn(false);
  }

  private Mono<Boolean> markItemAsUnread(Item item) {
    return Mono.fromCallable(item::markAsUnread)
        .flatMap(itemRepository::save)
        .map(result -> true)
        .onErrorReturn(false);
  }

  Mono<Boolean> processRequest(ReadItemDTO readItemDTO, String correlationId) {
    return itemRepository.findById(readItemDTO.getItemId())
        .switchIfEmpty(Mono.error(new ItemNotFoundException(readItemDTO.getItemId(), correlationId)))
        .flatMap(item -> {
          if (readItemDTO.isRead()) {
            return markItemAsRead(item);
          }
          return markItemAsUnread(item);
        });
  }
}
