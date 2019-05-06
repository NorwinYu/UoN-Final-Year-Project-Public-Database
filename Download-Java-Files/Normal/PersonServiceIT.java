package hhu.ausleihservice.web;

import hhu.ausleihservice.dataaccess.AbholortRepository;
import hhu.ausleihservice.dataaccess.AusleihItemRepository;
import hhu.ausleihservice.dataaccess.AusleiheRepository;
import hhu.ausleihservice.dataaccess.PersonRepository;
import hhu.ausleihservice.databasemodel.Person;
import hhu.ausleihservice.web.service.PersonService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PersonServiceIT {

	PersonService personService;
	TestData testData;

	@Autowired
	private AusleihItemRepository ausleihItemRepository;
	@Autowired
	private PersonRepository personRepository;
	@Autowired
	private AbholortRepository abholortRepository;
	@Autowired
	private AusleiheRepository ausleiheRepository;

	@Before
	public void onStartup() {
		System.out.println("Populating the database");
		testData = new TestData();
		testData.getAbholortList().forEach(x -> abholortRepository.save(x));
		testData.getPersonList().forEach(x -> personRepository.save(x));
		testData.getAusleihItemList().forEach(x -> ausleihItemRepository.save(x));
		testData.getAusleiheList().forEach(x -> ausleiheRepository.save(x));
		personService = new PersonService(personRepository);
	}

	@Test
	public void testSimpleSearch_1_nullQuery() {
		List<Person> simple = personService.searchByNames(null);
		assertEquals(5, simple.size());
		assertEquals(testData.getPersonList(), simple);
	}

	@Test
	public void testSimpleSearch_2_emptyQuery() {
		List<Person> simple = personService.searchByNames("");
		assertEquals(5, simple.size());
		assertEquals(testData.getPersonList(), simple);
	}

	@Test
	public void testQueryJohn() {
		List<Person> queryJohn = personService.searchByNames("John");
		assertEquals(0, queryJohn.size());
	}

	@Test
	public void testQueryO() {
		List<Person> query = personService.searchByNames("O");
		Person gerold = testData.getPersonList().get(0);
		Person volker = testData.getPersonList().get(1);
		Person kartoffel = testData.getPersonList().get(2);
		Person admin = testData.getPersonList().get(3);
		assertEquals(4, query.size());
		assertTrue(query.contains(gerold));
		assertTrue(query.contains(volker));
		assertTrue(query.contains(kartoffel));
		assertTrue(query.contains(admin));
	}

	@Test
	public void testUserDetails() {
		UserDetails u = personService.loadUserByUsername("Miner4lwasser");
		assertEquals("Miner4lwasser", u.getUsername());
		assertEquals(testData.getPersonList().get(0).getPassword(), u.getPassword());
	}

	@Test
	public void testFindById() {
		Long id = personService.findByUsername("Miner4lwasser").get().getId();
		Person person = personService.findById(id);
		assertEquals(testData.getPersonList().get(0), person);
		Long id2 = personService.findByUsername("Kawumms").get().getId();
		Person person2 = personService.findById(id2);
		assertEquals(testData.getPersonList().get(1), person2);
	}

	@Test
	public void testUpdateById() {
		Person newPerson = new Person();
		newPerson.setId(5L);
		newPerson.setVorname("Ron");
		newPerson.setNachname("Aldinho");
		newPerson.setEmail("showdebola@brasil.br");
		Person gerri = personService.findByUsername("Miner4lwasser").get();
		Long id = gerri.getId();
		personService.updateById(id, newPerson);
		Person dbPerson = personService.findById(id);
		assertNotEquals("Gerold", dbPerson.getVorname());
		assertEquals("Ron", dbPerson.getVorname());
	}

}
