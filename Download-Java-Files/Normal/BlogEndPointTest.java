package pl.michal.olszewski.rssaggregator.blog;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@WebFluxTest(BlogEndPoint.class)
class BlogEndPointTest {

  @MockBean
  private BlogService blogService;

  @Autowired
  private WebTestClient webClient;

  @Test
  void shouldGetBlogByIdReturnStatusOK() {
    given(blogService.getBlogDTOById(Mockito.eq("1"), Mockito.anyString())).willReturn(Mono.just(new BlogAggregationDTO()));

    webClient.get().uri("/api/v1/blogs/1")
        .exchange()
        .expectStatus().isOk();
  }

  @Test
  void shouldGetBlogByIdReturnStatusNotFoundWhenBlogNotExist() {
    given(blogService.getBlogDTOById(Mockito.eq("1"), Mockito.anyString())).willReturn(Mono.error(new BlogNotFoundException("aaa", "correlationID")));

    webClient.get().uri("/api/v1/blogs/1")
        .exchange()
        .expectStatus()
        .isNotFound();
  }

  @Test
  void shouldGetBlogByIdReturnBlogAsJson() {
    given(blogService.getBlogDTOById(Mockito.eq("1"), Mockito.anyString())).willReturn(Mono.just(new BlogAggregationDTO()));

    webClient.get().uri("/api/v1/blogs/1")
        .exchange()
        .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8);
  }

  @Test
  void shouldReturnCorrectBlogById() {
    given(blogService.getBlogDTOById(Mockito.eq("1"), Mockito.anyString())).willReturn(Mono.just(new BlogAggregationDTO(Blog.builder().name("nazwa").build())));

    webClient.get().uri("/api/v1/blogs/1")
        .exchange()
        .expectBody(BlogDTO.class)
        .value(v -> assertThat(v.getName()).isEqualTo("nazwa"));
  }

  @Test
  void shouldGetBlogsReturnStatusOK() {
    given(blogService.getAllBlogDTOs(Mockito.anyString())).willReturn(Flux.empty());

    webClient.get().uri("/api/v1/blogs")
        .exchange()
        .expectStatus().isOk();
  }

  @Test
  void shouldUpdateBlogReturnStatusNoContent() {
    BlogDTO blogDTO = BlogDTO.builder().build();
    given(blogService.updateBlog(Mockito.eq(blogDTO), Mockito.anyString())).willReturn(Mono.just(new Blog()));

    webClient.put().uri("/api/v1/blogs")
        .body(BodyInserters.fromObject(blogDTO))
        .exchange()
        .expectStatus()
        .isNoContent();
  }

  @Test
  void shouldCreateBlogReturnStatusNoContent() {
    BlogDTO blogDTO = BlogDTO.builder().name("name").build();
    given(blogService.getBlogOrCreate(Mockito.eq(blogDTO), Mockito.anyString())).willReturn(Mono.just(new Blog()));

    webClient.post().uri("/api/v1/blogs")
        .body(BodyInserters.fromObject(blogDTO))
        .exchange()
        .expectStatus()
        .isNoContent();
  }

  @Test
  void shouldDeleteBlogReturnStatusNoContent() {
    given(blogService.deleteBlog(Mockito.eq("1"), Mockito.anyString())).willReturn(Mono.empty());

    webClient.delete().uri("/api/v1/blogs/1")
        .exchange()
        .expectStatus()
        .isNoContent();
  }
}
