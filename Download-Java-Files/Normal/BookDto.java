package edu.mum.library.view.dto;

import edu.mum.library.model.Book;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BookDto extends BaseDto<Book> {

	private StringProperty availability;
	private StringProperty copies;
	private StringProperty isbn;
	private StringProperty title;

	public BookDto(Book member) {
		this.isbn = new SimpleStringProperty(member.getIsbn());
		this.title = new SimpleStringProperty(member.getTitle());
		this.availability = new SimpleStringProperty(Integer.toString(member.getAvailability()));
		this.copies = new SimpleStringProperty(Integer.toString((member.getBookCopies().size())));
	}

	public StringProperty availabilityProperty() {
		return availability;
	}

	public StringProperty copiesProperty() {
		return copies;
	}

	public String getAvailability() {
		return availability.get();
	}

	public String getCopies() {
		return copies.get();
	}

	public String getIsbn() {
		return isbn.get();
	}

	public String getTitle() {
		return title.get();
	}

	public StringProperty isbnProperty() {
		return isbn;
	}

	public void setAvailability(String availability) {
		this.availability.set(availability);
	}

	public void setCopies(String numberofCopies) {
		this.copies.set(numberofCopies);
	}

	public void setIsbn(String isbn) {
		this.isbn.set(isbn);
	}

	public void setTitle(String title) {
		this.title.set(title);
	}

	public StringProperty titleProperty() {
		return title;
	}
}
