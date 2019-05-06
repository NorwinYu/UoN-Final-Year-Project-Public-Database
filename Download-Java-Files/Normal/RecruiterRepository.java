package recruitment.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import recruitment.domain.PersonDTO;
import recruitment.domain.Role;

/**
 * Contains all database operations concerning users on the application.
 */
@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface RecruiterRepository extends JpaRepository<Role, String> {

    /**
     * Finds a role given a certain identifier.
     *
     * @param id The identifier.
     * @return the role.
     */
    @Query(value = "SELECT * FROM ROLE WHERE ROLE_ID = ?", nativeQuery = true)
    Role getRoleById(int id);

    /**
     * Checks if a username exists in database.
     *
     * @param username The given username to check for.
     * @return true if username already exists, else false.
     */
    @Query(value = "SELECT * FROM PERSON WHERE USERNAME = ?", nativeQuery = true)
    PersonDTO checkUsername(String username);

    /**
     * Checks if an email exists in database.
     *
     * @param email The email to check for.
     * @return true if email already exists, otherwise false.
     * @throws DataAccessException
     */
    @Query(value = "SELECT * FROM PERSON WHERE EMAIL = ?", nativeQuery = true)
    PersonDTO checkEmail(String email);

    /**
     * Checks if social security number exists in database.
     *
     * @param ssn The ssn to check for.
     * @return true if ssn already exists, otherwise false.
     * @throws DataAccessException
     */
    @Query(value = "SELECT * FROM PERSON WHERE SSN = ?", nativeQuery = true)
    PersonDTO checkSsn(String ssn);
}
