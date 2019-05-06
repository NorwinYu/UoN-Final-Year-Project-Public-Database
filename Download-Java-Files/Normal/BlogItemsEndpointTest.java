package pl.michal.olszewski.rssaggregator.blog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.web.reactive.server.WebTestClient.ListBodySpec;
import pl.michal.olszewski.rssaggregator.integration.IntegrationTestBase;
import pl.michal.olszewski.rssaggregator.item.ItemDTO;

class BlogItemsEndpointTest extends IntegrationTestBase {

  @Autowired
  private BlogReactiveRepository blogRepository;

  @Autowired
  private MongoTemplate mongoTemplate;


  @Test
  void should_get_all_items_for_blog() {
    //given
    Blog blog = givenBlog()
        .buildBlogWithItemsAndSave(2);
    //when
    ListBodySpec<ItemDTO> dtos = thenGetBlogsItemsFromApi(blog.getId());
    //then
    dtos.hasSize(2);
  }

  private BlogListFactory givenBlog() {
    return new BlogListFactory(blogRepository, mongoTemplate);
  }

  private ListBodySpec<ItemDTO> thenGetBlogsItemsFromApi(String id) {
    return webTestClient.get().uri("http://localhost:{port}/api/v1/blogs/{id}/items", port, id)
        .exchange()
        .expectStatus().isOk()
        .expectBodyList(ItemDTO.class);
  }
}
