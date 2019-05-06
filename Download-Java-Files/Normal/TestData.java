package hhu.ausleihservice.web;

import hhu.ausleihservice.databasemodel.*;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class TestData {
	List<Abholort> abholortList;
	List<Person> personList;
	Set<Person> personSet;
	List<AusleihItem> ausleihItemList;
	Set<Item> itemSet;
	List<Ausleihe> ausleiheList;

	public TestData() {
		createLists();
	}

	private void createLists() {
		//Abholorte
		abholortList = new ArrayList<>();

		Abholort ort1 = new Abholort();
		Abholort ort2 = new Abholort();
		Abholort ort3 = new Abholort();
		Abholort ort4 = new Abholort();

		ort1.setBeschreibung("Höhle");
		ort2.setBeschreibung("Garage");
		ort3.setBeschreibung("Haus");
		ort4.setBeschreibung("Verloren");

		ort1.setLatitude(51.227741);
		ort1.setLongitude(6.773456);
		ort2.setLatitude(51.227741);
		ort2.setLongitude(6.773456);
		ort3.setLatitude(51.227741);
		ort3.setLongitude(6.773456);
		ort4.setLatitude(51.227741);
		ort4.setLongitude(6.773456);

		abholortList.add(ort1);
		abholortList.add(ort2);
		abholortList.add(ort3);
		abholortList.add(ort4);

		Set<Abholort> orte1 = new HashSet<>();
		Set<Abholort> orte2 = new HashSet<>();
		Set<Abholort> orte3 = new HashSet<>();

		orte1.add(ort1);
		orte2.add(ort2);
		orte2.add(ort3);
		orte3.add(ort4);

		//Items
		ausleihItemList = new ArrayList<>();

		AusleihItem item1 = new AusleihItem();
		AusleihItem item2 = new AusleihItem();
		AusleihItem item3 = new AusleihItem();

		item1.setTitel("Stift");
		item2.setTitel("Fahrrad");
		item3.setTitel("Pfeil");

		item1.setBeschreibung("Zum stiften gehen");
		item2.setBeschreibung("Falls man sich radlos fühlt");
		item3.setBeschreibung("Wenn man den Bogen schon raus hat");

		item1.setTagessatz(3);
		item2.setTagessatz(8);
		item3.setTagessatz(100);

		item1.setKautionswert(34);
		item2.setKautionswert(1245);
		item3.setKautionswert(55);

		LocalDate ersterMai = LocalDate.of(2019, 5, 1);

		item1.setAvailableFrom(ersterMai.plusDays(1));
		item2.setAvailableFrom(ersterMai.plusDays(3));
		item3.setAvailableFrom(ersterMai.plusDays(4));

		item1.setAvailableTill(ersterMai.plusDays(8));
		item2.setAvailableTill(ersterMai.plusDays(10));
		item3.setAvailableTill(ersterMai.plusDays(11));

		ausleihItemList.add(item1);
		ausleihItemList.add(item2);
		ausleihItemList.add(item3);

		//Personen
		personList = new ArrayList<>();

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

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		person1.setPassword(encoder.encode("pl4tsh"));
		person2.setPassword(encoder.encode("rumbl7"));
		person3.setPassword(encoder.encode("ichwillschlafen123"));
		person4.setPassword(encoder.encode("123"));
		person5.setPassword(encoder.encode("123"));

		person1.setRole(Role.ADMIN); //Role.USER is automatically set in constructor
		person4.setRole(Role.ADMIN);

		person1.setEmail("sleeping@home.com");
		person2.setEmail("notWorking@uni.com");
		person3.setEmail("screaming@computer.de");
		person4.setEmail("admin@uni-dusseldorf.de");
		person5.setEmail("asdf@gmail.com");

		personList.add(person1);
		personList.add(person2);
		personList.add(person3);
		personList.add(person4);
		personList.add(person5);

		//Ausleihe
		ausleiheList = new ArrayList<>();

		Ausleihe ausleihe1 = new Ausleihe();
		ausleihe1.setReservationId(0L);
		ausleihe1.setStartDatum(ersterMai.plusDays(4));
		ausleihe1.setEndDatum(ersterMai.plusDays(6));

		Ausleihe ausleihe2 = new Ausleihe();
		ausleihe2.setReservationId(1L);
		ausleihe2.setStartDatum(ersterMai.plusDays(2));
		ausleihe2.setEndDatum(ersterMai.plusDays(3));
		ausleihe2.setAusleiher(person3);
		ausleihe2.setKonflikt(true);

		ausleiheList.add(ausleihe1);
		ausleiheList.add(ausleihe2);

		//combine data
		item1.setAbholort(ort1);
		item2.setAbholort(ort3);
		item3.setAbholort(ort4);

		person1.setAbholorte(orte1);
		person2.setAbholorte(orte2);
		person3.setAbholorte(orte3);

		person1.addItem(item1);
		person2.addItem(item2);
		person3.addItem(item3);

		item3.addAusleihe(ausleihe1);
		item1.addAusleihe(ausleihe2);
		person2.addAusleihe(ausleihe1);
		person3.addAusleihe(ausleihe2);
	}

}
