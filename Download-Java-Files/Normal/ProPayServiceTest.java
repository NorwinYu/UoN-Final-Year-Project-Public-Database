package hhu.ausleihservice.web;

import hhu.ausleihservice.databasemodel.AusleihItem;
import hhu.ausleihservice.databasemodel.Ausleihe;
import hhu.ausleihservice.databasemodel.Person;
import hhu.ausleihservice.propay.ProPayAccount;
import hhu.ausleihservice.propay.ProPayInterface;
import hhu.ausleihservice.propay.ProPayReservation;
import hhu.ausleihservice.web.service.ProPayService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ProPayServiceTest {

	@Mock
	private ProPayInterface proPayInterface = mock(ProPayInterface.class);

	private ProPayService proPayService;

	@Before
	public void init() {
		proPayService = new ProPayService(proPayInterface);
	}

	@Test
	public void testUeberweiseTagessaetzeForOneDay() {
		Ausleihe ausleihe = new Ausleihe();
		AusleihItem fahrrad = new AusleihItem();
		Person burak = new Person();
		Person simon = new Person();

		burak.setUsername("bumar100");
		simon.setUsername("siker102");
		fahrrad.setBesitzer(burak);
		fahrrad.setTagessatz(10);
		ausleihe.setItem(fahrrad);
		ausleihe.setAusleiher(simon);
		ausleihe.setStartDatum(LocalDate.now());
		ausleihe.setEndDatum(LocalDate.now().plusDays(1));

		proPayService.ueberweiseTagessaetze(ausleihe);
		verify(proPayInterface).transferFunds(ArgumentMatchers.eq("siker102"), ArgumentMatchers.eq("bumar100"),
				ArgumentMatchers.eq(20.0));
	}

	@Test
	public void testUeberweiseTagessaetzeForFiveDays() {
		Ausleihe ausleihe = new Ausleihe();
		AusleihItem fahrrad = new AusleihItem();
		Person burak = new Person();
		Person simon = new Person();

		burak.setUsername("bumar100");
		simon.setUsername("siker102");
		fahrrad.setBesitzer(burak);
		fahrrad.setTagessatz(10);
		ausleihe.setItem(fahrrad);
		ausleihe.setAusleiher(simon);
		ausleihe.setStartDatum(LocalDate.now());
		ausleihe.setEndDatum(LocalDate.now().plusDays(5));

		proPayService.ueberweiseTagessaetze(ausleihe);
		verify(proPayInterface).transferFunds(ArgumentMatchers.eq("siker102"), ArgumentMatchers.eq("bumar100"),
				ArgumentMatchers.eq(60.0));
	}

	@Test
	public void testUeberweiseTagessaetzeForZeroDays() {
		Ausleihe ausleihe = new Ausleihe();
		AusleihItem fahrrad = new AusleihItem();
		Person burak = new Person();
		Person simon = new Person();

		burak.setUsername("bumar100");
		simon.setUsername("siker102");
		fahrrad.setBesitzer(burak);
		fahrrad.setTagessatz(10);
		ausleihe.setItem(fahrrad);
		ausleihe.setAusleiher(simon);
		ausleihe.setStartDatum(LocalDate.now());
		ausleihe.setEndDatum(LocalDate.now());

		proPayService.ueberweiseTagessaetze(ausleihe);
		verify(proPayInterface).transferFunds(ArgumentMatchers.eq("siker102"), ArgumentMatchers.eq("bumar100"),
				ArgumentMatchers.eq(10.0));
	}

	@Test
	public void kautionReservieren() {
		Ausleihe ausleihe = new Ausleihe();
		AusleihItem fahrrad = new AusleihItem();
		Person burak = new Person();
		Person simon = new Person();
		burak.setUsername("bumar100");
		simon.setUsername("siker102");
		fahrrad.setBesitzer(burak);
		fahrrad.setKautionswert(300);
		ausleihe.setItem(fahrrad);
		ausleihe.setAusleiher(simon);

		ProPayReservation reservation = new ProPayReservation();
		reservation.setId(42L);
		when(proPayInterface.createReservation(anyString(), anyString(), anyDouble())).thenReturn(reservation);
		proPayService.kautionReservieren(ausleihe);

		verify(proPayInterface).createReservation(ArgumentMatchers.eq("siker102"), ArgumentMatchers.eq("bumar100"),
				ArgumentMatchers.eq(300.0));

		assertEquals(Long.valueOf(42L), ausleihe.getReservationId());
	}

	@Test
	public void punishRerservation() {
		Ausleihe ausleihe = new Ausleihe();
		AusleihItem fahrrad = new AusleihItem();
		Person burak = new Person();
		Person simon = new Person();
		burak.setUsername("bumar100");
		simon.setUsername("siker102");
		fahrrad.setBesitzer(burak);
		fahrrad.setKautionswert(300);
		ausleihe.setItem(fahrrad);
		ausleihe.setAusleiher(simon);
		ausleihe.setReservationId(22L);
		proPayService.punishRerservation(ausleihe);
		verify(proPayInterface).punishReservation(ArgumentMatchers.eq(22L), ArgumentMatchers.eq("siker102"));
	}

	@Test
	public void releaseReservation() {
		Ausleihe ausleihe = new Ausleihe();
		AusleihItem fahrrad = new AusleihItem();
		Person burak = new Person();
		Person simon = new Person();
		burak.setUsername("bumar100");
		simon.setUsername("siker102");
		fahrrad.setBesitzer(burak);
		fahrrad.setKautionswert(300);
		ausleihe.setItem(fahrrad);
		ausleihe.setAusleiher(simon);
		ausleihe.setReservationId(22L);
		proPayService.releaseReservation(ausleihe);
		verify(proPayInterface).releaseReservation(ArgumentMatchers.eq(22L), ArgumentMatchers.eq("siker102"));
	}

	@Test
	public void getProPayKontostand() {
		Person burak = new Person();
		burak.setUsername("bumar100");
		ProPayAccount account = new ProPayAccount();
		account.setAmount(1700);
		account.setAccount("dragonsleigher321412543652465426436");
		when(proPayInterface.getAccountInfo(anyString())).thenReturn(account);
		Assert.assertEquals(1700.0, proPayService.getProPayKontostand(burak), 0.0);
		verify(proPayInterface).getAccountInfo(ArgumentMatchers.eq("bumar100"));
	}

	@Test
	public void addFunds() {
		Person burak = new Person();
		burak.setUsername("bumar100");
		proPayService.addFunds(burak, 400);
		verify(proPayInterface).addFunds(ArgumentMatchers.eq("bumar100"), ArgumentMatchers.eq(400.0));

	}
}
