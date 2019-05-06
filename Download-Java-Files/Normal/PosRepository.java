package tivoli.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tivoli.domain.Position;

import java.util.List;

/**
 * Database access in regards to position repository.
 */
@Repository
@Transactional(propagation = Propagation.MANDATORY) //don't allow method call outside of transaction
public interface PosRepository extends JpaRepository<Position, Long> {

    /**
     * Gets a position based on name.
     * @param position the work position name.
     * @return the position object tied to this position name.
     */
    Position findPositionByName(String position);

    /**
     * Gets all positions available.
     * @return a list of all positions.
     */
    List<Position> findAll();
}
