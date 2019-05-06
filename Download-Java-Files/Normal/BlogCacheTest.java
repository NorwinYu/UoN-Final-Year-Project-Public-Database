package pl.michal.olszewski.rssaggregator.blog;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import pl.michal.olszewski.rssaggregator.integration.IntegrationTestBase;
import reactor.core.publisher.Flux;

class BlogCacheTest extends IntegrationTestBase {

  private Blog blog;
  @Autowired
  private BlogService service;
  @Autowired
  private MongoTemplate mongoTemplate;

  @BeforeEach
  void setUp() {
    mongoTemplate.dropCollection(Blog.class);
    blog = mongoTemplate.save(Blog.builder().blogURL("nazwa").feedURL("nazwa").name("nazwa").build());
    service.evictBlogCache();
  }

  @Test
  void shouldReturnTheSameCollectionFromCache() {
    //given
    Flux<BlogAggregationDTO> allBlogs = service.getAllBlogDTOs("correlationId");
    List<BlogAggregationDTO> blogList = allBlogs.collectList().block();

    //when
    Flux<BlogAggregationDTO> cacheBlogs = service.getAllBlogDTOs("correlationId");

    //then
    assertThat(blogList).hasSameElementsAs(cacheBlogs.toIterable());
  }

  @Test
  void shouldAfterEvictCacheAndReturnNotTheSameCollection() {
    //given
    Flux<BlogAggregationDTO> allBlogs = service.getAllBlogDTOs("correlationId");
    List<BlogAggregationDTO> blogList = allBlogs.collectList().block();

    //when
    service.evictBlogCache();
    Flux<BlogAggregationDTO> blogs = service.getAllBlogDTOs("correlationId");

    //then
    assertNotSame(allBlogs, blogs);
    assertThat(blogList).containsExactlyElementsOf(blogs.toIterable());
  }

  @Test
  void shouldAfterCreateNewBlogAndReturnNotTheSameCollection() {
    //given
    Flux<BlogAggregationDTO> allBlogs = service.getAllBlogDTOs("correlationId");
    List<BlogAggregationDTO> blogList = allBlogs.collectList().block();

    //when
    service.getBlogOrCreate(BlogDTO.builder().name("nazwa2").build(), "correlationId").block();
    Flux<BlogAggregationDTO> blogs = service.getAllBlogDTOs("correlationId");

    //then
    assertNotSame(allBlogs, blogs);
    assertThat(blogList).isNotEqualTo(blogs.toIterable());
  }

  @Test
  void shouldAfterDeleteNewBlogAndReturnNotTheSameCollection() {
    //given
    Flux<BlogAggregationDTO> allBlogs = service.getAllBlogDTOs("correlationId");
    List<BlogAggregationDTO> blogList = allBlogs.collectList().block();

    //when
    service.deleteBlog(blog.getId(), "correlationID").block();
    Flux<BlogAggregationDTO> blogs = service.getAllBlogDTOs("correlationId");

    //then
    assertNotSame(allBlogs, blogs);
    assertThat(blogList).isNotEqualTo(blogs.toIterable());
  }

}
