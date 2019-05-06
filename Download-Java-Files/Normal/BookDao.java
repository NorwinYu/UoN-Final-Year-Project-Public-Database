package edu.mum.library.dataaccess;

import org.springframework.stereotype.Component;

import edu.mum.library.dataaccess.base.BaseDaoWithPrimaryKey;
import edu.mum.library.model.Book;

@Component
public class BookDao extends BaseDaoWithPrimaryKey<Book, String> {

	@Override
	public String getTableName() {
		return "book";
	}
}
