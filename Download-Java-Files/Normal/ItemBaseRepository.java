package hhu.ausleihservice.dataaccess;

import hhu.ausleihservice.databasemodel.Item;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface ItemBaseRepository<T extends Item> extends CrudRepository<T, Long> {
	List<T> findAll();
	Optional<T> findById(long id);
	@Query("select distinct f from Item f " +
			"where lower(:name) in (lower(f.titel)) or " +
			"lower(:name) in (lower(f.beschreibung))")
	List<T> simpleSearch(@Param("name") String name);
}
