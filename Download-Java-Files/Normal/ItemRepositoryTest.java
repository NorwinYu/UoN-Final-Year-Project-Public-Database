package pl.michal.olszewski.rssaggregator.item;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.mongodb.MongoWriteException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ActiveProfiles;
import pl.michal.olszewski.rssaggregator.blog.Blog;
import pl.michal.olszewski.rssaggregator.config.Profiles;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@DataMongoTest
@ActiveProfiles(Profiles.TEST)
@EnableMongoAuditing
public class ItemRepositoryTest {

  @Autowired
  protected MongoTemplate mongoTemplate;

  @Autowired
  private ItemRepository itemRepository;

  @BeforeEach
  void setUp() {
    mongoTemplate.remove(new Query(), "item");
    mongoTemplate.remove(new Query(), "blog");
  }

  //TODO za dlugie given
  @Test
  void shouldFind2NewestPublishedItems() {
    //given
    Instant instant = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    Item title3 = new Item(ItemDTO.builder().link("title3").date(instant.plusSeconds(10)).build());
    Item title1 = new Item(ItemDTO.builder().link("title1").date(instant).build());
    Blog blog = Blog.builder().blogURL("url")
        .item(mongoTemplate.save(title1))
        .item(mongoTemplate.save(title3))
        .item(mongoTemplate.save(new Item(ItemDTO.builder().link("title2").date(instant.minusSeconds(10)).build())))
        .build();
    mongoTemplate.save(blog);
    //when
    Flux<Item> items = itemRepository.findAllOrderByPublishedDate(2);

    //then
    StepVerifier.create(items)
        .expectNext(title3)
        .expectNext(title1)
        .expectComplete()
        .verify();
  }

  @Test
  void shouldFind2NewestPersistedItems() {
    //given
    mongoTemplate.save(new Item(ItemDTO.builder().link("title2").build()));
    Item title1 = mongoTemplate.save(new Item(ItemDTO.builder().link("title1").build()));
    Item title3 = mongoTemplate.save(new Item(ItemDTO.builder().link("title3").build()));

    //when
    Flux<Item> items = itemRepository.findAllOrderByCreatedAt(2);

    //then
    StepVerifier.create(items)
        .expectNext(title3)
        .expectNext(title1)
        .expectComplete()
        .verify();
  }

  @Test
  void shouldFindItemsWhenDateIsNull() {
    //given
    Blog blog = Blog.builder()
        .blogURL("url")
        .item(mongoTemplate.save(new Item(ItemDTO.builder().link("title1").build())))
        .item(mongoTemplate.save(new Item(ItemDTO.builder().link("title2").build())))
        .item(mongoTemplate.save(new Item(ItemDTO.builder().link("title3").build())))
        .build();
    mongoTemplate.save(blog);

    //when
    Flux<Item> items = itemRepository.findAllOrderByPublishedDate(2);
    //then
    StepVerifier.create(items)
        .expectNextCount(2)
        .expectComplete()
        .verify();
  }

  @Test
  void shouldNotCreateItemByUniqueConstraint() { //TODO zapiac sie jakas fabryka
    //given
    mongoTemplate.save(new Item(ItemDTO.builder().link("title1").build()));

    //expect
    assertThatThrownBy(() -> mongoTemplate.save(new Item(ItemDTO.builder().link("title1").build())))
        .hasMessageContaining("duplicate key error collection")
        .hasCauseInstanceOf(MongoWriteException.class);
  }

  @Test
  void shouldSetCreatedDateOnPersistNewItem() {
    Item item = new Item(ItemDTO.builder().link("title1").build());
    Item save = mongoTemplate.save(item);

    assertThat(save.getCreatedAt()).isNotNull();
  }
}
