package tivoli.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tivoli.domain.Account;
import tivoli.domain.Application;

import java.util.List;

/**
 * Database access for applications.
 */
@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    /**
     * Gets a list of all applications.
     * @return a list of all applications.
     */
    List<Application> findAll();

    /**
     * Finds applications made by an account.
     * @param account the account tied the applications.
     * @return a list of applications made by the account.
     */
    List<Application> findApplicationByAccount(Account account);
}