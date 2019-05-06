package recruitment.application;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import recruitment.domain.IllegalActionException;
import recruitment.domain.Person;
import org.springframework.security.core.userdetails.User;
import recruitment.domain.PersonDTO;
import recruitment.domain.Role;
import recruitment.repository.RecruiterRepository;
import recruitment.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>This is the recruitment application class, which defines tasks that can be
 * performed by the domain layer.</p>
 */
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
@Service
public class RecruiterService implements UserDetailsService {
    @Autowired
    private RecruiterRepository recruiterRepo;

    @Autowired
    private PersonRepository personRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Registers a user, by creating an instance in the database.
     *
     * @param fname User's first name.
     * @param lname User's last name.
     * @param email User's email-address.
     * @param ssn User's social security number.
     * @param username User's username.
     * @param password User's password.
     */
    public PersonDTO registerUser(String fname, String lname, String email, String ssn, String username, String password, String cnfrmpwd) throws IllegalActionException {
        StringBuilder message = new StringBuilder("msg:");
        if(recruiterRepo.checkUsername(username) != null)
            message.append("USERNAME");
        if(recruiterRepo.checkEmail(email) != null)
            message.append("EMAIL");
        if(recruiterRepo.checkSsn(ssn) != null)
            message.append("SSN");
        if(!password.equals(cnfrmpwd))
            message.append("PASSWORD");
        if(message.length() > 4)
            throw new IllegalActionException(message.toString());
        Person person = new Person(fname, lname, ssn, email,
                username, passwordEncoder.encode(password), recruiterRepo.getRoleById(2));
        if (person == null)
           return person;
        return personRepo.save(person);
    }

    /**
     * Finds a user entity based on a given username.
     *
     * @param username The specified username.
     * @return The user entity, with related data.
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = personRepo.getPersonByUsername(username);
        if (person == null)
            throw new UsernameNotFoundException("Could not find a person with username: " + username);

        Role role = person.getRole();
        String roleName = role.getName();

        Set<GrantedAuthority> grantedAuthoritySet = new HashSet<>();
        grantedAuthoritySet.add(new SimpleGrantedAuthority(roleName));
        return new User(person.getUsername(),person.getPassword(),grantedAuthoritySet);

    }

    /**
     * Finds username of authenticated user.
     *
     * @return The instance of person.
     */
    public PersonDTO getAuthenticatedUsername() throws Exception{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            User user = (User) authentication.getPrincipal();
            return personRepo.getPersonByUsername(user.getUsername());
        }else{
            throw new Exception("Could not find an authenticated user");
        }
    }
}
