package edu.mum.library.mock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.mum.library.dataaccess.BookDao;
import edu.mum.library.dataaccess.storage.PersistanceManager;
import edu.mum.library.model.CheckoutEntry;

@Component
public class CreateMockData {

	@Autowired
	BookDao bookDao;

	public void create() {
//		Member member1 = new Member("member1", "John", "Smith", "641-727-4577");
//		Member member2 = new Member("member2", "John", "Doe", "641-727-4578");
//		Member member3 = new Member("member3", "Ana", "Maria", "641-727-4579");
//
//		libraryService.addMember(member1);
//		libraryService.addMember(member2);
//		libraryService.addMember(member3);
//
//		Author author1 = new Author("Cay S.", "Horstmann", "641-727-4577");
//
//		Book book1 = new Book("978-0321996329", "Core Java for the impatient", 7, Arrays.asList(author1));

		bookDao.getAll().stream().forEach(book->{
			book.getBookCopies().stream().forEach(bc->{
				CheckoutEntry ce = bc.getCheckoutEntry();
				if(ce!=null)
				{
					ce.setCheckoutDate(ce.getCheckoutDate().minusDays(20));
					ce.setDueDate(ce.getDueDate().minusDays(20));
				}
			});
		});
		PersistanceManager.saveDatabase();
	}

}
