package pl.michal.olszewski.rssaggregator.blog;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import lombok.Builder;
import lombok.Getter;
import pl.michal.olszewski.rssaggregator.item.Item;

@Getter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
class BlogItemDTO {

  @JsonProperty("title")
  private final String title;
  @JsonProperty("link")
  private final String link;
  @JsonProperty("date")
  private final Instant date;
  @JsonProperty("author")
  private final String author;

  @JsonCreator
  BlogItemDTO(
      @JsonProperty("title") String title,
      @JsonProperty("link") String link,
      @JsonProperty("date") Instant date,
      @JsonProperty("author") String author) {
    this.title = title;
    this.link = link;
    this.date = date;
    this.author = author;
  }

  BlogItemDTO(Item item) {
    this.title = item.getTitle();
    this.link = item.getLink();
    this.date = item.getDate();
    this.author = item.getAuthor();
  }
}
