package pl.michal.olszewski.rssaggregator.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.micrometer.core.instrument.MeterRegistry;
import java.util.concurrent.TimeUnit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
class BlogCacheConfig {

  @Bean
  @Profile({Profiles.PRODUCTION})
  @Primary
  public Cache blogByIdCacheProd(MeterRegistry registry) {
    Cache cache = Caffeine.newBuilder()
        .expireAfterAccess(1, TimeUnit.HOURS)
        .maximumSize(30000)
        .build();
    registry.gauge("blogByIdCache", cache, Cache::estimatedSize);
    return cache;
  }

  @Bean
  @Profile({Profiles.TEST, Profiles.DEVELOPMENT})
  @Primary
  public Cache blogByIdCache() {
    return Caffeine.newBuilder()
        .expireAfterAccess(1, TimeUnit.MINUTES)
        .maximumSize(300)
        .build();
  }
}
