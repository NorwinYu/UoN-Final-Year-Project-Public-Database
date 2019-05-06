package com.clocker.clocker;

import com.clocker.clocker.config.FileStorageConfig;
import com.clocker.clocker.repository.RoleRepository;
import com.clocker.clocker.repository.TimerRepository;
import com.clocker.clocker.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableConfigurationProperties({
		FileStorageConfig.class
})
@EnableJpaRepositories(basePackageClasses = {
		UserRepository.class,
		TimerRepository.class,
		RoleRepository.class,
})
public class ClockerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClockerApplication.class, args);
	}

}
