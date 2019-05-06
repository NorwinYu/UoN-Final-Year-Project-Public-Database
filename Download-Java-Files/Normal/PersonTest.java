package hhu.ausleihservice.databasemodel;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class PersonTest {

	@Test
	public void addAusleihe() {
		Ausleihe ausleihe = new Ausleihe();
		ausleihe.setId(1L);
		Person person = new Person();
		person.setUsername("bumar100");
		person.addAusleihe(ausleihe);
		Assert.assertEquals(person.getAusleihen().iterator().next(), ausleihe);
		Assert.assertEquals(person, ausleihe.getAusleiher());
	}

	@Test
	public void removeAusleihe() {
		Ausleihe ausleihe = new Ausleihe();
		ausleihe.setId(1L);
		Person person = new Person();
		person.setUsername("bumar100");

		person.addAusleihe(ausleihe);
		Assert.assertTrue(person.getAusleihen().contains(ausleihe));
		person.removeAusleihe(ausleihe);
		Assert.assertFalse(person.getAusleihen().contains(ausleihe));
	}


	@Test
	public void addItem() {
		Person person = new Person();
		person.setUsername("bumar100");
		AusleihItem item = new AusleihItem();
		item.setId(1L);
		person.addItem(item);
		Assert.assertEquals(person.getItems().iterator().next(), item);
		Assert.assertEquals(item.getBesitzer(), person);
		Assert.assertTrue(person.getItems().contains(item));
		Assert.assertEquals(1, person.getItems().size());
	}

	@Test
	public void removeItem() {
		Person person = new Person();
		person.setUsername("bumar100");
		AusleihItem item = new AusleihItem();
		item.setId(1L);
		person.addItem(item);

		person.removeItem(item);
		Assert.assertFalse(person.getItems().contains(item));
		Assert.assertEquals(0, person.getItems().size());
	}

	@Test
	public void thisTestCanBeRemoved() {
		Person person = new Person();
		Abholort ort = new Abholort();
		Set<Abholort> abholort = new HashSet<>();
		abholort.add(ort);
		ort.setId(55L);
		person.setId(1L);
		person.setNachname("Martin");
		person.setVorname("Burak");
		person.setPassword("123");    //Top secret
		person.setRole(Role.ADMIN);
		person.setEmail("mail");
		person.setAbholorte(abholort);

		Assert.assertEquals(Long.valueOf(1L), person.getId());
		Assert.assertEquals("Burak", person.getVorname());
		Assert.assertEquals("Martin", person.getNachname());
		Assert.assertEquals("123", person.getPassword());
		Assert.assertEquals(Role.ADMIN, person.getRole());
		Assert.assertEquals("mail", person.getEmail());
		Assert.assertEquals(ort, person.getAbholorte().iterator().next());
	}


	//Tests for setNachname(String s)
	@Test
	public void setNachnameToNull() {
		Person person = new Person();
		person.setNachname(null);

		assertEquals("", person.getNachname());
	}

	@Test
	public void setNachnameWithNoWhiteSpace() {
		Person person = new Person();
		person.setNachname("Uganda");

		assertEquals("Uganda", person.getNachname());
	}

	@Test
	public void setNachnameWithWhiteSpaceLeft() {
		Person person = new Person();
		person.setNachname("                          Uganda");

		assertEquals("Uganda", person.getNachname());
	}

	@Test
	public void setNachnameWithWhiteSpaceRight() {
		Person person = new Person();
		person.setNachname("Uganda                            ");

		assertEquals("Uganda", person.getNachname());
	}

	@Test
	public void setNachnameWithWhiteSpaceBothSides() {
		Person person = new Person();
		person.setNachname("                    Uganda                  ");

		assertEquals("Uganda", person.getNachname());
	}

	@Test
	public void setNachnameWithWhiteSpaceInside() {
		Person person = new Person();
		person.setNachname("Uga nda");

		assertEquals("Uga nda", person.getNachname());
	}


	//Tests for setVorname(String s)
	@Test
	public void setVornameToNull() {
		Person person = new Person();
		person.setVorname(null);

		assertEquals("", person.getVorname());
	}

	@Test
	public void setVornameWithNoWhiteSpace() {
		Person person = new Person();
		person.setVorname("Uganda");

		assertEquals("Uganda", person.getVorname());
	}

	@Test
	public void setVornameWithWhiteSpaceLeft() {
		Person person = new Person();
		person.setVorname("                          Uganda");

		assertEquals("Uganda", person.getVorname());
	}

	@Test
	public void setVornameWithWhiteSpaceRight() {
		Person person = new Person();
		person.setVorname("Uganda                            ");

		assertEquals("Uganda", person.getVorname());
	}

	@Test
	public void setVornameWithWhiteSpaceBothSides() {
		Person person = new Person();
		person.setVorname("                    Uganda                  ");

		assertEquals("Uganda", person.getVorname());
	}

	@Test
	public void setVornameWithWhiteSpaceInside() {
		Person person = new Person();
		person.setVorname("Uga nda");

		assertEquals("Uga nda", person.getVorname());
	}


	//Tests for setUsername(String s)
	@Test
	public void setUsernameToNull() {
		Person person = new Person();
		person.setUsername(null);

		assertEquals("", person.getUsername());
	}

	@Test
	public void setUsernameWithNoWhiteSpace() {
		Person person = new Person();
		person.setUsername("Uganda");

		assertEquals("Uganda", person.getUsername());
	}

	@Test
	public void setUsernameWithWhiteSpaceLeft() {
		Person person = new Person();
		person.setUsername("                          Uganda");

		assertEquals("Uganda", person.getUsername());
	}

	@Test
	public void setUsernameWithWhiteSpaceRight() {
		Person person = new Person();
		person.setUsername("Uganda                            ");

		assertEquals("Uganda", person.getUsername());
	}

	@Test
	public void setUsernameWithWhiteSpaceBothSides() {
		Person person = new Person();
		person.setUsername("                    Uganda                  ");

		assertEquals("Uganda", person.getUsername());
	}

	@Test
	public void setUsernameWithWhiteSpaceInside() {
		Person person = new Person();
		person.setUsername("Uga nda");

		assertEquals("Uga nda", person.getUsername());
	}


	//Tests for setEmail(String s)
	@Test
	public void setEmailToNull() {
		Person person = new Person();
		person.setEmail(null);

		assertEquals("", person.getEmail());
	}

	@Test
	public void setEmailWithNoWhiteSpace() {
		Person person = new Person();
		person.setEmail("Uganda");

		assertEquals("Uganda", person.getEmail());
	}

	@Test
	public void setEmailWithWhiteSpaceLeft() {
		Person person = new Person();
		person.setEmail("                          Uganda");

		assertEquals("Uganda", person.getEmail());
	}

	@Test
	public void setEmailWithWhiteSpaceRight() {
		Person person = new Person();
		person.setEmail("Uganda                            ");

		assertEquals("Uganda", person.getEmail());
	}

	@Test
	public void setEmailWithWhiteSpaceBothSides() {
		Person person = new Person();
		person.setEmail("                    Uganda                  ");

		assertEquals("Uganda", person.getEmail());
	}

	@Test
	public void setEmailWithWhiteSpaceInside() {
		Person person = new Person();
		person.setEmail("Uga nda");

		assertEquals("Uga nda", person.getEmail());
	}
}
