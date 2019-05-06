package hhu.ausleihservice.web.service;

import hhu.ausleihservice.dataaccess.AusleiheRepository;
import hhu.ausleihservice.databasemodel.Ausleihe;
import hhu.ausleihservice.databasemodel.Status;
import hhu.ausleihservice.web.responsestatus.PersonNichtVorhanden;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AusleiheService {

	private AusleiheRepository ausleiheRepository;

	public AusleiheService(AusleiheRepository ausleiheRepository) {
		this.ausleiheRepository = ausleiheRepository;
	}

	public void save(Ausleihe ausleihe) {
		ausleiheRepository.save(ausleihe);
	}

	public List<Ausleihe> findAll() {
		return ausleiheRepository.findAll();
	}

	@Scheduled(cron = "0 0 1 * * ?")
	public void updateAllAusleihenDaily() {
		List<Ausleihe> ausleihen = findAll();
		LocalDate now = LocalDate.now();
		update(ausleihen, now);
	}

	public void update(List<Ausleihe> ausleihen, LocalDate now) {
		if (ausleihen != null) {
			for (Ausleihe ausleihe : ausleihen) {
				Status status = ausleihe.getStatus();
				LocalDate startDatum = ausleihe.getStartDatum();
				switch (status) {
					case AUSGELIEHEN:
						if (ausleihe.getEndDatum().isBefore(now)) {
							ausleihe.setStatus(Status.RUECKGABE_VERPASST);
						}
						break;
					case BESTAETIGT:
						if (LocalDate.now().equals(startDatum)) {
							ausleihe.setStatus(Status.AUSGELIEHEN);
						}
						break;
					default:
						break;
				}
			}
		}
	}


	public List<Ausleihe> findAllConflicts() {
		return ausleiheRepository.findByKonflikt(true);
	}

	public Ausleihe findById(Long id) {
		Optional<Ausleihe> ausleihe = ausleiheRepository.findById(id);
		if (!ausleihe.isPresent()) {
			throw new PersonNichtVorhanden();
		}
		return ausleihe.get();
	}

	public List<Ausleihe> findAllByAusleiherId(Long id) {
		return ausleiheRepository.findAllByAusleiherId(id);
	}
	public List<Ausleihe> findLateAusleihen(Iterable<Ausleihe> ausleiheList) {
		List<Ausleihe> lateAusleihen = new ArrayList<>();

		for (Ausleihe ausleihe : ausleiheList) {
			if (ausleihe.getStatus().equals(Status.RUECKGABE_VERPASST)) {
				lateAusleihen.add(ausleihe);
			}
		}

		return lateAusleihen;
	}
}
