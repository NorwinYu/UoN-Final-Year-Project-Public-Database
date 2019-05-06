package com.clocker.clocker.repository;

import com.clocker.clocker.model.User;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void whenFindByUsername_thenReturnUser() {
        // given
        User testuser = new User("Test User", "testuser", "test@test.ee", "somepassword");
        entityManager.persist(testuser);
        entityManager.flush();

        // when
        Optional<User> found = userRepository.findByUsername(testuser.getUsername());

        // then
        Assertions.assertThat(found.get().getName())
                .isEqualTo(testuser.getName());

        Assertions.assertThat(found.get().getName())
                .startsWith("test")
                .endsWith("user")
                .isEqualToIgnoringCase("testuser");
    }

    @Test
    public void whenFindByUsernameOrEmail_thenReturnUser() {
        // given
        User user = new User("Test User", "testuser", "test@test.ee", "somepassword");
        entityManager.persist(user);
        entityManager.flush();

        // when
        Optional<User> found = userRepository.findByUsernameOrEmail(user.getUsername(), user.getEmail());

        // then
        Assertions.assertThat(found.get().getName())
                .isEqualTo(user.getName() + "eee");

        Assertions.assertThat(found.get().getName())
                .startsWith("test")
                .endsWith("user")
                .isEqualToIgnoringCase("testuser");
    }

    @Test
    public void whenExistsByUsername_thenReturnBoolean() {
        // given
        User user = new User("Test User", "testuser", "test@test.ee", "somepassword");
        entityManager.persist(user);
        entityManager.flush();

        // when
        boolean found1 = userRepository.existsByUsername(user.getUsername());
        boolean found2 = userRepository.existsByUsername("notExists");

        // then
        Assertions.assertThat(found1).isTrue();

        Assertions.assertThat(found2).isFalse();
    }

    @Test
    public void whenExistsByEmail_thenReturnBoolean() {
        // given
        User user = new User("Test User", "testuser", "test@test.ee", "somepassword");
        entityManager.persist(user);
        entityManager.flush();

        // when
        boolean found1 = userRepository.existsByEmail(user.getEmail());
        boolean found2 = userRepository.existsByEmail("notExists");

        // then
        Assertions.assertThat(found1).isTrue();
        Assertions.assertThat(found2).isFalse();
    }
}