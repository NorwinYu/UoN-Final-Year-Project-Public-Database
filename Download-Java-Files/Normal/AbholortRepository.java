package hhu.ausleihservice.dataaccess;

import hhu.ausleihservice.databasemodel.Abholort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AbholortRepository extends CrudRepository<Abholort, Long> {
	List<Abholort> findAll();
}
