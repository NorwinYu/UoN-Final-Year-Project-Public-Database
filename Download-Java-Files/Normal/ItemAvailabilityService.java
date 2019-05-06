package hhu.ausleihservice.web.service;

import hhu.ausleihservice.databasemodel.AusleihItem;
import hhu.ausleihservice.databasemodel.Ausleihe;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ItemAvailabilityService {

	private boolean isInPeriod(LocalDate date, LocalDate start, LocalDate end) {
		return !date.isBefore(start) && !date.isAfter(end);
	}

	public boolean isAvailable(AusleihItem item) {
		return isAvailable(item, LocalDate.now());
	}

	public boolean isAvailable(AusleihItem item, LocalDate date) {
		LocalDate availableFrom = item.getAvailableFrom();
		LocalDate availableTill = item.getAvailableTill();
		Set<Ausleihe> ausleihen = item.getAusleihen();
		return isInPeriod(date, availableFrom, availableTill)
				&& ausleihen.stream().noneMatch((ausleihe)
				-> isInPeriod(date, ausleihe.getStartDatum(), ausleihe.getEndDatum()));
	}

	public boolean isAvailableFromTill(AusleihItem item, LocalDate from, LocalDate till) {
		LocalDate temp = from;
		while (!temp.equals(till.plusDays(1))) {
			if (!isAvailable(item, temp)) {
				return false;
			}
			temp = temp.plusDays(1);
		}
		return true;
	}

	public List<String> getUnavailableDates(AusleihItem item) {
		LocalDate temp = item.getAvailableFrom();
		LocalDate end = item.getAvailableTill();
		List<String> unavailabeDates = new ArrayList<>();
		while (!temp.isAfter(end)) {
			if (!isAvailable(item, temp)) {
				unavailabeDates.add(temp.toString());
			}
			temp = temp.plusDays(1);
		}
		return unavailabeDates;
	}
}
