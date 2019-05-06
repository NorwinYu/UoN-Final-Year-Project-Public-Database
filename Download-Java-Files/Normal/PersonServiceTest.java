package hhu.ausleihservice.web;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import hhu.ausleihservice.dataaccess.PersonRepository;
import hhu.ausleihservice.databasemodel.Person;
import hhu.ausleihservice.web.service.PersonService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PersonServiceTest {

	//This field is required for the tests to work but will cause spotBugs to detect dodgy code
	@Rule
	@SuppressFBWarnings("URF_UNREAD_PUBLIC_OR_PROTECTED_FIELD")
	public MockitoRule mockitoRule = MockitoJUnit.rule();
	@Mock
	private PersonRepository personRepository;
	private PersonService personService = new PersonService(null);
	private List<Person> repository = new ArrayList<>();

	private boolean testPersonEquality(Person base, Person toTest) {
		return base.getId().longValue() == toTest.getId().longValue() &&
				base.getVorname().equals(toTest.getVorname()) &&
				base.getNachname().equals(toTest.getNachname()) &&
				base.getUsername().equals(toTest.getUsername());
	}

	@Before
	public void prepareTestData() {
		repository = new ArrayList<>();

		Person person1 = new Person();
		person1.setId(1L);
		person1.setVorname("John");
		person1.setNachname("Egbert");
		person1.setUsername("ghostyTrickster");

		Person person2 = new Person();
		person2.setId(2L);
		person2.setVorname("John");
		person2.setNachname("Stamos");
		person2.setUsername("Friendo");

		Person person3 = new Person();
		person3.setId(3L);
		person3.setVorname("Liv");
		person3.setNachname("Tyler");
		person3.setUsername("TheEndOfTheWorld");

		Person person4 = new Person();
		person4.setId(4L);
		person4.setVorname("Tyler");
		person4.setNachname("Johnson");
		person4.setUsername("TyJay");

		Person person5 = new Person();
		person5.setId(5L);
		person5.setVorname("Elizabeth");
		person5.setNachname("Stamos");
		person5.setUsername("Friendie");

		Person person6 = new Person();
		person6.setId(6L);
		person6.setVorname("Lance");
		person6.setNachname("Vance");
		person6.setUsername("Dance");

		repository.add(person1);
		repository.add(person2);
		repository.add(person3);
		repository.add(person4);
		repository.add(person5);
		repository.add(person6);

		personService = new PersonService(personRepository);

		Mockito.when(personRepository.findAll()).thenReturn(repository);
		Optional<Person> person1Opt = Optional.of(person1);
		Mockito.when(personRepository.findByUsername("ghostyTrickster")).thenReturn(person1Opt);
	}

	@Test
	public void testSearchByNames_1_nullQuery() {
		List<Person> searchedPeople = personService.searchByNames(null);

		assertEquals(6, searchedPeople.size());

		for (Person searchedPerson : searchedPeople) {
			if (searchedPerson.getId() == 1L) {
				assertTrue(testPersonEquality(repository.get(0), searchedPerson));
			} else if (searchedPerson.getId() == 2L) {
				assertTrue(testPersonEquality(repository.get(1), searchedPerson));
			} else if (searchedPerson.getId() == 3L) {
				assertTrue(testPersonEquality(repository.get(2), searchedPerson));
			} else if (searchedPerson.getId() == 4L) {
				assertTrue(testPersonEquality(repository.get(3), searchedPerson));
			} else if (searchedPerson.getId() == 5L) {
				assertTrue(testPersonEquality(repository.get(4), searchedPerson));
			} else if (searchedPerson.getId() == 6L) {
				assertTrue(testPersonEquality(repository.get(5), searchedPerson));
			} else {
				fail();
			}
		}
	}

	@Test
	public void testSearchByNames_2_emptyQuery() {
		List<Person> searchedPeople = personService.searchByNames("");

		assertEquals(6, searchedPeople.size());

		for (Person searchedPerson : searchedPeople) {
			if (searchedPerson.getId() == 1L) {
				assertTrue(testPersonEquality(repository.get(0), searchedPerson));
			} else if (searchedPerson.getId() == 2L) {
				assertTrue(testPersonEquality(repository.get(1), searchedPerson));
			} else if (searchedPerson.getId() == 3L) {
				assertTrue(testPersonEquality(repository.get(2), searchedPerson));
			} else if (searchedPerson.getId() == 4L) {
				assertTrue(testPersonEquality(repository.get(3), searchedPerson));
			} else if (searchedPerson.getId() == 5L) {
				assertTrue(testPersonEquality(repository.get(4), searchedPerson));
			} else if (searchedPerson.getId() == 6L) {
				assertTrue(testPersonEquality(repository.get(5), searchedPerson));
			} else {
				fail();
			}
		}
	}

	@Test
	public void testSearchByNames_3_queryJohn() {
		List<Person> searchedPeople = personService.searchByNames("John");

		assertEquals(3, searchedPeople.size());

		for (Person searchedPerson : searchedPeople) {
			if (searchedPerson.getId() == 1L) {
				assertTrue(testPersonEquality(repository.get(0), searchedPerson));
			} else if (searchedPerson.getId() == 2L) {
				assertTrue(testPersonEquality(repository.get(1), searchedPerson));
			} else if (searchedPerson.getId() == 4L) {
				assertTrue(testPersonEquality(repository.get(3), searchedPerson));
			} else {
				fail();
			}
		}
	}

	@Test
	public void testSearchByNames_4_queryLance() {
		List<Person> searchedPeople = personService.searchByNames("Lance");

		assertEquals(1, searchedPeople.size());

		for (Person searchedPerson : searchedPeople) {
			if (searchedPerson.getId() == 6L) {
				assertTrue(testPersonEquality(repository.get(5), searchedPerson));
			} else {
				fail();
			}
		}
	}

	@Test
	public void testSearchByNames_5_queryTyler() {
		List<Person> searchedPeople = personService.searchByNames("Tyler");

		assertEquals(2, searchedPeople.size());

		for (Person searchedPerson : searchedPeople) {
			if (searchedPerson.getId() == 3L) {
				assertTrue(testPersonEquality(repository.get(2), searchedPerson));
			} else if (searchedPerson.getId() == 4L) {
				assertTrue(testPersonEquality(repository.get(3), searchedPerson));
			} else {
				fail();
			}
		}
	}

	@Test
	public void testSearchByNames_6_queryStamos() {
		List<Person> searchedPeople = personService.searchByNames("Stamos");

		assertEquals(2, searchedPeople.size());

		for (Person searchedPerson : searchedPeople) {
			if (searchedPerson.getId() == 2L) {
				assertTrue(testPersonEquality(repository.get(1), searchedPerson));
			} else if (searchedPerson.getId() == 5L) {
				assertTrue(testPersonEquality(repository.get(4), searchedPerson));
			} else {
				fail();
			}
		}
	}

	@Test
	public void testSearchByNames_7_queryTyJay() {
		List<Person> searchedPeople = personService.searchByNames("TyJay");

		assertEquals(1, searchedPeople.size());

		for (Person searchedPerson : searchedPeople) {
			if (searchedPerson.getId() == 4L) {
				assertTrue(testPersonEquality(repository.get(3), searchedPerson));
			} else {
				fail();
			}
		}
	}

	@Test
	public void testSearchByNames_8_queryJade() {
		List<Person> searchedPeople = personService.searchByNames("Jade");

		assertEquals(0, searchedPeople.size());
	}

	@Test
	public void testSearchByNames_9_queryMix_1() {
		List<Person> searchedPeople = personService.searchByNames("John Stamos");

		assertEquals(1, searchedPeople.size());

		for (Person searchedPerson : searchedPeople) {
			if (searchedPerson.getId() == 2L) {
				assertTrue(testPersonEquality(repository.get(1), searchedPerson));
			} else {
				fail();
			}
		}
	}

	@Test
	public void testSearchByNames_10_queryMix_2() {
		List<Person> searchedPeople = personService.searchByNames("Lance Vance Dance");

		assertEquals(1, searchedPeople.size());

		for (Person searchedPerson : searchedPeople) {
			if (searchedPerson.getId() == 6L) {
				assertTrue(testPersonEquality(repository.get(5), searchedPerson));
			} else {
				fail();
			}
		}
	}

	@Test
	public void testSearchByNames_11_queryMix_3() {
		List<Person> searchedPeople = personService.searchByNames("Liv Stamos");

		assertEquals(0, searchedPeople.size());
	}
	
	@Test
	public void getNullPrincipal() {
		assertEquals(null, personService.get(null));
	}
	
	@Test
	public void getValidPrincipal() {
		Principal principal = mock(Principal.class);
		when(principal.getName()).thenReturn("ghostyTrickster");
		assertTrue(testPersonEquality(repository.get(0), personService.get(principal)));
	}
}
