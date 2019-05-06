package hhu.ausleihservice.web;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import hhu.ausleihservice.dataaccess.AusleihItemRepository;
import hhu.ausleihservice.dataaccess.KaufItemRepository;
import hhu.ausleihservice.databasemodel.AusleihItem;
import hhu.ausleihservice.databasemodel.KaufItem;
import hhu.ausleihservice.web.service.AusleihItemService;
import hhu.ausleihservice.web.service.KaufItemService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ItemServiceTest {

	//This field is required for the tests to work but will cause spotBugs to detect dodgy code
	@Rule
	@SuppressFBWarnings("URF_UNREAD_PUBLIC_OR_PROTECTED_FIELD")
	public MockitoRule mockitoRule = MockitoJUnit.rule();
	@Mock
	private AusleihItemRepository ausleihItemRepository;
	private AusleihItemService ausleihItemService;
	private List<AusleihItem> ausleihItemList; //as proxy for the repository
	@Mock
	private KaufItemRepository kaufItemRepository;
	private KaufItemService kaufItemService;
	private List<KaufItem> kaufItemList; //as proxy for the repository

	private boolean testItemEquality(AusleihItem base, AusleihItem toTest) {
		return base.getId().longValue() == toTest.getId().longValue() &&
				base.getTitel().equals(toTest.getTitel()) &&
				base.getBeschreibung().equals(toTest.getBeschreibung()) &&
				base.getKautionswert().equals(toTest.getKautionswert()) &&
				base.getTagessatz().equals(toTest.getTagessatz()) &&
				base.getAvailableFrom() == toTest.getAvailableFrom() &&
				base.getAvailableTill() == toTest.getAvailableTill();
	}

	@Before
	public void prepareTestData() {
		ausleihItemList = new ArrayList<>();
		kaufItemList = new ArrayList<>();

		ausleihItemService = new AusleihItemService(ausleihItemRepository);
		kaufItemService = new KaufItemService(kaufItemRepository);

		AusleihItem item1 = new AusleihItem();
		item1.setId(1L);
		item1.setTitel("Fahrrad");
		item1.setBeschreibung("Hammer Fahrrad mit Dynamo");
		item1.setKautionswert(200);
		item1.setTagessatz(15);
		item1.setAvailableFrom(LocalDate.of(0, 1, 1));
		item1.setAvailableTill(LocalDate.of(9999, 12, 31));

		AusleihItem item2 = new AusleihItem();
		item2.setId(2L);
		item2.setTitel("Würfelset");
		item2.setBeschreibung("Acht hellgürne Würfel. Sie leuchten im dunkeln");
		item2.setKautionswert(800);
		item2.setTagessatz(2);
		item2.setAvailableFrom(LocalDate.of(2010, 6, 1));
		item2.setAvailableTill(LocalDate.of(2010, 8, 1));

		AusleihItem item3 = new AusleihItem();
		item3.setId(3L);
		item3.setTitel("Rosa Fahrrad");
		item3.setBeschreibung("Rosafarbendes Fahrrad ohne Hinterrad");
		item3.setKautionswert(50);
		item3.setTagessatz(8);
		item3.setAvailableFrom(LocalDate.of(2005, 1, 1));
		item3.setAvailableTill(LocalDate.of(2015, 1, 1));

		KaufItem item4 = new KaufItem();
		item4.setId(4L);
		item4.setTitel("Hammer");
		item4.setBeschreibung("Ein großer Vorschlaghammer");
		item4.setKaufpreis(43);

		ausleihItemList.add(item1);
		ausleihItemList.add(item2);
		ausleihItemList.add(item3);
		kaufItemList.add(item4);

		Mockito.when(ausleihItemRepository.findAll()).thenReturn(ausleihItemList); //3 items
		Mockito.when(kaufItemRepository.findAll()).thenReturn(kaufItemList); //1 item
	}

	@Test
	public void testAusleihSimpleSearch_nullQuery() {
		ausleihItemList.forEach(x -> System.out.println(x.getTitel()));
		List<AusleihItem> l = ausleihItemRepository.findAll();
		assertEquals(3, l.size());
		List<AusleihItem> searchedList = ausleihItemService.simpleSearch(null);
		assertEquals(3, searchedList.size());
	}

	@Test
	public void testKaufSimpleSearch_nullQuery() {
		kaufItemList.forEach(x -> System.out.println(x.getTitel()));
		List<KaufItem> l = kaufItemRepository.findAll();
		assertEquals(1, l.size());
		List<KaufItem> searchedList = kaufItemService.simpleSearch(null);
		assertEquals(1, searchedList.size());
	}
}
