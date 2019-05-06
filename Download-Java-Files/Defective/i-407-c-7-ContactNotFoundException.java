package com.bakercreations.contacts.contactmanager.exceptions;

public class ContactNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

	public ContactNotFoundException(String uuid) {
		super("Could not find contact " + uuid);
	}
}