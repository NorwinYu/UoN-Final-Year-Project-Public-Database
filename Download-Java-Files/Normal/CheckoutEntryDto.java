package edu.mum.library.view.dto;

import java.time.LocalDate;

import edu.mum.library.model.CheckoutEntry;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CheckoutEntryDto extends BaseDto<CheckoutEntry> {

	private SimpleObjectProperty<LocalDate> checkoutDate;
	private StringProperty copyNumber;
	private SimpleObjectProperty<LocalDate> dueDate;

	public CheckoutEntryDto(CheckoutEntry checkoutEntry) {
		this.copyNumber = new SimpleStringProperty(checkoutEntry.getBookCopy().getCopyNumber());
		this.checkoutDate = new SimpleObjectProperty<>(checkoutEntry.getCheckoutDate());
		this.dueDate = new SimpleObjectProperty<>(checkoutEntry.getDueDate());
	}

	public SimpleObjectProperty<LocalDate> checkoutDateProperty() {
		return checkoutDate;
	}

	public StringProperty copyNumberProperty() {
		return copyNumber;
	}

	public SimpleObjectProperty<LocalDate> dueDateProperty() {
		return dueDate;
	}
}
