package hhu.ausleihservice.validators;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;

import hhu.ausleihservice.databasemodel.AusleihItem;
import hhu.ausleihservice.databasemodel.Ausleihe;
import hhu.ausleihservice.databasemodel.Person;
import hhu.ausleihservice.web.service.ItemAvailabilityService;
import hhu.ausleihservice.web.service.ProPayService;

public class AusleiheValidatorTest {

	@Test
	public void startDatumAfterEndDatum() {
		ItemAvailabilityService availabilityService = mock(ItemAvailabilityService.class);
		ProPayService proPayService = mock(ProPayService.class);
		AusleihItem ausleiheItem = mock(AusleihItem.class);
		AusleiheValidator ausleiheValidator = new AusleiheValidator(availabilityService, proPayService);
		Ausleihe ausleihe = new Ausleihe();
		ausleihe.setStartDatum(LocalDate.of(2000, 5, 5));
		ausleihe.setEndDatum(LocalDate.of(2000, 5, 4));
		ausleihe.setItem(ausleiheItem);
		ausleihe.setAusleiher(new Person());
		when(ausleiheItem.getBesitzer()).thenReturn(new Person());

		when(availabilityService.isAvailableFromTill(ausleiheItem, ausleihe.getStartDatum(), ausleihe.getEndDatum()))
				.thenReturn(false);

		DataBinder dataBinder = new DataBinder(ausleihe);
		dataBinder.setValidator(ausleiheValidator);
		dataBinder.validate();
		BindingResult bindingResult = dataBinder.getBindingResult();
		assertTrue(bindingResult.hasFieldErrors("startDatum"));
		assertEquals(Messages.itemNotAvailable, bindingResult.getFieldError("startDatum").getCode());
	}

	@Test
	public void startDatumAndEndDatumAreTheSame() {
		ItemAvailabilityService availabilityService = mock(ItemAvailabilityService.class);
		AusleihItem ausleiheItem = mock(AusleihItem.class);
		ProPayService proPayService = mock(ProPayService.class);
		AusleiheValidator ausleiheValidator = new AusleiheValidator(availabilityService, proPayService);
		Ausleihe ausleihe = new Ausleihe();
		ausleihe.setStartDatum(LocalDate.of(2019, 5, 5));
		ausleihe.setEndDatum(LocalDate.of(2019, 5, 5));
		Set<Ausleihe> ausleihen = new HashSet<>();
		ausleiheItem.setAusleihen(ausleihen);
		when(ausleiheItem.getAvailableFrom()).thenReturn(LocalDate.of(2019, 5, 4));
		when(ausleiheItem.getAvailableTill()).thenReturn(LocalDate.of(2019, 5, 6));
		ausleihe.setAusleiher(new Person());
		when(ausleiheItem.getBesitzer()).thenReturn(new Person());
		ausleihe.setItem(ausleiheItem);
		when(availabilityService.isAvailableFromTill(ausleiheItem, ausleihe.getStartDatum(), ausleihe.getEndDatum()))
				.thenReturn(true);

		DataBinder dataBinder = new DataBinder(ausleihe);
		dataBinder.setValidator(ausleiheValidator);
		dataBinder.validate();
		BindingResult bindingResult = dataBinder.getBindingResult();
		assertFalse(bindingResult.hasFieldErrors("startDatum"));
	}

	@Test
	public void itemIsEmpty() {
		ItemAvailabilityService availabilityService = mock(ItemAvailabilityService.class);
		ProPayService proPayService = mock(ProPayService.class);
		AusleiheValidator ausleiheValidator = new AusleiheValidator(availabilityService, proPayService);
		Ausleihe ausleihe = new Ausleihe();
		ausleihe.setStartDatum(LocalDate.of(2019, 5, 5));
		ausleihe.setEndDatum(LocalDate.of(2019, 5, 5));
		ausleihe.setAusleiher(new Person());

		DataBinder dataBinder = new DataBinder(ausleihe);
		dataBinder.setValidator(ausleiheValidator);
		dataBinder.validate();
		BindingResult bindingResult = dataBinder.getBindingResult();
		assertTrue(bindingResult.hasFieldErrors("item"));
		assertEquals(Messages.notEmpty, bindingResult.getFieldError("item").getCode());
	}

	@Test
	public void leiheEigenesItemAus() {
		Person person = mock(Person.class);
		ItemAvailabilityService availabilityService = mock(ItemAvailabilityService.class);
		ProPayService proPayService = mock(ProPayService.class);
		AusleihItem ausleiheItem = mock(AusleihItem.class);
		when(ausleiheItem.getBesitzer()).thenReturn(person);
		AusleiheValidator ausleiheValidator = new AusleiheValidator(availabilityService, proPayService);
		Ausleihe ausleihe = new Ausleihe();
		ausleihe.setStartDatum(LocalDate.of(2019, 1, 5));
		ausleihe.setEndDatum(LocalDate.of(2019, 5, 5));
		ausleihe.setItem(ausleiheItem);
		ausleihe.setAusleiher(person);
		Set<Ausleihe> ausleihen = new HashSet<>();
		ausleiheItem.setAusleihen(ausleihen);
		when(ausleiheItem.getAvailableFrom()).thenReturn(LocalDate.of(2019, 2, 5));
		when(ausleiheItem.getAvailableTill()).thenReturn(LocalDate.of(2019, 3, 5));

		when(availabilityService.isAvailableFromTill(ausleiheItem, ausleihe.getStartDatum(), ausleihe.getEndDatum()))
				.thenReturn(true);

		DataBinder dataBinder = new DataBinder(ausleihe);
		dataBinder.setValidator(ausleiheValidator);
		dataBinder.validate();
		BindingResult bindingResult = dataBinder.getBindingResult();
		assertTrue(bindingResult.hasFieldErrors("ausleiher"));
		assertEquals(Messages.ownItemAusleihe, bindingResult.getFieldError("ausleiher").getCode());
	}

	@Test
	public void emptyAusleiher() {
		ItemAvailabilityService availabilityService = mock(ItemAvailabilityService.class);
		ProPayService proPayService = mock(ProPayService.class);
		AusleihItem ausleiheItem = mock(AusleihItem.class);
		when(ausleiheItem.getBesitzer()).thenReturn(null);
		AusleiheValidator ausleiheValidator = new AusleiheValidator(availabilityService, proPayService);
		Ausleihe ausleihe = new Ausleihe();
		ausleihe.setStartDatum(LocalDate.of(2019, 1, 5));
		ausleihe.setEndDatum(LocalDate.of(2019, 5, 5));
		ausleihe.setItem(ausleiheItem);
		Set<Ausleihe> ausleihen = new HashSet<>();
		ausleiheItem.setAusleihen(ausleihen);
		when(ausleiheItem.getAvailableFrom()).thenReturn(LocalDate.of(2019, 2, 5));
		when(ausleiheItem.getAvailableTill()).thenReturn(LocalDate.of(2019, 3, 5));

		when(availabilityService.isAvailableFromTill(ausleiheItem, ausleihe.getStartDatum(), ausleihe.getEndDatum()))
				.thenReturn(true);

		DataBinder dataBinder = new DataBinder(ausleihe);
		dataBinder.setValidator(ausleiheValidator);
		dataBinder.validate();
		BindingResult bindingResult = dataBinder.getBindingResult();
		assertEquals(Messages.notEmpty, bindingResult.getFieldError("ausleiher").getCode());

	}

	@Test
	public void itemIsAlreadyAusgeliehen() {
		Ausleihe otherAusleihe = mock(Ausleihe.class);
		when(otherAusleihe.getStartDatum()).thenReturn(LocalDate.of(2019, 5, 2));
		when(otherAusleihe.getEndDatum()).thenReturn(LocalDate.of(2019, 5, 4));


		ItemAvailabilityService availabilityService = mock(ItemAvailabilityService.class);

		ProPayService proPayService = mock(ProPayService.class);
		AusleihItem ausleiheItem = mock(AusleihItem.class);
		AusleiheValidator ausleiheValidator = new AusleiheValidator(availabilityService, proPayService);

		Ausleihe ausleihe = new Ausleihe();
		ausleihe.setStartDatum(LocalDate.of(2019, 5, 3));
		ausleihe.setEndDatum(LocalDate.of(2019, 5, 3));
		ausleihe.setItem(ausleiheItem);
		Set<Ausleihe> ausleihen = new HashSet<>();
		ausleihen.add(otherAusleihe);
		when(ausleiheItem.getAusleihen()).thenReturn(ausleihen);
		when(ausleiheItem.getAvailableFrom()).thenReturn(LocalDate.of(2019, 5, 1));
		when(ausleiheItem.getAvailableTill()).thenReturn(LocalDate.of(2019, 5, 5));

		DataBinder dataBinder = new DataBinder(ausleihe);
		dataBinder.setValidator(ausleiheValidator);
		dataBinder.validate();
		BindingResult bindingResult = dataBinder.getBindingResult();
		assertEquals(Messages.itemNotAvailable, bindingResult.getFieldError("startDatum").getCode());
	}
}
