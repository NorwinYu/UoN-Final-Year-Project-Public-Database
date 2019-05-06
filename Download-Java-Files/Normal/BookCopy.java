package edu.mum.library.model;

import java.time.LocalDate;

import edu.mum.library.model.base.BaseEntityWithPrimaryKey;

public class BookCopy extends BaseEntityWithPrimaryKey<String> {

	/**
	 *
	 */
	private static final long serialVersionUID = -3987813167332764154L;

	private CheckoutEntry checkoutEntry;

	private String copyNumber;

	private boolean isAvailable;

	private Book originalBook;

	BookCopy(Book originalBook, String copyNumber, Boolean isAvailable) {
		this.originalBook = originalBook;
		this.copyNumber = copyNumber;
		this.isAvailable = isAvailable;
	}

	public void checkout(CheckoutEntry ce) {
		this.setCheckoutEntry(ce);
		this.setAvailable(false);
	}

	public Book getBook() {
		return originalBook;
	}
	public CheckoutEntry getCheckoutEntry() {
		return checkoutEntry;
	}

	public String getCopyNumber() {
		return copyNumber;
	}

	@Override
	public String getPrimaryKey() {
		return getCopyNumber();
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void returnBook()
	{
		this.setCheckoutEntry(null);
		this.setAvailable(true);
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public void setCheckoutEntry(CheckoutEntry checkoutEntry) {
		this.checkoutEntry = checkoutEntry;
	}
}
