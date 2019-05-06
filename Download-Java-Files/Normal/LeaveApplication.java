package com.leave;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LeaveApplication {

	public static void main(String[] args) {
		
		System.out.println("This is an added log message to indicate that Leave Application has started");
		SpringApplication.run(LeaveApplication.class, args);
	}
}
//java -jar target/demo-0.0.1-SNAPSHOT.jar