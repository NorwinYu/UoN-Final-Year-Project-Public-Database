package hhu.ausleihservice.web;

import hhu.ausleihservice.dataaccess.AbholortRepository;
import hhu.ausleihservice.dataaccess.AusleihItemRepository;
import hhu.ausleihservice.dataaccess.AusleiheRepository;
import hhu.ausleihservice.dataaccess.PersonRepository;
import hhu.ausleihservice.databasemodel.Abholort;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AbholortServiceIT {

	TestData testData;

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
		testData = new TestData();
		testData.getAbholortList().forEach(x -> abholortRepository.save(x));
		testData.getPersonList().forEach(x -> personRepository.save(x));
		testData.getAusleihItemList().forEach(x -> ausleihItemRepository.save(x));
		testData.getAusleiheList().forEach(x -> ausleiheRepository.save(x));
	}

	@Test
	public void testAbholortDatabase() {
		List<Abholort> aortList = abholortRepository.findAll();
		assertEquals(4, aortList.size());
		assertEquals(testData.getAbholortList(), aortList);
	}

}
