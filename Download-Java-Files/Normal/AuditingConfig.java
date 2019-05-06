package pl.michal.olszewski.rssaggregator.config;

import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.stereotype.Component;

@EnableMongoAuditing
@Profile({Profiles.PRODUCTION, Profiles.TEST, Profiles.DEVELOPMENT})
@Component
class AuditingConfig {

}
