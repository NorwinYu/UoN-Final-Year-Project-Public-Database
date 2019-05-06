package pl.michal.olszewski.rssaggregator.item;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.reactive.function.BodyInserters;
import pl.michal.olszewski.rssaggregator.blog.BlogReactiveRepository;
import pl.michal.olszewski.rssaggregator.integration.IntegrationTestBase;
import reactor.test.StepVerifier;

class ReadItemApiIntegrationTest extends IntegrationTestBase {

  @Autowired
  private BlogReactiveRepository blogRepository;

  @Autowired
  private MongoTemplate mongoTemplate;

  @Autowired
  private ItemRepository itemRepository;

  @BeforeEach
  void setUp() {
    blogRepository.deleteAll().block();
    mongoTemplate.remove(new Query(), "item");
  }

  @Test
  void should_mark_item_as_read() {
    Item link = itemRepository.save(new Item(ItemDTO.builder().link("link").build())).block();

    thenMarkItemAsRead(link.getId());

    StepVerifier.create(itemRepository.findById(link.getId()))
        .assertNext(item -> assertThat(item.isRead()).isTrue())
        .verifyComplete();
  }

  @Test
  void should_mark_item_as_unread() {
    Item item = new Item(ItemDTO.builder().link("link").build());
    item.setRead(true);
    itemRepository.save(item).block();

    thenMarkItemAsUnread(item.getId());

    StepVerifier.create(itemRepository.findById(item.getId()))
        .assertNext(itemFromDB -> assertThat(itemFromDB.isRead()).isFalse())
        .verifyComplete();
  }


  private void thenMarkItemAsRead(String itemID) {
    ReadItemDTO itemDTO = ReadItemDTO.builder().itemId(itemID).read(true).build();
    post(itemDTO);
  }

  private void thenMarkItemAsUnread(String itemID) {
    ReadItemDTO itemDTO = ReadItemDTO.builder().itemId(itemID).read(false).build();
    post(itemDTO);
  }

  private void post(ReadItemDTO itemDTO) {
    webTestClient.post()
        .uri("http://localhost:{port}/api/v1/items/mark", port)
        .body(BodyInserters.fromObject(itemDTO))
        .exchange()
        .expectStatus().isNoContent();
  }

}
