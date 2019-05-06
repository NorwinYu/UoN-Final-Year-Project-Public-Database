package hhu.ausleihservice.databasemodel;

import org.junit.Assert;
import org.junit.Test;

import java.util.Base64;

import static org.junit.Assert.assertEquals;

public class ItemTests {
	@Test
	public void testGetPictureReturnsByteArrayWithSameLength() {
		Item item = new Item();
		byte[] picture = new byte[256];
		item.setPicture(picture);
		Assert.assertEquals(Base64.getEncoder().encodeToString(picture), item.getPicture());
	}

	@Test
	public void testGetPictureReturnsNullIfPictureIsNotSet() {
		Item item = new Item();
		Assert.assertNull(item.getPicture());
	}

	@Test
	public void testGetPictureReturnsEmptyByteArrayIfPictureIsEmpty() {
		Item item = new Item();
		item.setPicture(new byte[0]);
		Assert.assertEquals(0, item.getPicture().length());
	}

	@Test
	public void testSetPictureNull() {
		Item item = new Item();
		item.setPicture(null);
		Assert.assertNull(item.getPicture());
	}

	@Test
	public void addAusleiheFahrrad() {
		Person burak = new Person();
		burak.setUsername("bumar100");
		Abholort ort = new Abholort();
		ort.setBeschreibung("Zuhause");
		AusleihItem fahrrad = new AusleihItem();
		fahrrad.setId(1L);
		fahrrad.setBeschreibung("Schnell");
		fahrrad.setTitel("Mountain-Bike");
		fahrrad.setAbholort(ort);

		Ausleihe ausleihe = new Ausleihe();
		ausleihe.setAusleiher(burak);
		fahrrad.addAusleihe(ausleihe);
		Assert.assertEquals(fahrrad, ausleihe.getItem());
		Assert.assertEquals("Mountain-Bike", ausleihe.getItem().getTitel());
		Assert.assertEquals("Schnell", ausleihe.getItem().getBeschreibung());
		Assert.assertEquals("Zuhause", fahrrad.getAbholort().getBeschreibung());

	}

	@Test
	public void addAusleiheNull() {
		AusleihItem fahrrad = new AusleihItem();
		Assert.assertEquals(0, fahrrad.getAusleihen().size());
		fahrrad.addAusleihe(null);
		Assert.assertEquals(0, fahrrad.getAusleihen().size());
	}

	@Test
	public void removeAusleihe() {
		Ausleihe ausleihe = new Ausleihe();
		ausleihe.setId(1L);
		AusleihItem item = new AusleihItem();
		item.setId(2L);
		item.addAusleihe(ausleihe);
		item.removeAusleihe(ausleihe);
		Assert.assertEquals(0, item.getAusleihen().size());
		Assert.assertNull(ausleihe.getItem());
	}


	@Test
	public void setTitelToNull() {
		Item item = new Item();
		item.setTitel(null);
		assertEquals("", item.getTitel());
	}

	@Test
	public void setTitelWithNoWhiteSpace() {
		Item item = new Item();
		item.setTitel("Uganda");

		assertEquals("Uganda", item.getTitel());
	}

	@Test
	public void setTitelWithWhiteSpaceLeft() {
		Item item = new Item();
		item.setTitel("                          Uganda");

		assertEquals("Uganda", item.getTitel());
	}

	@Test
	public void setTitelWithWhiteSpaceRight() {
		Item item = new Item();
		item.setTitel("Uganda                            ");

		assertEquals("Uganda", item.getTitel());
	}

	@Test
	public void setTitelWithWhiteSpaceBothSides() {
		Item item = new Item();
		item.setTitel("                    Uganda                  ");

		assertEquals("Uganda", item.getTitel());
	}

	@Test
	public void setTitelWithWhiteSpaceInside() {
		Item item = new Item();
		item.setTitel("Uga nda");

		assertEquals("Uga nda", item.getTitel());
	}


	//Tests for setBeschreibung(String s)
	@Test
	public void setBeschreibungToNull() {
		Item item = new Item();
		item.setBeschreibung(null);

		assertEquals("", item.getBeschreibung());
	}

	@Test
	public void setBeschreibungWithNoWhiteSpace() {
		Item item = new Item();
		item.setBeschreibung("Uganda");

		assertEquals("Uganda", item.getBeschreibung());
	}

	@Test
	public void setBeschreibungWithWhiteSpaceLeft() {
		Item item = new Item();
		item.setBeschreibung("                          Uganda");

		assertEquals("Uganda", item.getBeschreibung());
	}

	@Test
	public void setBeschreibungWithWhiteSpaceRight() {
		Item item = new Item();
		item.setBeschreibung("Uganda                            ");

		assertEquals("Uganda", item.getBeschreibung());
	}

	@Test
	public void setBeschreibungWithWhiteSpaceBothSides() {
		Item item = new Item();
		item.setBeschreibung("                    Uganda                  ");

		assertEquals("Uganda", item.getBeschreibung());
	}

	@Test
	public void setBeschreibungWithWhiteSpaceInside() {
		Item item = new Item();
		item.setBeschreibung("Uga nda");

		assertEquals("Uga nda", item.getBeschreibung());
	}


	//Tests for setAbholort(Abholort a)
	@Test
	public void setAbholortToNull() {
		Item item = new Item();
		item.setAbholort(null);

		assertEquals(new Abholort(), item.getAbholort());
	}
}
