package api.iti0208.unit.repository;

import api.iti0208.data.entity.AppUser;
import api.iti0208.data.entity.Post;
import api.iti0208.repository.PostRepository;
import api.iti0208.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.assertj.core.api.Java6Assertions.assertThat;

@DataJpaTest
public class PostRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testToSeePosts() {
        AppUser user = new AppUser("testUser", "testUser",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        Post post = new Post("testpost", "testpost", "Varia", user);
        entityManager.persist(post);

        Post found = postRepository.findById(post.getId()).get();

        assertThat(found.getTitle()).isEqualTo(post.getTitle());
    }

    @Test
    public void testSearchPosts() {
        AppUser user = new AppUser("user_test", "user_test",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        entityManager.persist(user);
        entityManager.flush();
        long userId = userRepository.findIdByUsername("user_test");

        Post post = new Post("testpost", "testpost", "Varia", user);
        entityManager.persist(post);
        entityManager.flush();
        post = new Post("testpost2", "testpost", "Varia", user);
        entityManager.persist(post);
        entityManager.flush();

        Page found = postRepository.findBySearchTerm("2", Pageable.unpaged());
        assertThat(found.getTotalElements()).isEqualTo(1);

        found = postRepository.findBySearchTerm("testpost", Pageable.unpaged());
        assertThat(found.getTotalElements()).isEqualTo(2);
    }

}