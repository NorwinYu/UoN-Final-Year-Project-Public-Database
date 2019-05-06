package pl.michal.olszewski.rssaggregator.blog;

import java.time.Instant;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.hateoas.ResourceSupport;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
class BlogAggregationDTO extends ResourceSupport {

  private String blogId;
  private String link;
  private String description;
  private String name;
  private String feedURL;
  private Instant publishedDate;
  private Long blogItemsCount;


  BlogAggregationDTO(Blog blog) {
    this.blogId = blog.getId();
    this.link = blog.getBlogURL();
    this.description = blog.getDescription();
    this.name = blog.getName();
    this.feedURL = blog.getFeedURL();
    this.publishedDate = blog.getPublishedDate();
    this.blogItemsCount = (long) blog.getItems().size();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BlogAggregationDTO that = (BlogAggregationDTO) o;
    return Objects.equals(blogId, that.blogId) &&
        Objects.equals(link, that.link) &&
        Objects.equals(description, that.description) &&
        Objects.equals(name, that.name) &&
        Objects.equals(feedURL, that.feedURL) &&
        Objects.equals(publishedDate, that.publishedDate) &&
        Objects.equals(blogItemsCount, that.blogItemsCount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(blogId, link, description, name, feedURL, publishedDate, blogItemsCount);
  }
}
