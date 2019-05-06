package pl.michal.olszewski.rssaggregator.item;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemDTO {

  @JsonProperty("title")
  private final String title;
  @JsonProperty("description")
  private final String description;
  @JsonProperty("link")
  private final String link;
  @JsonProperty("date")
  private final Instant date;
  @JsonProperty("author")
  private final String author;

  @JsonCreator
  public ItemDTO(
      @JsonProperty("title") String title,
      @JsonProperty("description") String description,
      @JsonProperty("link") String link,
      @JsonProperty("date") Instant date,
      @JsonProperty("author") String author) {
    this.title = title;
    this.description = description;
    this.link = link;
    this.date = date;
    this.author = author;
  }

  public ItemDTO(Item item) {
    this.title = item.getTitle();
    this.description = item.getDescription();
    this.link = item.getLink();
    this.date = item.getDate();
    this.author = item.getAuthor();
  }
}
