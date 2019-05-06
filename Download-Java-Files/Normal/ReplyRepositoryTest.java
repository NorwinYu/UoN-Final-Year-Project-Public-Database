package api.iti0208.unit.repository;

import api.iti0208.data.entity.AppUser;
import api.iti0208.data.entity.Post;
import api.iti0208.data.entity.Reply;
import api.iti0208.repository.PostRepository;
import api.iti0208.repository.ReplyRepository;
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
public class ReplyRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Test
    public void testToSeeReplies() {
        AppUser user = new AppUser("user_test", "user_test",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        entityManager.persist(user);
        entityManager.flush();
        long userId = userRepository.findIdByUsername("user_test");

        Post post = new Post("testpost", "testpost", "Varia", user);
        entityManager.persist(post);
        entityManager.flush();

        Reply reply = new Reply("testReply", post, user);
        entityManager.persist(reply);
        entityManager.flush();

        Reply found = replyRepository.findById(reply.getId()).get();

        assertThat(found.getReply()).isEqualTo(reply.getReply());
    }

}