package pl.michal.olszewski.rssaggregator.blog;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/blogs")
@Slf4j
class BlogEndPoint {

  private final BlogService blogService;

  public BlogEndPoint(BlogService blogService) {
    this.blogService = blogService;
  }

  @GetMapping(value = "/{id}")
  public Mono<BlogAggregationDTO> getBlog(@PathVariable("id") String blogId) {
    String correlationID = UUID.randomUUID().toString();
    log.debug("START GET blog by id {} correlationId {}", blogId, correlationID);
    return blogService.getBlogDTOById(blogId, correlationID)
        .map(this::addLinkToBlogItems)
        .doOnSuccess(result -> log.trace("END GET blog by id {} correlationId {}", blogId, correlationID))
        .doOnError(error -> log.error("ERROR GET blog by id {} correlationId {}", blogId, correlationID, error));
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Flux<BlogAggregationDTO> getBlogs() {
    String correlationID = UUID.randomUUID().toString();
    log.debug("START GET blogs correlationId {}", correlationID);
    return blogService.getAllBlogDTOs(correlationID)
        .map(this::addLinkToSelf)
        .map(this::addLinkToBlogItems)
        .doOnComplete(() -> log.debug("END GET blogs - correlationId {}", correlationID))
        .doOnError(error -> log.error("ERROR GET blogs - correlationId {}", correlationID, error));
  }

  @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Blog> updateBlog(@RequestBody BlogDTO blogDTO) {
    String correlationId = UUID.randomUUID().toString();
    log.debug("PUT - updateBlog {} correlationId {}", blogDTO.getName(), correlationId);
    log.trace("PUT - updateBlog {} correlationId {}", blogDTO, correlationId);
    return blogService.updateBlog(blogDTO, correlationId)
        .doOnSuccess(blog -> log.debug("END updateBlog - correlationId {}", correlationId))
        .doOnError(error -> log.error("ERROR updateBlog - correlationId {}", correlationId, error));
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Blog> addBlog(@RequestBody BlogDTO blogDTO) {
    String correlationId = UUID.randomUUID().toString();
    log.debug("POST - addBlog {} correlationId {}", blogDTO.getName(), correlationId);
    log.trace("POST - addBlog {} correlationId {}", blogDTO, correlationId);
    return blogService.getBlogOrCreate(blogDTO, correlationId)
        .doOnSuccess(blog -> log.debug("END addBlog {} - correlationId {}", blogDTO.getName(), correlationId))
        .doOnError(error -> log.error("ERROR addBlog {} - correlationId {}", blogDTO.getName(), correlationId, error));
  }

  @DeleteMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Void> deleteBlog(@PathVariable("id") String blogId) {
    String correlationId = UUID.randomUUID().toString();
    log.debug("DELETE - deleteBlog id {} correlationId {}", blogId, correlationId);
    return blogService.deleteBlog(blogId, correlationId)
        .doOnSuccess(blog -> log.debug("END deleteBlog id {} - correlationId {}", blogId, correlationId))
        .doOnError(error -> log.error("ERROR deleteBlog id {} - correlationId {}", blogId, correlationId, error));
  }

  @PostMapping(value = "/evictCache")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void evictCache() {
    log.debug("POST - evict Cache");
    blogService.evictBlogCache();
  }

  private BlogAggregationDTO addLinkToSelf(BlogAggregationDTO blog) {
    if (blog.getLink("self") == null) {
      Link link = linkTo(methodOn(BlogEndPoint.class)
          .getBlog(blog.getBlogId())).withSelfRel();
      blog.add(link);
    }
    return blog;
  }

  private BlogAggregationDTO addLinkToBlogItems(BlogAggregationDTO blog) {
    if (blog.getLink("items") == null) {
      Link link = linkTo(methodOn(BlogItemsEndPoint.class)
          .getBlogItems(blog.getBlogId())).withRel("items");
      blog.add(link);
    }
    return blog;
  }

}
