package hhu.ausleihservice.web;

import hhu.ausleihservice.dataaccess.AusleihItemRepository;
import hhu.ausleihservice.databasemodel.Abholort;
import hhu.ausleihservice.databasemodel.AusleihItem;
import hhu.ausleihservice.databasemodel.Person;
import hhu.ausleihservice.web.service.AusleihItemService;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AusleihItemServiceTest {

	//Tests for updateById
	@Test
	public void updateByIdAbholortEmpty() {
		Abholort oldAbholort = new Abholort();
		oldAbholort.setId(0L);
		oldAbholort.setLatitude(-53.04379);
		oldAbholort.setLongitude(72.59947);
		oldAbholort.setBeschreibung("McDonald's");

		AusleihItem oldItem = new AusleihItem();
		oldItem.setId(0L);
		oldItem.setTitel("Orange");
		oldItem.setBeschreibung("Um seinen Code besser zu orrangieren");
		oldItem.setAbholort(oldAbholort);
		oldItem.setBesitzer(new Person());
		oldItem.setTagessatz(4);
		oldItem.setKautionswert(20);
		oldItem.setAvailableFrom(LocalDate.now().plusDays(5));
		oldItem.setAvailableTill(LocalDate.now().plusDays(7));

		AusleihItem newItem = new AusleihItem();

		Optional<AusleihItem> optionalOldItem = Optional.of(oldItem);
		AusleihItemRepository repo = mock(AusleihItemRepository.class);
		when(repo.findById(0L)).thenReturn(optionalOldItem);
		AusleihItemService service = new AusleihItemService(repo);

		service.updateById(0L, newItem);

		assertEquals(oldAbholort, service.findById(0L).getAbholort());
	}

	@Test
	public void updateByIdAbholortNew() {
		Abholort oldAbholort = new Abholort();
		oldAbholort.setId(0L);
		oldAbholort.setLatitude(-53.04379);
		oldAbholort.setLongitude(72.59947);
		oldAbholort.setBeschreibung("McDonald's");

		AusleihItem oldItem = new AusleihItem();
		oldItem.setId(0L);
		oldItem.setTitel("Orange");
		oldItem.setBeschreibung("Um seinen Code besser zu orrangieren");
		oldItem.setAbholort(oldAbholort);
		oldItem.setBesitzer(new Person());
		oldItem.setTagessatz(4);
		oldItem.setKautionswert(20);
		oldItem.setAvailableFrom(LocalDate.now().plusDays(5));
		oldItem.setAvailableTill(LocalDate.now().plusDays(7));

		Abholort newAbholort = new Abholort();
		newAbholort.setId(1L);
		newAbholort.setLatitude(-27.126);
		newAbholort.setLongitude(-109.3387);
		newAbholort.setBeschreibung("Osterinsel");

		AusleihItem newItem = new AusleihItem();
		newItem.setId(0L);
		newItem.setTitel("Orange");
		newItem.setBeschreibung("Um seinen Code besser zu orrangieren");
		newItem.setAbholort(newAbholort);
		newItem.setBesitzer(new Person());
		newItem.setTagessatz(4);
		newItem.setKautionswert(20);
		newItem.setAvailableFrom(LocalDate.now().plusDays(5));
		newItem.setAvailableTill(LocalDate.now().plusDays(7));

		Optional<AusleihItem> optionalOldItem = Optional.of(oldItem);
		AusleihItemRepository repo = mock(AusleihItemRepository.class);
		when(repo.findById(0L)).thenReturn(optionalOldItem);
		AusleihItemService service = new AusleihItemService(repo);

		service.updateById(0L, newItem);

		assertEquals(newAbholort, service.findById(0L).getAbholort());
	}
}
