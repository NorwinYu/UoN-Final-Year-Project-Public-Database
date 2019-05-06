package com.gcp.registration.domain;

import lombok.Data;

@Data
public class Response {
	private String errorMessage;
	private String message;
	private User userDetails;
}
