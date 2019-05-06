package at.jugger.tracker.config;

import at.jugger.tracker.config.mock.MockMailServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
@Profile("default")
public class DevConfig {
    @Bean
    public JavaMailSender javaMailSender() {
        return new MockMailServer();
    }
}
