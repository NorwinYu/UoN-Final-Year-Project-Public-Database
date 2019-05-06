package edu.mum.library.service;

import java.util.List;

import edu.mum.library.model.Book;
import edu.mum.library.model.CheckoutEntry;
import edu.mum.library.model.Member;

public interface LibraryService {

	void checkoutBook(String memberId, String isbn);

	void addMember(Member member);

	void addBookCopy(Book book);

	void addBook(Book book, int copies);

	String printCheckoutRecord(String memberId);

	List<CheckoutEntry> getOverdues(String isbn);
}
