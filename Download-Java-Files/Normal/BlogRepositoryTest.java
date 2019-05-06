package pl.michal.olszewski.rssaggregator.blog;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.mongodb.MongoWriteException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import pl.michal.olszewski.rssaggregator.config.Profiles;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@DataMongoTest
@ActiveProfiles(Profiles.TEST)
public class BlogRepositoryTest {

  @Autowired
  protected MongoTemplate entityManager;

  @Autowired
  private BlogReactiveRepository blogRepository;

  @BeforeEach
  void setUp() {
    blogRepository.deleteAll().block();
  }

  @Test
  void shouldFindBlogByBlogURL() {
    //given
    givenBlog()
        .withURL("url");

    //when
    Mono<Blog> byBlogURL = blogRepository.findByFeedURL("url");

    //then
    StepVerifier
        .create(byBlogURL)
        .assertNext(v -> assertThat(v.getBlogURL()).isEqualTo("url"))
        .expectComplete()
        .verify();
  }

  @Test
  void shouldFindBlogById() {
    //given
    Blog blog = givenBlog()
        .withURL("url");

    //when
    Mono<Blog> blogByID = blogRepository.findById(blog.getId());

    //then
    StepVerifier
        .create(blogByID)
        .assertNext(v -> assertThat(v.getBlogURL()).isEqualTo("url"))
        .expectComplete()
        .verify();
  }

  @Test
  void shouldNotFindBlogByBlogURLWhenNotExists() {
    //when
    Mono<Blog> byBlogURL = blogRepository.findByFeedURL("url");

    //then
    StepVerifier
        .create(byBlogURL)
        .expectNextCount(0)
        .expectComplete()
        .verify();
  }

  @Test
  void shouldNotFindBlogByIdWhenNotExists() {
    //when
    Mono<Blog> blogById = blogRepository.findById("1");

    //then
    StepVerifier
        .create(blogById)
        .expectNextCount(0)
        .expectComplete()
        .verify();
  }

  @Test
  void shouldThrownExceptionWhenSave2BlogWithTheSameName() {
    //given
    givenBlog()
        .withURL("url");
    //then
    assertThatThrownBy(() -> entityManager.save(Blog.builder().blogURL("url").build()))
        .hasCauseInstanceOf(MongoWriteException.class)
        .isInstanceOf(DuplicateKeyException.class)
        .hasMessageContaining("duplicate key error collection");
  }

  @Test
  void shouldFindBlogByName() {
    //given
    givenBlog()
        .withName("url");

    //when
    Mono<Blog> byName = blogRepository.findByName("url");

    //then
    StepVerifier
        .create(byName)
        .assertNext(v -> assertThat(v.getName()).isEqualTo("url"))
        .expectComplete()
        .verify();
  }

  @Test
  void shouldNotFindBlogByNameWhenNotExists() {
    //when
    Mono<Blog> byName = blogRepository.findByName("name");

    //then
    StepVerifier
        .create(byName)
        .expectNextCount(0)
        .expectComplete()
        .verify();
  }

  @Test
  void shouldGetAllBlogsIfAllAreActive() {
    givenBlog().buildNumberOfBlogsAndSave(5);
    //when
    Flux<Blog> streamAll = blogRepository.findAll();
    //then
    StepVerifier
        .create(streamAll)
        .expectNextCount(5)
        .expectComplete()
        .verify();
  }

  @Test
  void shouldNotReturnNotActiveBlog() {
    givenBlog().notActive();
    //when
    Flux<Blog> streamAll = blogRepository.findAll();
    //then
    StepVerifier
        .create(streamAll)
        .expectNextCount(0)
        .expectComplete()
        .verify();
  }

  private BlogListFactory givenBlog() {
    return new BlogListFactory(blogRepository, entityManager);
  }

}
