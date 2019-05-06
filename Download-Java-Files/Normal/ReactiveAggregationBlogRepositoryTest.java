package pl.michal.olszewski.rssaggregator.blog;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import pl.michal.olszewski.rssaggregator.integration.IntegrationTestBase;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class ReactiveAggregationBlogRepositoryTest extends IntegrationTestBase {

  @Autowired
  private MongoTemplate mongoTemplate;

  @Autowired
  private BlogReactiveRepository blogRepository;

  @BeforeEach
  void setUp() {
    blogRepository.deleteAll().block();
  }

  @Test
  void shouldAggregateBlogPosts() {
    givenBlog().buildBlogWithItemsAndSave(2);

    Flux<BlogAggregationDTO> blogsWithCount = blogRepository.getBlogsWithCount();

    StepVerifier.create(blogsWithCount)
        .assertNext(v -> assertThat(v.getBlogItemsCount()).isEqualTo(2))
        .verifyComplete();
  }

  @Test
  void shouldAggregateBlogPosts2() {
    givenBlog().buildBlogWithItemsAndSave(0);

    Flux<BlogAggregationDTO> blogsWithCount = blogRepository.getBlogsWithCount();

    StepVerifier.create(blogsWithCount)
        .assertNext(v -> assertThat(v.getBlogItemsCount()).isEqualTo(0))
        .verifyComplete();
  }

  private BlogListFactory givenBlog() {
    return new BlogListFactory(blogRepository, mongoTemplate);
  }

}
