package edu.mum.library.view.dto;

import edu.mum.library.model.Author;
import edu.mum.library.model.Person;
import javafx.beans.property.StringProperty;

public class AuthorDto extends PersonDto<Author> {

	private StringProperty credentials;

	private StringProperty shortBio;

	public AuthorDto(Person member) {
		super(member);
	}

	public String getCredentials() {
		return credentials.get();
	}

	public String getShortBio() {
		return shortBio.get();
	}

	public void setCredentials(String credentials) {
		this.credentials.set(credentials);
	}

	public void setShortBio(String shortBio) {
		this.shortBio.set(shortBio);
	}

}
