package tivoli.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tivoli.domain.Experience;
import tivoli.domain.Position;
import tivoli.domain.Account;

import java.util.List;


/**
 * Database access in regards to experience.
 */
@Repository
@Transactional(propagation = Propagation.MANDATORY) //don't allow method call outside of transaction
public interface ExpRepository extends JpaRepository<Experience, Long> {

    /**
     * Finds a list of Experiences by a certain account
     * @param account the account whose experiences you're interested in
     * @return list of Experience tied to account
     */
    List<Experience> findExperiencesByAccount(Account account);

    /**
     * Finds a list of Experiences filtered by a position
     * @param pos the position that you're interested in
     * @return list of Position tied to account
     */
    List<Experience> findExperiencesByPosition(Position pos);

    /**
     * Finds an experience based on an account and the work role.
     * @param account the account in question.
     * @param position the work role.
     * @return the experience tied to the given account and position.
     */
    Experience findExperiencesByAccountAndPosition(Account account, Position position);
}