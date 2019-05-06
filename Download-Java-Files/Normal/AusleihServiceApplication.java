package hhu.ausleihservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AusleihServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AusleihServiceApplication.class, args);
	}

}

