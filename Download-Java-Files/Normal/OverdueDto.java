package edu.mum.library.view.dto;

import java.time.LocalDate;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class OverdueDto {

	private StringProperty copyNumber;

	private ObjectProperty<LocalDate> dueDate;

	private StringProperty memberId;

	private BooleanProperty overdue;

	public OverdueDto(String copyNumber, LocalDate dueDate, String memberId, Boolean isOverdue) {
		super();
		this.copyNumber = new SimpleStringProperty(copyNumber);
		this.dueDate = new SimpleObjectProperty<>(dueDate);
		this.memberId = new SimpleStringProperty(memberId);
		this.overdue = new SimpleBooleanProperty(isOverdue);
	}

	public StringProperty copyNumberProperty() {
		return copyNumber;
	}

	public ObjectProperty<LocalDate> dueDateProperty() {
		return dueDate;
	}

	public StringProperty memberIdProperty() {
		return memberId;
	}

	public BooleanProperty overdueProperty() {
		return overdue;
	}

}
