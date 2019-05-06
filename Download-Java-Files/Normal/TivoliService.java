package tivoli.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tivoli.domain.*;
import tivoli.repository.*;

import java.util.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Date.valueOf;
import static tivoli.util.ParseDate.parseDate;


/**
 * This is the class that that defines all the tasks that can be performed by the domain and repository layers.
 */
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
@Service
public class TivoliService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PosRepository posRepository;

    @Autowired
    private ExpRepository expRepository;

    @Autowired
    private AvailRepository availRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    /**
     * Finds an account based on a username.
     *
     * @param username the username of the account.
     * @return the account that is tied to the username. Null if none.
     */
    public Account findAccountByUsername(String username) {
        return accountRepository.findAccountByUsername(username);
    }

    /**
     * Finds an account based on an email.
     *
     * @param email the email of the account.
     * @return the account that is tied to the email. Null if none.
     */
    public Account findAccountByEmail(String email) {
        return accountRepository.findAccountByEmail(email);
    }

    /**
     * Finds an account based on a SSN.
     *
     * @param ssn the ssn of the account.
     * @return the account that is toed to the ssn. Null if none.
     */
    public Account findAccountBySsn(String ssn) {
        return accountRepository.findAccountBySsn(ssn);
    }

    /**
     * Adds a new user to the database.
     *
     * @param name the users first name.
     * @param surname the users last name.
     * @param username the users username.
     * @param ssn the users social security number.
     * @param email the users email.
     * @param password the users password.
     */
    public void registerAccount(String name, String surname, String username, String ssn, String email, String password) {
        accountRepository.save(new Account(name, surname, username, ssn, email, password, roleRepository.findRoleByName("applicant")));
    }

    /**
     * User sends in an application. The info gets stored on the db.
     * @param account the account applying.
     */
    public void addApplication(Account account){
        applicationRepository.save(new Application(account, valueOf(LocalDate.now())));
    }

    /**
     * Stores a experience ties to an account, position and length in years.
     * @param account the account of the user making the application.
     * @param posName the position name they worked as.
     * @param years the time they worked for.
     */
    public void addExperience(Account account, String posName, double years){
        Position position = posRepository.findPositionByName(posName);

        Experience experience = expRepository.findExperiencesByAccountAndPosition(account, position);

        if (experience == null)
            expRepository.save(new Experience(account, position, years));
        else {
            experience.setYears(years);
            expRepository.save(experience);
        }
    }

    /**
     * Gets the people that are able to work in the given period.
     *
     * @param from When is available to start working.
     * @param to When is no longer available to work.
     * @return List of Accounts that meets the criteria.
     */
    public List<Account> getApplicantsByPeriod(Date from, Date to){

        List<Account> accounts = availRepository.findAccountsByFromAndTo(from, to);
        return accounts;
    }


    /**
     * Gets all possible positions.
     * @return list of positions available.
     */
    public List<Position> getPositions(){
        return posRepository.findAll();
    }

    /**
     * Gets an accounts avaialbility periods.
     *
     * @param account applicants account
     * @return returns availability details
     */
    public List<Availability> getApplicantAvailability(Account account){

        return availRepository.findAvailabilityByAccount(account);
    }

    /**
     * Used by spring security to load a user from the db.
     * @param username the users username.
     * @return a custom user object that implements userdetail.
     * @throws UsernameNotFoundException thrown if username does not exist.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findAccountByUsernameOrEmail(username, username);

        if (account == null) {
            throw new UsernameNotFoundException("User " + username + " could not be found.");
        }

        String roleName = account.getRole().getName();

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + roleName.toUpperCase()));
        UserDetails userDetails = new CurrentUser(account.getUsername(), account.getPassword(), grantedAuthorities);
        ((CurrentUser) userDetails).setAccount(account);
        return userDetails;
    }

    /**
     * Gets all applications.
     *
     * @return a list of all applications
     */
    public List<Application> getApplications() {
        return applicationRepository.findAll();
    }

    /**
     * Gets all people that have experience in a given position.
     * @param position the role that the person has experience in.
     * @return a list of people that have experience in the given position.
     */
    public List<Account> getApplicantsByPosition(String position) {

        List<Experience> experiences = expRepository.findExperiencesByPosition(posRepository.findPositionByName(position));

        List<Account> accounts = new ArrayList<>();
        for (Experience exp : experiences) {
            accounts.add(exp.getAccount());
        }

        return accounts;
    }

    /**
     * Gets a users work experience.
     * @param account the users account.
     * @return a list of the users work experiences.
     */
    public List<Experience> getUserExperience(Account account) {
        return expRepository.findExperiencesByAccount(account);
    }

    /**
     * Adds an a period that the person is available.
     * @param account the persons account.
     * @param fromDate starting date the person is available.
     * @param toDate date the person is no longer available.
     */
    public void addAvailability(Account account, String fromDate, String toDate) {

        Date from = parseDate(fromDate);
        Date to = parseDate(toDate);

        Availability availabilityFrom = availRepository.findAvailabilityByAccountAndFrom(account, from);
        Availability availabilityTo = availRepository.findAvailabilityByAccountAndTo(account, to);

        if (availabilityFrom != null) {

            availabilityFrom.editAvailability(from, to);
            availRepository.save(availabilityFrom);

        }
        else if (availabilityTo != null) {

            availabilityTo.editAvailability(from, to);
            availRepository.save(availabilityTo);

        }
        else {
            availRepository.save(new Availability(account, from, to));
        }
    }

    /**
     * Gets a persons applications.
     *
     * @param account the persons account.
     * @return a list of applications the user has made.
     */
    public List<Application> getApplications(Account account) {
        return applicationRepository.findApplicationByAccount(account);
    }
}
