package hhu.ausleihservice.web;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import hhu.ausleihservice.databasemodel.Person;
import hhu.ausleihservice.web.controller.MainController;
import hhu.ausleihservice.web.service.PersonService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SuchControllerUnitTest {

	private MockMvc mockMvc;

	@Autowired
	protected WebApplicationContext wac;

	@Autowired
	MainController mainController;

	@MockBean
	PersonService personService;

	private List<Person> personListe;

	@Before
	public void setup() {
		this.mockMvc = standaloneSetup(this.mainController).build();
		Person person1 = new Person();
		Person person2 = new Person();
		Person person3 = new Person();
		Person person4 = new Person();
		Person person5 = new Person();

		person1.setVorname("Gerold");
		person1.setNachname("Steiner");
		person2.setVorname("Volker");
		person2.setNachname("Racho");
		person3.setVorname("Wilma");
		person3.setNachname("Pause");
		person4.setVorname("AdminVorname");
		person4.setNachname("AdminNachname");
		person5.setVorname("asdffsdag");
		person5.setNachname("sbsbsew");

		person1.setUsername("Miner4lwasser");
		person2.setUsername("Kawumms");
		person3.setUsername("Kautschkartoffel3000");
		person4.setUsername("admin");
		person5.setUsername("user");

		personListe = new ArrayList<>();
		personListe.add(person1);
		personListe.add(person2);
		personListe.add(person3);
		personListe.add(person4);
		personListe.add(person5);
	}
	
	@Test
	public void emptyUserSearch() throws Exception {
		Principal principal = mock(Principal.class);
		when(principal.getName()).thenReturn("user");
		when(personService.searchByNames("")).thenReturn(personListe);
		when(personService.get(principal)).thenReturn(personListe.get(4));
		mockMvc.perform(post("/benutzersuche").principal(principal).param("query", ""))
		.andExpect(status().isOk())
		.andExpect(model().attribute("benutzerListe", personListe))
		.andExpect(model().attribute("user", personListe.get(4)));
	}
	
	@Test
	public void simpleUserSearch() throws Exception {
		Principal principal = mock(Principal.class);
		ArrayList<Person> ergebnisListe = new ArrayList<>();
		ergebnisListe.add(personListe.get(0));
		when(principal.getName()).thenReturn("user");
		when(personService.searchByNames("Gerold")).thenReturn(ergebnisListe);
		when(personService.get(principal)).thenReturn(personListe.get(4));
		mockMvc.perform(post("/benutzersuche").principal(principal).param("query", "Gerold"))
		.andExpect(status().isOk())
		.andExpect(model().attribute("benutzerListe", ergebnisListe))
		.andExpect(model().attribute("user", personListe.get(4)));
	}

}
