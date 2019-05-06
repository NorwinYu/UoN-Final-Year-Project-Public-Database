package propra2.leihOrDie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableRetry
public class LeihOrDieApplication {
    public static void main(String[] args) {
        SpringApplication.run(LeihOrDieApplication.class, args);
    }

}