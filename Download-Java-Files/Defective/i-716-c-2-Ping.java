package com.blitznihar.springbootsimplerestapi;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Ping {

	@RequestMapping("/ping")
	public ResponseEntity<?> ping(@RequestParam(value = "bounce", defaultValue = "") String bounce) {

		if (bounce.contains("error")) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(bounce + "@" + java.time.LocalDateTime.now().toString(), HttpStatus.OK);

		}
	}

}
