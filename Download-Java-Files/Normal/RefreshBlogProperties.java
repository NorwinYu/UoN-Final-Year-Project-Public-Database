package pl.michal.olszewski.rssaggregator.blog;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("refresh.blog")
@Data
class RefreshBlogProperties {

  private Integer milis = 5 * 1000 * 60;
}
