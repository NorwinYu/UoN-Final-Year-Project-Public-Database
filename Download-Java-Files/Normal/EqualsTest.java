package pl.michal.olszewski.rssaggregator.units;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;
import pl.michal.olszewski.rssaggregator.blog.Blog;
import pl.michal.olszewski.rssaggregator.item.Item;
import pl.michal.olszewski.rssaggregator.item.ItemDTO;

class EqualsTest {

  @Test
  void blogEqualsContractTest() {
    Item item1 = new Item(ItemDTO.builder().title("title").build());
    Item item2 = new Item(ItemDTO.builder().title("title2").build());
    EqualsVerifier.forClass(Blog.class)
        .withPrefabValues(Item.class, item1, item2)
        .withIgnoredFields("id")
        .suppress(Warning.NONFINAL_FIELDS)
        .withNonnullFields("items")
        .verify();
  }

  @Test
  void itemEqualsContractTest() {
    EqualsVerifier.forClass(Item.class)
        .withIgnoredFields("id", "createdAt", "read")
        .suppress(Warning.NONFINAL_FIELDS)
        .verify();
  }
}
