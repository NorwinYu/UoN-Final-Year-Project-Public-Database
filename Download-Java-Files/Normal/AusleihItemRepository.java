package hhu.ausleihservice.dataaccess;

import hhu.ausleihservice.databasemodel.AusleihItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional
public interface AusleihItemRepository extends ItemBaseRepository<AusleihItem> {

	@Query("select distinct f from AusleihItem f where " +
			":availableMin between f.availableFrom and f.availableTill and" +
			":availableMax between f.availableFrom and f.availableTill")
	List<AusleihItem> extendedDateSearch(@Param("availableMin") LocalDate availableMin,
									 @Param("availableMax") LocalDate availableMax);

	@Query("select distinct f from AusleihItem f where " +
			"lower(:name) in (lower(f.titel)) or " +
			"lower(:name) in (lower(f.beschreibung)) and " +
			":availableMin between f.availableFrom and f.availableTill and" +
			":availableMax between f.availableFrom and f.availableTill")
	List<AusleihItem> extendedSearch(@Param("name") String name,
									 @Param("availableMin") LocalDate availableMin,
									 @Param("availableMax") LocalDate availableMax);
}
