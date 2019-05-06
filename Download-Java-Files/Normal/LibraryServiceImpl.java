package edu.mum.library.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mum.library.common.LibraryException;
import edu.mum.library.dataaccess.BookDao;
import edu.mum.library.dataaccess.MemberDao;
import edu.mum.library.model.Book;
import edu.mum.library.model.BookCopy;
import edu.mum.library.model.CheckoutEntry;
import edu.mum.library.model.Member;

@Service
public class LibraryServiceImpl implements LibraryService {

	@Autowired
	private MemberDao memberDao;

	@Autowired
	private BookDao bookDao;

	@Override
	public void checkoutBook(String memberId, String isbn) {

		Member member = memberDao.readById(memberId);

		if (member == null) {
			throw new LibraryException("Member Id not found:" + memberId);
		}

		Book book = bookDao.readById(isbn);

		if (book == null) {
			throw new LibraryException("Book ISBN not found:" + isbn);
		}

		BookCopy bc = book.allocateCopy();
		if (bc == null) {
			throw new LibraryException("There are no copies available for this book:" + isbn);
		}

		CheckoutEntry ce = member.checkoutBook(bc);
		bc.checkout(ce);
		memberDao.save(member);
	}

	@Override
	public void addMember(Member member) {
		memberDao.insert(member);
	}

	@Override
	public void addBookCopy(Book book) {
		book.addBookCopy();
	}

	@Override
	public void addBook(Book book, int copies) {
		book.addBookCopyList(copies);
		bookDao.insert(book);
	}

	@Override
	public String printCheckoutRecord(String memberId) {
		Member member = memberDao.readById(memberId);
		if (member == null) {
			throw new LibraryException("Member Id not found: " + memberId);
		}
		return member.printCheckoutRecord();
	}

	@Override
	public List<CheckoutEntry> getOverdues(String isbn) {
		Book book = bookDao.readById(isbn);
		if (book == null) {
			throw new LibraryException("ISBN not found: " + isbn);
		}

		return book.getBookCopies().stream().filter(x -> !x.isAvailable()).map(y -> y.getCheckoutEntry())
				.collect(Collectors.toList());

	}

}
