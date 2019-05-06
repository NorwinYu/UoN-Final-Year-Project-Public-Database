package pl.michal.olszewski.rssaggregator.blog;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.rometools.rome.io.XmlReader;
import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.michal.olszewski.rssaggregator.item.ItemDTO;

class AtomReadRomeTest {

  private RssExtractorService rssExtractorService;


  @BeforeEach
  void setUp() {
    rssExtractorService = new RssExtractorService();
  }

  @Test
  void shouldCorrectTransformXmlToRome() {
    try {
      BlogDTO blog = rssExtractorService.getBlog(
          new XmlReader(new File("AtomExample.xml")),
          new Blog.RssInfo("feedURL", "blogURL", Instant.MIN),
          "correlationId"
      );
      assertAll(
          () -> assertThat(blog).isNotNull(),
          () -> assertThat(blog.getFeedURL()).isEqualTo("feedURL")
      );
    } catch (IOException e) {
      Assert.fail();
    }
  }

  @Test
  void shouldTransformWithCorrectTitle() {
    try {
      BlogDTO blog = rssExtractorService.getBlog(
          new XmlReader(new File("AtomExample.xml")),
          new Blog.RssInfo("feedURL", "blogURL", Instant.MIN),
          "correlationId"
      );
      assertThat(blog.getName()).isNotNull().isEqualTo("Test RSS");
    } catch (IOException e) {
      Assert.fail();
    }
  }

  @Test
  void shouldTransformWithCorrectDescriptionAndPublicationDate() {
    try {
      BlogDTO blog = rssExtractorService.getBlog(
          new XmlReader(new File("AtomExample.xml")),
          new Blog.RssInfo("feedURL", "blogURL", Instant.MIN),
          "correlationId"
      );
      assertThat(blog.getPublishedDate())
          .isNotNull()
          .isEqualTo(LocalDateTime.of(2017, 10, 27, 14, 9).atZone(ZoneId.of("UTC")).toInstant());
    } catch (IOException e) {
      Assert.fail();
    }
  }

  @Test
  void shouldTransformWithCorrectBlogItems() {
    try {
      BlogDTO blog = rssExtractorService.getBlog(
          new XmlReader(new File("AtomExample.xml")),
          new Blog.RssInfo("feedURL", "blogURL", Instant.MIN),
          "correlationId"
      );
      List<ItemDTO> itemsForBlog = blog.getItemsList();
      assertThat(itemsForBlog).isEmpty();
    } catch (IOException e) {
      Assert.fail();
    }
  }

  @Test
  void shouldThrowException() {
    assertThatThrownBy(() -> rssExtractorService.getBlog(
        null,
        new Blog.RssInfo("test", "blogURL", Instant.MIN),
        "correlationId"))
        .isNotNull()
        .hasMessage("Wystąpił błąd przy pobieraniu informacji z bloga test correlationID = correlationId");
  }

}
