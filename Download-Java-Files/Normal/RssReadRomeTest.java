package pl.michal.olszewski.rssaggregator.blog;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.michal.olszewski.rssaggregator.item.ItemDTO;

@ExtendWith(MockitoExtension.class)
class RssReadRomeTest {

  private final RssExtractorService rssExtractorService = new RssExtractorService();

  @Test
  void shouldCorrectTransformXmlToRome() {
    try {
      BlogDTO blog = rssExtractorService.getBlog(new XmlReader(new File("RssExample.xml")), new Blog.RssInfo("feedURL", "blogURL", Instant.MIN), "correlationId");
      assertAll(
          () -> assertThat(blog).isNotNull(),
          () -> assertThat(blog.getFeedURL()).isEqualTo("feedURL"),
          () -> assertThat(blog.getLink()).isNotNull().isEqualTo("http://rssAggragator.pl")
      );
    } catch (IOException e) {
      Assert.fail();
    }
  }

  @Test
  void shouldTransformWithCorrectTitle() {
    try {
      BlogDTO blog = rssExtractorService.getBlog(new XmlReader(new File("RssExample.xml")), new Blog.RssInfo("feedURL", "blogURL", Instant.MIN), "correlationId");
      assertThat(blog.getName()).isNotNull().isEqualTo("Test RSS");
    } catch (IOException e) {
      Assert.fail();
    }
  }

  @Test
  void shouldTransformWithCorrectDescriptionAndPublicationDate() {
    try {
      BlogDTO blog = rssExtractorService.getBlog(new XmlReader(new File("RssExample.xml")), new Blog.RssInfo("feedURL", "blogURL", Instant.MIN), "correlationId");
      assertAll(
          () -> assertThat(blog.getDescription()).isNotNull().isEqualTo("Testowy rss"),
          () -> assertThat(blog.getPublishedDate()).isNotNull().isEqualTo(LocalDateTime.of(2017, 10, 27, 14, 9).atZone(ZoneId.of("UTC")).toInstant())
      );
    } catch (IOException e) {
      Assert.fail();
    }
  }

  @Test
  void shouldTransformWithCorrectImage() {
    try (XmlReader reader = new XmlReader(new File("RssExample.xml"))) {
      SyndFeed feed = new SyndFeedInput().build(reader);
      assertAll(
          () -> assertThat(feed.getImage()).isNotNull(),
          () -> assertThat(feed.getImage().getWidth()).isEqualTo(32),
          () -> assertThat(feed.getImage().getHeight()).isEqualTo(32),
          () -> assertThat(feed.getImage().getUrl()).contains("image.png")
      );
    } catch (IOException | FeedException e) {
      Assert.fail();
    }
  }

  @Test
  void shouldTransformWithCorrectBlogItems() {
    try {
      BlogDTO blog = rssExtractorService.getBlog(new XmlReader(new File("RssExample.xml")), new Blog.RssInfo("feedURL", "blogURL", Instant.MIN), "correlationId");
      List<ItemDTO> itemsForBlog = blog.getItemsList().stream().sorted(Comparator.comparing(ItemDTO::getLink)).collect(Collectors.toList());
      assertAll(
          () -> assertThat(itemsForBlog).isNotEmpty().hasSize(2),
          () -> assertThat(itemsForBlog.get(0).getTitle()).isEqualTo("testowy item"),
          () -> assertThat(itemsForBlog.get(0).getLink()).isEqualTo("https://item.pl"),
          () -> assertThat(itemsForBlog.get(0).getDescription()).isEqualTo("Opis"),
          () -> assertThat(itemsForBlog.get(0).getAuthor()).isEqualTo("Michał"),
          () -> assertThat(itemsForBlog.get(0).getDate()).isEqualTo(LocalDateTime.of(2017, 10, 27, 14, 9).atZone(ZoneId.of("UTC")).toInstant()),
          () -> assertThat(itemsForBlog.get(1).getTitle()).isEqualTo("testowy item2"),
          () -> assertThat(itemsForBlog.get(1).getLink()).isEqualTo("https://item2.pl"),
          () -> assertThat(itemsForBlog.get(1).getDescription()).isEqualTo(""),
          () -> assertThat(itemsForBlog.get(1).getAuthor()).isEqualTo("Michał"),
          () -> assertThat(itemsForBlog.get(1).getDate()).isEqualTo(LocalDateTime.of(2017, 10, 27, 14, 9).atZone(ZoneId.of("UTC")).toInstant())
      );
    } catch (IOException e) {
      Assert.fail();
    }
  }

}
