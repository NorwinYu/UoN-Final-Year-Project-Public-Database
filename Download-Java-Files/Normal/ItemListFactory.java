package pl.michal.olszewski.rssaggregator.item;

import java.util.UUID;
import java.util.stream.IntStream;
import org.springframework.data.mongodb.core.MongoTemplate;
import pl.michal.olszewski.rssaggregator.blog.Blog;
import pl.michal.olszewski.rssaggregator.blog.BlogReactiveRepository;

class ItemListFactory {

  private final BlogReactiveRepository repository;
  private final MongoTemplate itemRepository;

  ItemListFactory(BlogReactiveRepository repository, MongoTemplate itemRepository) {
    this.repository = repository;
    this.itemRepository = itemRepository;
  }

  void buildNumberOfItemsAndSave(int numberOfItems) {
    Blog blog = Blog.builder().blogURL(UUID.randomUUID().toString()).build();

    IntStream.rangeClosed(1, numberOfItems)
        .parallel()
        .forEach(number -> blog.addItem(new Item(ItemDTO.builder().link("link" + number).title("title" + number).build()), itemRepository));

    repository.save(blog).block();
  }
}
