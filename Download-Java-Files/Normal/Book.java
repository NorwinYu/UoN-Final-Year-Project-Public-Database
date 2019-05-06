package edu.mum.library.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import edu.mum.library.model.base.BaseEntityWithPrimaryKey;

public class Book extends BaseEntityWithPrimaryKey<String> {

	/**
	 *
	 */
	private static final long serialVersionUID = 1132196726352622831L;
	private int availability;

	private List<Author> bookAuthors;

	private List<BookCopy> bookCopies;

	private String isbn;

	private String title;

	public Book(String isbn, String title, int availability, List<Author> authorList) {
		this.title = title;
		this.isbn = isbn;
		this.availability = availability;

		bookCopies = new ArrayList<>();
		bookAuthors = new ArrayList<>(authorList);
	}

	public void addBookCopy() {
		int index = LocalDate.now().getYear() * 10000 + LocalDate.now().getMonthValue() * 100 + (bookCopies.size() + 1);
		addBookCopy(index);
	}

	private void addBookCopy(int index) {
		String generated_id = this.getIsbn() + "_copy_" + index;
		bookCopies.add(new BookCopy(this, generated_id, true));
	}

	public void addBookCopyList(int size) {
		for (int i = 0; i < size; i++) {
			addBookCopy();
		}
	}

	public BookCopy allocateCopy() {

		Optional<BookCopy> bcOptional = bookCopies.stream().filter(bc -> bc.isAvailable()).findAny();
		return bcOptional.isPresent() ? bcOptional.get() : null;
	}

	public int getAvailability() {
		return availability;
	}

	public List<Author> getBookAuthors() {
		return Collections.unmodifiableList(bookAuthors);
	}

	public void addAuthors(List<Author> newAuthors) {
		bookAuthors.addAll(newAuthors);
	}

	public List<BookCopy> getBookCopies() {
		return Collections.unmodifiableList(bookCopies);
	}

	public String getIsbn() {
		return isbn;
	}

	@Override
	public String getPrimaryKey() {
		return getIsbn();
	}

	public String getTitle() {
		return title;
	}

	public void setAvailability(int availability) {
		this.availability = availability;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
