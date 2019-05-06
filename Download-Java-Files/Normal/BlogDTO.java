package pl.michal.olszewski.rssaggregator.blog;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Singular;
import lombok.ToString;
import pl.michal.olszewski.rssaggregator.item.ItemDTO;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
class BlogDTO {

  private String link;
  private String description;
  private String name;
  private String feedURL;
  private Instant publishedDate;
  @JsonProperty("itemsList")
  @Singular("item")
  private List<ItemDTO> itemsList = new ArrayList<>();

  public BlogDTO(Blog blog, List<ItemDTO> items) {
    this.link = blog.getBlogURL();
    this.description = blog.getDescription();
    this.name = blog.getName();
    this.feedURL = blog.getFeedURL();
    this.publishedDate = blog.getPublishedDate();
    this.itemsList = items;
  }

  List<ItemDTO> getItemsList() {
    if (itemsList == null) {
      itemsList = new ArrayList<>();
    }
    return Collections.unmodifiableList(itemsList);
  }

  void addNewItem(ItemDTO itemDTO) {
    this.itemsList.add(itemDTO);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BlogDTO blogDTO = (BlogDTO) o;
    return Objects.equals(link, blogDTO.link) &&
        Objects.equals(description, blogDTO.description) &&
        Objects.equals(name, blogDTO.name) &&
        Objects.equals(feedURL, blogDTO.feedURL) &&
        Objects.equals(publishedDate, blogDTO.publishedDate) &&
        Objects.equals(itemsList, blogDTO.itemsList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(link, description, name, feedURL, publishedDate, itemsList);
  }
}
