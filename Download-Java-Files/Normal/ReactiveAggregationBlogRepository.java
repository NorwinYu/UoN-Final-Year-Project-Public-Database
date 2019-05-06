package pl.michal.olszewski.rssaggregator.blog;

import reactor.core.publisher.Flux;

interface ReactiveAggregationBlogRepository {

  Flux<BlogAggregationDTO> getBlogsWithCount();
}
