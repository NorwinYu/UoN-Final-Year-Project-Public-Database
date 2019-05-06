package edu.mum.library.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import edu.mum.library.model.Person;

@Component
public class PersonService {

	private List<Person> peopleList = new ArrayList<>();

	{
		peopleList.add(new Person("Hans", "Muster"));
		peopleList.add(new Person("Ruth", "Mueller"));
		peopleList.add(new Person("Heinz", "Kurz"));
		peopleList.add(new Person("Cornelia", "Meier"));
		peopleList.add(new Person("Werner", "Meyer"));
		peopleList.add(new Person("Lydia", "Kunz"));
		peopleList.add(new Person("Anna", "Best"));
		peopleList.add(new Person("Stefan", "Meier"));
		peopleList.add(new Person("Martin", "Mueller"));
	}

	public List<Person> getAllPerson() {
		return peopleList;
	}

	public void addPerson(Person person) {
		peopleList.add(person);
	}
}
