package hhu.ausleihservice.dataaccess;

import hhu.ausleihservice.databasemodel.Ausleihe;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AusleiheRepository extends CrudRepository<Ausleihe, Long> {
	List<Ausleihe> findAll();
	List<Ausleihe> findByKonflikt(boolean konflikt);
	List<Ausleihe> findAllByAusleiherId(Long id);

}
