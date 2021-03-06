
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Brotherhood;
import domain.Float;
import domain.Procession;

@Repository
public interface BrotherhoodRepository extends JpaRepository<Brotherhood, Integer> {

	@Query("select b.floats from Brotherhood b where b.id = ?1")
	public List<Float> getFloatsByBrotherhood(int id);

	@Query("select b.processions from Brotherhood b where b.id = ?1")
	public List<Procession> getProcessionsByBrotherhood(int id);

	@Query("select a from Brotherhood a join a.userAccount b where b.username = ?1")
	public Brotherhood getBrotherhoodByUsername(String a);

	@Query("select p from Brotherhood b join b.processions p where p.isDraftMode = false and b.id = ?1")
	public List<Procession> getProcessionsByBrotherhoodFinal(int id);
}
