package pl.michal.olszewski.rssaggregator.blog;

import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/v1/blogs")
@Slf4j
class BlogItemsEndPoint {

  private final BlogService blogService;

  public BlogItemsEndPoint(BlogService blogService) {
    this.blogService = blogService;
  }

  @GetMapping(value = "/{id}/items")
  public Flux<BlogItemDTO> getBlogItems(@PathVariable("id") String blogId) {
    String correlationId = UUID.randomUUID().toString();
    log.debug("START GET blogItems for id {} correlationId {}", blogId, correlationId);
    return blogService.getBlogItemsForBlog(blogId, correlationId)
        .doOnComplete(() -> log.debug("END GET blogItems for id {} correlationId {}", blogId, correlationId));
  }
}
