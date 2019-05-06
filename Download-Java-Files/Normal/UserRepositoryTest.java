package api.iti0208.unit.repository;

import api.iti0208.data.entity.AppUser;
import api.iti0208.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.assertj.core.api.Java6Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByUsername() throws Exception {
        // given
        AppUser user = new AppUser("testUser", "testUser",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        entityManager.persist(user);
        entityManager.flush();

        // when
        AppUser found = userRepository.findByUsername(user.getUsername());
        System.out.println(found);

        // then
        assertThat(found.getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    public void testFindIdByUsername() throws Exception {
        AppUser user = new AppUser("testUser", "testUser",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        entityManager.persist(user);
        entityManager.flush();

        Long id = userRepository.findIdByUsername(user.getUsername());

        assertThat(id).isNotNull();
    }

}