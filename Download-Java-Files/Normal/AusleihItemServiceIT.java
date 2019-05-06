package hhu.ausleihservice.web;

import hhu.ausleihservice.dataaccess.AbholortRepository;
import hhu.ausleihservice.dataaccess.AusleihItemRepository;
import hhu.ausleihservice.dataaccess.AusleiheRepository;
import hhu.ausleihservice.dataaccess.PersonRepository;
import hhu.ausleihservice.databasemodel.*;
import hhu.ausleihservice.web.service.AusleihItemService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AusleihItemServiceIT {

	AusleihItemService ausleihItemService;

	@Autowired
	private AusleihItemRepository ausleihItemRepository;
	@Autowired
	private PersonRepository personRepository;
	@Autowired
	private AbholortRepository abholortRepository;
	@Autowired
	private AusleiheRepository ausleiheRepository;

	@Before
	public void onStartup() {
		System.out.println("Populating the database");
		TestData testData = new TestData();
		testData.getAbholortList().forEach(x -> abholortRepository.save(x));
		testData.getPersonList().forEach(x -> personRepository.save(x));
		testData.getAusleihItemList().forEach(x -> ausleihItemRepository.save(x));
		testData.getAusleiheList().forEach(x -> ausleiheRepository.save(x));
		ausleihItemService = new AusleihItemService(ausleihItemRepository);
	}

	@Test
	public void testSimpleSearch_1_nullQuery() {
		List<AusleihItem> simple = ausleihItemService.simpleSearch(null);
		assertEquals(3, simple.size());
		assertTrue(simple.containsAll(ausleihItemRepository.findAll()));
	}

	@Test
	public void testExtendedSearch_1_nullQuery() {
		List<AusleihItem> searchedList = ausleihItemService.extendedSearch(null,
				2147483647,
				2147483647,
				LocalDate.of(2019, 5, 5),
				LocalDate.of(2019, 5, 6));

		assertEquals(3, searchedList.size());
		assertTrue(searchedList.containsAll(ausleihItemRepository.findAll()));
	}

	@Test
	public void testExtendedSearch_1_dates() {
		List<AusleihItem> searchedList = ausleihItemService.extendedSearch(null,
				2147483647,
				2147483647,
				LocalDate.of(2019, 5, 4),
				LocalDate.of(2019, 5, 5));

		assertFalse(searchedList.containsAll(ausleihItemRepository.findAll()));
		assertEquals(2, searchedList.size());
	}

}
