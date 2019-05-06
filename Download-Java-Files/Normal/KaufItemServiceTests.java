package hhu.ausleihservice.web;

import hhu.ausleihservice.dataaccess.KaufItemRepository;
import hhu.ausleihservice.databasemodel.Abholort;
import hhu.ausleihservice.databasemodel.KaufItem;
import hhu.ausleihservice.databasemodel.Person;
import hhu.ausleihservice.web.service.KaufItemService;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class KaufItemServiceTests {

	//Tests for updateById
	@Test
	public void updateByIdAbholortEmpty() {
		Abholort oldAbholort = new Abholort();
		oldAbholort.setId(0L);
		oldAbholort.setLatitude(-53.04379);
		oldAbholort.setLongitude(72.59947);
		oldAbholort.setBeschreibung("McDonald's");

		KaufItem oldItem = new KaufItem();
		oldItem.setId(0L);
		oldItem.setTitel("Orange");
		oldItem.setBeschreibung("Um seinen Code besser zu orrangieren");
		oldItem.setAbholort(oldAbholort);
		oldItem.setBesitzer(new Person());
		oldItem.setKaufpreis(7);

		KaufItem newItem = new KaufItem();

		Optional<KaufItem> optionalOldItem = Optional.of(oldItem);
		KaufItemRepository repo = mock(KaufItemRepository.class);
		when(repo.findById(0L)).thenReturn(optionalOldItem);
		KaufItemService service = new KaufItemService(repo);

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

		KaufItem oldItem = new KaufItem();
		oldItem.setId(0L);
		oldItem.setTitel("Orange");
		oldItem.setBeschreibung("Um seinen Code besser zu orrangieren");
		oldItem.setAbholort(oldAbholort);
		oldItem.setBesitzer(new Person());
		oldItem.setKaufpreis(7);

		Abholort newAbholort = new Abholort();
		newAbholort.setId(1L);
		newAbholort.setLatitude(-27.126);
		newAbholort.setLongitude(-109.3387);
		newAbholort.setBeschreibung("Osterinsel");

		KaufItem newItem = new KaufItem();
		newItem.setId(0L);
		newItem.setTitel("Orange");
		newItem.setBeschreibung("Um seinen Code besser zu orrangieren");
		newItem.setAbholort(newAbholort);
		newItem.setBesitzer(new Person());
		newItem.setKaufpreis(7);

		Optional<KaufItem> optionalOldItem = Optional.of(oldItem);
		KaufItemRepository repo = mock(KaufItemRepository.class);
		when(repo.findById(0L)).thenReturn(optionalOldItem);
		KaufItemService service = new KaufItemService(repo);

		service.updateById(0L, newItem);

		assertEquals(newAbholort, service.findById(0L).getAbholort());
	}
}
