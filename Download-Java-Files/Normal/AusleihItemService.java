package hhu.ausleihservice.web.service;

import hhu.ausleihservice.dataaccess.AusleihItemRepository;
import hhu.ausleihservice.databasemodel.Abholort;
import hhu.ausleihservice.databasemodel.AusleihItem;
import hhu.ausleihservice.web.responsestatus.ItemNichtVorhanden;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AusleihItemService {

	private AusleihItemRepository items;

	public AusleihItemService(AusleihItemRepository itemRep) {
		this.items = itemRep;
	}

	public AusleihItem findById(long id) {
		Optional<AusleihItem> item = items.findById(id);
		if (!item.isPresent()) {
			throw new ItemNichtVorhanden();
		}
		return item.get();
	}

	List<AusleihItem> findAll() {
		return items.findAll();
	}


	public List<AusleihItem> extendedDateSearch(LocalDate availableMin, LocalDate availableMax) {
		return items.extendedDateSearch(availableMin, availableMax);
	}

	public List<AusleihItem> extendedSearch(String query,
											int tagessatzMax,
											int kautionswertMax,
											LocalDate availableMin,
											LocalDate availableMax) {
		if (query == null || query.isEmpty()) {
			return items.extendedDateSearch(availableMin, availableMax);
		}
		return items.extendedSearch(query, availableMin, availableMax);
	}

	public void updateById(Long id, AusleihItem newItem) {
		AusleihItem toUpdate = findById(id);
		System.out.println("Starting ausleih item update");
		toUpdate.setTitel(newItem.getTitel());
		toUpdate.setBeschreibung(newItem.getBeschreibung());
		toUpdate.setAvailableFrom(newItem.getAvailableFrom());
		toUpdate.setAvailableTill(newItem.getAvailableTill());
		toUpdate.setTagessatz(newItem.getTagessatz());
		toUpdate.setKautionswert(newItem.getKautionswert());

		Abholort newAbholort = newItem.getAbholort();

		if (newAbholort != null && !newAbholort.equals(new Abholort())) {
			toUpdate.setAbholort(newAbholort);
		}

		items.save(toUpdate);
	}


	public void save(AusleihItem item) {
		items.save(item);
	}

	public List<AusleihItem> simpleSearch(String query) {
		if (query == null || query.isEmpty()) {
			return findAll();
		}
		return items.simpleSearch(query);
	}

}
