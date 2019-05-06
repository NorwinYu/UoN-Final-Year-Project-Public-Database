package pl.michal.olszewski.rssaggregator.blog;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.web.reactive.server.WebTestClient.BodySpec;
import org.springframework.test.web.reactive.server.WebTestClient.ListBodySpec;
import org.springframework.web.reactive.function.BodyInserters;
import pl.michal.olszewski.rssaggregator.integration.IntegrationTestBase;
import reactor.test.StepVerifier;

class BlogApiTest extends IntegrationTestBase {

  @Autowired
  private BlogReactiveRepository blogRepository;

  @Autowired
  private MongoTemplate mongoTemplate;

  @Autowired
  private BlogService blogService;

  @BeforeEach
  void setUp() {
    blogRepository.deleteAll().block();
    mongoTemplate.remove(new Query(), "item");
    blogService.evictBlogCache();
  }

  @Test
  void should_get_empty_list_of_blogs() {
    givenBlog()
        .buildNumberOfBlogsDTOAndSave(0);

    ListBodySpec<BlogAggregationDTO> blogs = thenGetBlogsFromApi();

    blogs.hasSize(0);
  }

  @Test
  void should_get_all_blogs() {
    givenBlog()
        .buildNumberOfBlogsDTOAndSave(3);

    ListBodySpec<BlogAggregationDTO> blogs = thenGetBlogsFromApi();

    blogs.hasSize(3);
  }

  @Test
  void should_get_one_blog() {
    Blog blog = givenBlog()
        .buildNumberOfBlogsAndSave(1).get(0);
    BlogAggregationDTO expected = new BlogAggregationDTO(blog);

    BodySpec<BlogAggregationDTO, ?> blogDTO = thenGetOneBlogFromApiById(blog.getId());

    blogDTO.value(v -> assertThat(v).isEqualToComparingFieldByField(expected));
  }

  @Test
  void should_create_a_blog() {
    //given
    blogRepository.deleteAll().block();
    //when
    thenCreateBlogByApi("test");

    //then
    StepVerifier.create(blogRepository.findAll())
        .assertNext(v -> assertThat(v).isNotNull())
        .expectComplete()
        .verify();
  }

  @Test
  void should_update_existing_blog() {
    //given
    Instant instant = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    Blog blog = givenBlog()
        .buildNumberOfBlogsAndSave(1).get(0);
    BlogDTO blogDTO = new BlogDTO(blog.getBlogURL(), "desc", blog.getName(), blog.getFeedURL(), instant, new ArrayList<>()); //TODO skrocic linie

    //when
    thenUpdateBlogByApi(blogDTO);

    //then
    assertThat(blogRepository.findById(blog.getId()).block())
        .isNotNull()
        .hasFieldOrPropertyWithValue("description", "desc")
        .hasFieldOrPropertyWithValue("publishedDate", instant);
  }

  @Test
  void should_delete_existing_blog() {
    //given
    Blog blog = givenBlog()
        .buildNumberOfBlogsAndSave(1).get(0);
    //when
    thenDeleteOneBlogFromApi(blog.getId());

    //then
    StepVerifier.create(blogRepository.findById(blog.getId()))
        .expectSubscription()
        .verifyComplete();
  }

  @Test
  void should_get_one_blogWith2Items() {
    //given
    Blog blog = givenBlog()
        .buildBlogWithItemsAndSave(2);
    //when
    BodySpec<BlogAggregationDTO, ?> blogDTO = thenGetOneBlogFromApiById(blog.getId());
    //then
    blogDTO.value(v -> assertThat(v).isNotNull());
  }

  private BlogListFactory givenBlog() {
    return new BlogListFactory(blogRepository, mongoTemplate);
  }

  private ListBodySpec<BlogAggregationDTO> thenGetBlogsFromApi() {
    return webTestClient.get().uri("http://localhost:{port}/api/v1/blogs", port)
        .exchange()
        .expectStatus().isOk()
        .expectBodyList(BlogAggregationDTO.class);
  }

  private BodySpec<BlogAggregationDTO, ?> thenGetOneBlogFromApiById(String id) {
    return webTestClient.get()
        .uri("http://localhost:{port}/api/v1/blogs/{id}", port, id)
        .exchange()
        .expectStatus().isOk()
        .expectBody(BlogAggregationDTO.class);
  }

  private void thenCreateBlogByApi(String link) {
    webTestClient.post()
        .uri("http://localhost:{port}/api/v1/blogs", port)
        .body(BodyInserters.fromObject(BlogDTO.builder().link(link).build()))
        .exchange()
        .expectStatus().isNoContent();
  }

  private void thenUpdateBlogByApi(BlogDTO blogDTO) {
    webTestClient.put()
        .uri("http://localhost:{port}/api/v1/blogs", port)
        .body(BodyInserters.fromObject(blogDTO))
        .exchange()
        .expectStatus().isNoContent();
  }

  private void thenDeleteOneBlogFromApi(String blogId) {
    webTestClient.delete()
        .uri("http://localhost:{port}/api/v1/blogs/{blogId}", port, blogId)
        .exchange()
        .expectStatus().isNoContent();
  }

}
