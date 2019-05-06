package edu.mum.library.view;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import edu.mum.library.common.LibraryException;
import edu.mum.library.common.NoAutoSettingGetting;
import edu.mum.library.dataaccess.BookDao;
import edu.mum.library.model.Author;
import edu.mum.library.model.Book;
import edu.mum.library.service.LibraryService;
import edu.mum.library.view.base.BaseFxModalController;
import edu.mum.library.view.base.BaseLibraryFxModalEditController;
import edu.mum.library.view.dto.BookDto;
import edu.mum.library.view.util.LibraryUiManager;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class BookEditDialogController extends BaseLibraryFxModalEditController<BookDto> {

	/**
	 * Author list to save the newly added author list
	 */
	private List<Author> authorAddedList = new ArrayList<>();

	@FXML
	@NoAutoSettingGetting
	private TextArea authors;
	@FXML
	private TextField availabilityField;
	@Autowired
	private BookDao bookDao;
	@FXML
	private TextField copiesField;
	@FXML
	private TextField isbnField;

	@Autowired
	private LibraryService libraryService;
	@Autowired
	private LibraryUiManager libraryUiManager;
	@FXML
	private TextField titleField;

	@FXML
	public void addAuthor() {
		BaseFxModalController result = libraryUiManager.showAuthorEditDialogDialog();
		if (result.isOkClicked()) {
			authorAddedList.add((Author) result.getReturnResult());
			updateAuthorInformationToUi();
		}
	}

	/**
	 * Called when the user clicks ok.
	 */
	@FXML
	private void handleOk() {
		try {
			if (isInputValid()) {
				if (this.entityDto != null) {
					this.fromViewToDto();
					// Read from database to update
					Book book = bookDao.readById(this.entityDto.getIsbn());
					book.setIsbn(isbnField.getText());
					book.setTitle(titleField.getText());
					book.setAvailability(Integer.parseInt(availabilityField.getText()));
					book.addAuthors(authorAddedList);
					bookDao.save(book);

				} else {
					Book book = new Book(isbnField.getText(), titleField.getText(),
							Integer.parseInt(availabilityField.getText()), new ArrayList<>(authorAddedList));

					libraryService.addBook(book, Integer.parseInt(copiesField.getText()));

				}
				okClicked = true;
				this.getCurrentStage().close();
			}
		} catch (LibraryException ex) {
			this.fxViewManager.showError(getCurrentStage(), ex.getMessage(), "Book Management", "Please correct Error");
		}
	}

	@Override
	protected String moreCheck() {
		StringBuilder sb = new StringBuilder();
		try {
			// try to parse the postal code into an int.
			int available = Integer.parseInt(availabilityField.getText());
			if (available <= 0) {
				sb.append("No valid vailability (must be greater than 0)!\n");
			}
		} catch (NumberFormatException e) {
			sb.append("No valid vailability (must be an integer)!\n");
		}
		try {
			// Check the copies and it should not be less than zero
			int copies = Integer.parseInt(copiesField.getText());
			if (copies < 0) {
				sb.append("No valid copies (must be greater than or equal to 0)!\n");
			}
		} catch (NumberFormatException e) {
			sb.append("No valid copies (must be an integer)!\n");
		}
		// If it is new book mode
		if (this.entityDto == null) {
			// Check whether author is added or not, author is a required field for a book
			if (authorAddedList.size() == 0) {
				sb.append("Please add author for the book!\n");
			}
		}
		return sb.toString();
	}

	@Override
	public void postInitInChild() {
		if (this.entityDto != null) {
			copiesField.setDisable(true);
			isbnField.setDisable(true);
		}
		updateAuthorInformationToUi();
		this.registerRequired("ISBN", isbnField::getText);
		this.registerRequired("Title", titleField::getText);
		this.registerRequired("availability", availabilityField::getText);
		this.registerRequired("Copies", copiesField::getText);
	}

	void updateAuthorInformationToUi() {
		List<Author> list = new ArrayList<>();
		if (this.entityDto != null) {
			list.addAll(bookDao.readById(this.entityDto.getIsbn()).getBookAuthors());
		}
		list.addAll(this.authorAddedList);
		String authorListText = list.stream().map(Object::toString).collect(Collectors.joining("\n"));
		authors.setText(authorListText);
	}
}
