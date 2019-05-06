package tivoli.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tivoli.domain.Availability;
import tivoli.domain.Account;

import java.util.Date;
import java.util.List;

/**
 * Databse access with regards to the availability.
 */
@Repository
@Transactional(propagation = Propagation.MANDATORY) //don't allow method call outside of transaction
public interface AvailRepository extends JpaRepository<Availability, Long> {

    /**
     * Returns the Availability connected to an Account
     * The Availability tells the details of when they can work
     * @param account Account tied to availability
     * @return Availability object of the Account
     */
    List<Availability> findAvailabilityByAccount(Account account);

    /**
     * Returns list of Accounts that can work from one date to another
     * @param from when they have to be able to work
     * @param to when they no longer have to be able to work
     * @return Any Accounts that have made applications that qualify
     */
    List<Account> findAccountsByFromAndTo(Date from, Date to);

    /**
     * Finds an availability record that is based on an account and a start date.
     * @param account the account in question.
     * @param from the starting date
     * @return the availability object tied to the account and start date.
     */
    Availability findAvailabilityByAccountAndFrom(Account account, Date from);

    /**
     * Finds an availability record that is based on an account and an end date.
     * @param account the account in question.
     * @param to the end date.
     * @return the availability object tied to the account and end date.
     */
    Availability findAvailabilityByAccountAndTo(Account account, Date to);
}
