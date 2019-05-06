package hhu.ausleihservice.propay;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@AutoConfigureWireMock(port = 8888)
public class ProPayInterfaceTest {


	@Test
	public void testGetAccountInfo_CorrectUrl() {
		ProPayInterface proPayInterface = new ProPayInterface("localhost");

		stubFor(get(urlEqualTo("/account/John"))
				.willReturn(aResponse().withStatus(200)));


		proPayInterface.getAccountInfo("John");

		verify(getRequestedFor(urlEqualTo("/account/John")));
	}

	@Test
	public void testGetAccountInfo_ResponseConversion_1() {
		ProPayInterface proPayInterface = new ProPayInterface("localhost");

		stubFor(get(urlEqualTo("/account/John"))
				.willReturn(aResponse()
						.withStatus(200)
						.withHeader("Content-Type", "application/json")
						.withBody("{\"account\":\"John\",\"amount\":0.0,\"reservations\":[]}")));

		ProPayAccount john = proPayInterface.getAccountInfo("John");

		assertEquals("John", john.getAccount());
		assertEquals(0.0, john.getAmount(), 0.001);
		assertEquals(new HashSet<ProPayReservation>(), john.getReservations());
	}

	@Test
	public void testGetAccountInfo_ResponseConversion_2() {
		ProPayInterface proPayInterface = new ProPayInterface("localhost");

		stubFor(get(urlEqualTo("/account/John"))
				.willReturn(aResponse()
						.withStatus(200)
						.withHeader("Content-Type", "application/json")
						.withBody("{\"account\":\"John\",\"amount\":1025.0,\"reservations\":[{\"id\":1,\"" +
								"amount\":64.0},{\"id\":3,\"amount\":11.0},{\"id\":4,\"amount\":625.0}]}")));

		ProPayAccount john = proPayInterface.getAccountInfo("John");

		assertEquals("John", john.getAccount());
		assertEquals(1025.0, john.getAmount(), 0.001);
		assertEquals(3, john.getReservations().size());

		for (ProPayReservation proPayReservation : john.getReservations()) {
			if (proPayReservation.getId() == 1) {
				assertEquals(64.0, proPayReservation.getAmount(), 0.001);
			} else if (proPayReservation.getId() == 3) {
				assertEquals(11.0, proPayReservation.getAmount(), 0.001);
			} else if (proPayReservation.getId() == 4) {
				assertEquals(625.0, proPayReservation.getAmount(), 0.001);
			} else {
				assertEquals(true, false);
			}
		}
	}

	@Test
	public void testAddFunds_CorrectUrl() {
		ProPayInterface proPayInterface = new ProPayInterface("localhost");

		stubFor(post(urlEqualTo("/account/John?amount=413.0"))
				.withQueryParam("amount", equalTo("413.0"))
				.willReturn(aResponse().withStatus(200)));

		proPayInterface.addFunds("John", 413.0);

		verify(postRequestedFor(urlEqualTo("/account/John?amount=413.0"))
				.withQueryParam("amount", equalTo("413.0")));
	}

	@Test
	public void testAddFunds_ResponseConversion() {
		ProPayInterface proPayInterface = new ProPayInterface("localhost");

		stubFor(post(urlEqualTo("/account/John?amount=413.0"))
				.withQueryParam("amount", equalTo("413.0"))
				.willReturn(aResponse()
						.withStatus(200)
						.withHeader("Content-Type", "application/json")
						.withBody("{\"account\":\"John\",\"amount\":413.0,\"reservations\":[]}")));

		ProPayAccount john = proPayInterface.addFunds("John", 413.0);

		assertEquals("John", john.getAccount());
		assertEquals(413.0, john.getAmount(), 0.001);
		assertEquals(new HashSet<ProPayReservation>(), john.getReservations());
	}

	@Test
	public void testTransferFunds_CorrectUrl() {
		ProPayInterface proPayInterface = new ProPayInterface("localhost");

		stubFor(post(urlEqualTo("/account/John/transfer/Jade?amount=413.0"))
				.withQueryParam("amount", equalTo("413.0"))
				.willReturn(aResponse().withStatus(200)));

		proPayInterface.transferFunds("John", "Jade", 413.0);

		verify(postRequestedFor(urlEqualTo("/account/John/transfer/Jade?amount=413.0"))
				.withQueryParam("amount", equalTo("413.0")));
	}

	@Test
	public void testCreateReservation_CorrectUrl() {
		ProPayInterface proPayInterface = new ProPayInterface("localhost");

		stubFor(post(urlEqualTo("/reservation/reserve/John/Jade?amount=413.0"))
				.withQueryParam("amount", equalTo("413.0"))
				.willReturn(aResponse().withStatus(200)));

		proPayInterface.createReservation("John", "Jade", 413.0);

		verify(postRequestedFor(urlEqualTo("/reservation/reserve/John/Jade?amount=413.0"))
				.withQueryParam("amount", equalTo("413.0")));
	}

	@Test
	public void testCreateReservation_ResponseConversion() {
		ProPayInterface proPayInterface = new ProPayInterface("localhost");

		stubFor(post(urlEqualTo("/reservation/reserve/John/Jade?amount=413.0"))
				.withQueryParam("amount", equalTo("413.0"))
				.willReturn(aResponse()
						.withStatus(200)
						.withHeader("Content-Type", "application/json")
						.withBody("{\"id\":1,\"amount\":413.0}")));

		ProPayReservation johnToJade = proPayInterface.createReservation("John", "Jade", 413.0);

		assertEquals(1, johnToJade.getId());
		assertEquals(413.0, johnToJade.getAmount(), 0.001);
	}

	@Test
	public void testReleaseReservation_CorrectUrl() {
		ProPayInterface proPayInterface = new ProPayInterface("localhost");

		stubFor(post(urlEqualTo("/reservation/release/John?reservationId=1"))
				.withQueryParam("reservationId", equalTo("1"))
				.willReturn(aResponse().withStatus(200)));

		proPayInterface.releaseReservation(1, "John");

		verify(postRequestedFor(urlEqualTo("/reservation/release/John?reservationId=1"))
				.withQueryParam("reservationId", equalTo("1")));
	}

	@Test
	public void testReleaseReservation_ResponseConversion() {
		ProPayInterface proPayInterface = new ProPayInterface("localhost");

		stubFor(post(urlEqualTo("/reservation/release/John?reservationId=1"))
				.withQueryParam("reservationId", equalTo("1"))
				.willReturn(aResponse()
						.withStatus(200)
						.withHeader("Content-Type", "application/json")
						.withBody("{\"account\":\"John\",\"amount\":0.0,\"reservations\":[]}")));

		ProPayAccount john = proPayInterface.releaseReservation(1, "John");

		assertEquals("John", john.getAccount());
		assertEquals(0.0, john.getAmount(), 0.001);
		assertEquals(new HashSet<ProPayReservation>(), john.getReservations());
	}

	@Test
	public void testPunishReservation_CorrectUrl() {
		ProPayInterface proPayInterface = new ProPayInterface("localhost");

		stubFor(post(urlEqualTo("/reservation/punish/John?reservationId=1"))
				.withQueryParam("reservationId", equalTo("1"))
				.willReturn(aResponse().withStatus(200)));

		proPayInterface.punishReservation(1, "John");

		verify(postRequestedFor(urlEqualTo("/reservation/punish/John?reservationId=1"))
				.withQueryParam("reservationId", equalTo("1")));
	}

	@Test
	public void testPunishReservation_ResponseConversion() {
		ProPayInterface proPayInterface = new ProPayInterface("localhost");

		stubFor(post(urlEqualTo("/reservation/punish/John?reservationId=1"))
				.withQueryParam("reservationId", equalTo("1"))
				.willReturn(aResponse()
						.withStatus(200)
						.withHeader("Content-Type", "application/json")
						.withBody("{\"account\":\"John\",\"amount\":0.0,\"reservations\":[]}")));

		ProPayAccount john = proPayInterface.punishReservation(1, "John");

		assertEquals("John", john.getAccount());
		assertEquals(0.0, john.getAmount(), 0.001);
		assertEquals(new HashSet<ProPayReservation>(), john.getReservations());
	}
}

