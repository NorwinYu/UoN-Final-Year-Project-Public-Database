package pl.michal.olszewski.rssaggregator.blog;

import io.micrometer.core.annotation.Timed;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.stereotype.Repository;
import pl.michal.olszewski.rssaggregator.config.RegistryTimed;
import reactor.core.publisher.Flux;

@Repository
class ReactiveAggregationBlogRepositoryImpl implements ReactiveAggregationBlogRepository {

  private final ReactiveMongoTemplate reactiveMongoTemplate;

  public ReactiveAggregationBlogRepositoryImpl(ReactiveMongoTemplate reactiveMongoTemplate) {
    this.reactiveMongoTemplate = reactiveMongoTemplate;
  }

  @Override
  @RegistryTimed
  @Timed
  public Flux<BlogAggregationDTO> getBlogsWithCount() {
    return reactiveMongoTemplate.aggregate(Aggregation.newAggregation(
        Aggregation.unwind("items", true),
        Aggregation.group("id")
            .first("name").as("name")
            .first("blogURL").as("link")
            .first("description").as("description")
            .first("id").as("blogId")
            .first("feedURL").as("feedURL")
            .first("publishedDate").as("publishedDate")
            .addToSet("items").as("items"),
        Aggregation.project("blogId", "link", "description", "name", "feedURL", "publishedDate")
            .and("items").project("size").as("blogItemsCount"),
        Aggregation.sort(Direction.DESC, "blogItemsCount")
    ), Blog.class, BlogAggregationDTO.class);
  }
}
