package edu.mum.library.view.dto;

import edu.mum.library.model.Person;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class PersonDto<T extends Person > extends BaseDto<T> {
	private StringProperty city;

	private StringProperty firstName;

	private StringProperty lastName;

	private StringProperty phoneNumber;

	private StringProperty state;

	private StringProperty street;

	private StringProperty zipcode;

	public PersonDto(Person person) {
		this.firstName = new SimpleStringProperty(person.getFirstName());
		this.lastName = new SimpleStringProperty(person.getLastName());
		this.phoneNumber = new SimpleStringProperty(person.getPhoneNumber());

		this.street = new SimpleStringProperty(person.getAddress().getStreet());
		this.city = new SimpleStringProperty(person.getAddress().getCity());
		this.state = new SimpleStringProperty(person.getAddress().getState());
		this.zipcode = new SimpleStringProperty(person.getAddress().getZipcode());
	}
	public StringProperty firstNameProperty() {
		return firstName;
	}
	public String getCity() {
		return city.getValue();
	}
	public String getFirstName() {
		return firstName.getValue();
	}

	public String getLastName() {
		return lastName.getValue();
	}
	
	public String getPhoneNumber() {
		return phoneNumber.getValue();
	}
	public String getState() {
		return state.getValue();
	}

	public String getStreet() {
		return street.getValue();
	}

	public String getZipcode() {
		return zipcode.getValue();
	}

	public StringProperty lastNameProperty() {
		return lastName;
	}

	public void setCity(String city) {
		this.city.set(city);
	}

	public void setFirstName(String firstName) {
		this.firstName.set(firstName);
	}

	public void setLastName(String lastName) {
		this.lastName.set(lastName);
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber.set(phoneNumber);
	}

	public void setState(String state) {
		this.state.set(state);
	}

	public void setStreet(String street) {
		this.street.set(street);
	}

	public void setZipcode(String zipcode) {
		this.zipcode.set(zipcode);
	}
}
