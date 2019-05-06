package com.bakercreations.contacts.contactmanager.exceptions;

public class InvalidContactException extends RuntimeException {
    private static final long serialVersionUID = 1L;

	public InvalidContactException(String message) {
		super(message);
	}
}