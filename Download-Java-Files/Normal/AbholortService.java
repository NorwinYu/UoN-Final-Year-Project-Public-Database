package hhu.ausleihservice.web.service;

import hhu.ausleihservice.dataaccess.AbholortRepository;
import hhu.ausleihservice.databasemodel.Abholort;
import org.springframework.stereotype.Service;

@Service
public class AbholortService {

	private AbholortRepository abholortRepository;

	public AbholortService(AbholortRepository abholortRepository) {
		this.abholortRepository = abholortRepository;
	}

	public void save(Abholort abholort) {
		abholortRepository.save(abholort);
	}

}
