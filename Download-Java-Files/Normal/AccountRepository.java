package tivoli.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tivoli.domain.Account;

import java.util.List;

/**
 * Database access in regards to accounts.
 */
@Repository
@Transactional(propagation = Propagation.MANDATORY) //doesn't allow calls to these methods from outside a transaction
public interface AccountRepository extends JpaRepository<Account, Long> {

    /**
     * Returns an Account based on a search by username
     * @param username name used to login for the account
     * @return Account with the correct username
     */
    Account findAccountByUsername(String username);

    /**
     * Returns an Account based on a search by email
     * @param email email address connected to the account
     * @return Account with the correct email address
     */
    Account findAccountByEmail(String email);

    /**
     * Returns an Account based on a search by social security number
     * @param ssn social security number
     * @return Account with the correct social security number
     */
    Account findAccountBySsn(String ssn);

    /**
     * Return an account based on a search by username or email.
     * @param username username to search for.
     * @param email email to search for.
     * @return The account associated to this username or email.
     */
    Account findAccountByUsernameOrEmail(String username, String email);
}
