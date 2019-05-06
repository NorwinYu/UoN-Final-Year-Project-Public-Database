package hhu.ausleihservice.dataaccess;

import hhu.ausleihservice.databasemodel.Person;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends CrudRepository<Person, Long> {
	List<Person> findAll();
	Optional<Person> findByUsername(String username);

	boolean existsByUsername(String username);
}
