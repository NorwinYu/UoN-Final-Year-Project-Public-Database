package hhu.ausleihservice.dataaccess;

import hhu.ausleihservice.databasemodel.Abholort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AbholortConverter implements Converter<Long, Abholort> {
	@Autowired
	AbholortRepository abholortRepository;

	@Override
	public Abholort convert(Long id) {
		return abholortRepository.findById(id).get();
	}

}
