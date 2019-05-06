package hhu.ausleihservice.databasemodel;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AbholortTest {

	//Tests for setBeschreibung(String s)
	@Test
	public void setBeschreibungToNull() {
		Abholort abholort = new Abholort();
		abholort.setBeschreibung(null);

		assertEquals("", abholort.getBeschreibung());
	}

	@Test
	public void setBeschreibungWithNoWhiteSpace() {
		Abholort abholort = new Abholort();
		abholort.setBeschreibung("Uganda");

		assertEquals("Uganda", abholort.getBeschreibung());
	}

	@Test
	public void setBeschreibungWithWhiteSpaceLeft() {
		Abholort abholort = new Abholort();
		abholort.setBeschreibung("                          Uganda");

		assertEquals("Uganda", abholort.getBeschreibung());
	}

	@Test
	public void setBeschreibungWithWhiteSpaceRight() {
		Abholort abholort = new Abholort();
		abholort.setBeschreibung("Uganda                            ");

		assertEquals("Uganda", abholort.getBeschreibung());
	}

	@Test
	public void setBeschreibungWithWhiteSpaceBothSides() {
		Abholort abholort = new Abholort();
		abholort.setBeschreibung("                    Uganda                  ");

		assertEquals("Uganda", abholort.getBeschreibung());
	}

	@Test
	public void setBeschreibungWithWhiteSpaceInside() {
		Abholort abholort = new Abholort();
		abholort.setBeschreibung("Uga nda");

		assertEquals("Uga nda", abholort.getBeschreibung());
	}
}
