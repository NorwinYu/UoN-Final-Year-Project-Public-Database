package hhu.ausleihservice.web;

import hhu.ausleihservice.databasemodel.Ausleihe;
import hhu.ausleihservice.databasemodel.Status;
import hhu.ausleihservice.web.service.AusleiheService;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class AusleiheServiceTest {

	private AusleiheService ausleiheService;

	@Before
	public void prepareTests() {
		ausleiheService = new AusleiheService(null);
	}

	@Test
	public void testUpdateAusleihenIfTooLate_1_nullList() {
		List<Ausleihe> testInputList = null;
		LocalDate testInputDate = LocalDate.of(2000, 1, 1);

		ausleiheService.update(testInputList, testInputDate);

		assertNull(testInputList);
	}

	@Test
	public void testUpdateAusleihenIfTooLate_2_emptyList() {
		List<Ausleihe> testInputList = new ArrayList<>();
		LocalDate testInputDate = LocalDate.of(2000, 1, 1);

		ausleiheService.update(testInputList, testInputDate);

		assertEquals(0, testInputList.size());
	}

	@Test
	public void testUpdateAusleihenIfTooLate_3() {
		Ausleihe ausleihe1 = new Ausleihe();
		Ausleihe ausleihe2 = new Ausleihe();
		Ausleihe ausleihe3 = new Ausleihe();
		Ausleihe ausleihe4 = new Ausleihe();

		ausleihe1.setStatus(Status.ANGEFRAGT);
		ausleihe2.setStatus(Status.RUECKGABE_VERPASST);
		ausleihe3.setStatus(Status.AUSGELIEHEN);
		ausleihe4.setStatus(Status.AUSGELIEHEN);

		ausleihe1.setEndDatum(LocalDate.of(2005, 1, 1));
		ausleihe2.setEndDatum(LocalDate.of(2005, 1, 1));
		ausleihe3.setEndDatum(LocalDate.of(2005, 1, 1));
		ausleihe4.setEndDatum(LocalDate.of(2005, 1, 1));

		List<Ausleihe> testInputList = new ArrayList<>();

		testInputList.add(ausleihe1);
		testInputList.add(ausleihe2);
		testInputList.add(ausleihe3);
		testInputList.add(ausleihe4);

		LocalDate testInputDate = LocalDate.of(2000, 1, 1);

		ausleiheService.update(testInputList, testInputDate);

		assertEquals(4, testInputList.size());

		assertEquals(Status.ANGEFRAGT, testInputList.get(0).getStatus());
		assertEquals(Status.RUECKGABE_VERPASST, testInputList.get(1).getStatus());
		assertEquals(Status.AUSGELIEHEN, testInputList.get(2).getStatus());
		assertEquals(Status.AUSGELIEHEN, testInputList.get(3).getStatus());
	}

	@Test
	public void testUpdateAusleihenIfTooLate_4() {
		Ausleihe ausleihe1 = new Ausleihe();
		Ausleihe ausleihe2 = new Ausleihe();
		Ausleihe ausleihe3 = new Ausleihe();
		Ausleihe ausleihe4 = new Ausleihe();
		Ausleihe ausleihe5 = new Ausleihe();
		Ausleihe ausleihe6 = new Ausleihe();

		ausleihe1.setStatus(Status.VERKAUFT);
		ausleihe2.setStatus(Status.RUECKGABE_VERPASST);
		ausleihe3.setStatus(Status.AUSGELIEHEN);
		ausleihe4.setStatus(Status.AUSGELIEHEN);
		ausleihe5.setStatus(Status.AUSGELIEHEN);
		ausleihe6.setStatus(Status.AUSGELIEHEN);

		ausleihe1.setEndDatum(LocalDate.of(2005, 1, 1));
		ausleihe2.setEndDatum(LocalDate.of(2005, 1, 1));
		ausleihe3.setEndDatum(LocalDate.of(2005, 1, 1));
		ausleihe4.setEndDatum(LocalDate.of(2000, 1, 1));
		ausleihe5.setEndDatum(LocalDate.of(1999, 1, 1));
		ausleihe6.setEndDatum(LocalDate.of(1999, 12, 31));

		List<Ausleihe> testInputList = new ArrayList<>();

		testInputList.add(ausleihe1);
		testInputList.add(ausleihe2);
		testInputList.add(ausleihe3);
		testInputList.add(ausleihe4);
		testInputList.add(ausleihe5);
		testInputList.add(ausleihe6);

		LocalDate testInputDate = LocalDate.of(2000, 1, 1);

		ausleiheService.update(testInputList, testInputDate);

		assertEquals(6, testInputList.size());

		assertEquals(Status.VERKAUFT, testInputList.get(0).getStatus());
		assertEquals(Status.RUECKGABE_VERPASST, testInputList.get(1).getStatus());
		assertEquals(Status.AUSGELIEHEN, testInputList.get(2).getStatus());
		assertEquals(Status.AUSGELIEHEN, testInputList.get(3).getStatus());
		assertEquals(Status.RUECKGABE_VERPASST, testInputList.get(4).getStatus());
		assertEquals(Status.RUECKGABE_VERPASST, testInputList.get(5).getStatus());
	}

	@Test
	public void testFindLateAusleihen_1() {
		Ausleihe ausleihe1 = new Ausleihe();
		Ausleihe ausleihe2 = new Ausleihe();
		Ausleihe ausleihe3 = new Ausleihe();
		Ausleihe ausleihe4 = new Ausleihe();
		Ausleihe ausleihe5 = new Ausleihe();
		Ausleihe ausleihe6 = new Ausleihe();

		ausleihe1.setStatus(Status.VERKAUFT);
		ausleihe2.setStatus(Status.BESTAETIGT);
		ausleihe3.setStatus(Status.AUSGELIEHEN);
		ausleihe4.setStatus(Status.AUSGELIEHEN);
		ausleihe5.setStatus(Status.AUSGELIEHEN);
		ausleihe6.setStatus(Status.AUSGELIEHEN);

		List<Ausleihe> testInputList = new ArrayList<>();

		testInputList.add(ausleihe1);
		testInputList.add(ausleihe2);
		testInputList.add(ausleihe3);
		testInputList.add(ausleihe4);
		testInputList.add(ausleihe5);
		testInputList.add(ausleihe6);

		List<Ausleihe> testOutputList = ausleiheService.findLateAusleihen(testInputList);

		assertEquals(0, testOutputList.size());
	}

	@Test
	public void testFindLateAusleihen_2() {
		Ausleihe ausleihe1 = new Ausleihe();
		Ausleihe ausleihe2 = new Ausleihe();
		Ausleihe ausleihe3 = new Ausleihe();
		Ausleihe ausleihe4 = new Ausleihe();
		Ausleihe ausleihe5 = new Ausleihe();
		Ausleihe ausleihe6 = new Ausleihe();

		ausleihe1.setStatus(Status.VERKAUFT);
		ausleihe2.setStatus(Status.BESTAETIGT);
		ausleihe3.setStatus(Status.RUECKGABE_VERPASST);
		ausleihe4.setStatus(Status.AUSGELIEHEN);
		ausleihe5.setStatus(Status.AUSGELIEHEN);
		ausleihe6.setStatus(Status.RUECKGABE_VERPASST);

		List<Ausleihe> testInputList = new ArrayList<>();

		testInputList.add(ausleihe1);
		testInputList.add(ausleihe2);
		testInputList.add(ausleihe3);
		testInputList.add(ausleihe4);
		testInputList.add(ausleihe5);
		testInputList.add(ausleihe6);

		List<Ausleihe> testOutputList = ausleiheService.findLateAusleihen(testInputList);

		assertEquals(2, testOutputList.size());

		assertEquals(Status.RUECKGABE_VERPASST, testOutputList.get(0).getStatus());
		assertEquals(Status.RUECKGABE_VERPASST, testOutputList.get(1).getStatus());
	}
}
