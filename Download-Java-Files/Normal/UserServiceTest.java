package api.iti0208.unit.service;

import api.iti0208.data.entity.AppUser;
import api.iti0208.data.entity.Post;
import api.iti0208.data.entity.Reply;
import api.iti0208.data.input.UserRegistrationInput;
import api.iti0208.exception.BadRequestException;
import api.iti0208.repository.PostRepository;
import api.iti0208.repository.ReplyRepository;
import api.iti0208.repository.UserRepository;
import api.iti0208.service.UserService;
import com.auth0.jwt.JWT;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static api.iti0208.security.SecurityConstants.EXPIRATION_TIME;
import static api.iti0208.security.SecurityConstants.SECRET;
import static api.iti0208.service.UserService.getUsernameFromJwtToken;
import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static junit.framework.TestCase.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @MockBean
    private PostRepository postRepository;

    @MockBean
    private ReplyRepository replyRepository;

    @MockBean
    private UserRepository userRepository;

    private UserService userService;

    private AppUser testUser;

    @Before
    public void setUp() {
        testUser = new AppUser("testUser", "password",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        userService = new UserService(userRepository, bCryptPasswordEncoder);

        Mockito.when(userRepository.save(any(AppUser.class))).then(returnsFirstArg());
        Mockito.when(userRepository.findByUsername("testUser")).thenReturn(testUser);
    }

    @Test
    public void testRegisterUser() {
        // Can't use testUser here, it should (and will) throw exception, as this is already 'in database'
        String username = "test_username";
        UserRegistrationInput registrationInput = new UserRegistrationInput(
                username, "first_name", "last_name",
                "password", "e@mail.com"
        );

        AppUser user = userService.save(registrationInput);

        assertEquals(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")),
                user.getGrantedAuthorities());
    }

    @Test(expected = BadRequestException.class)
    public void testTryToRegisterUser() {
        userService.save(null);
    }

    @Test(expected = BadRequestException.class)
    public void testTryToRegisterUserNameInUse() {
        UserRegistrationInput registrationInput = new UserRegistrationInput(
                "testUser", "first_name", "last_name",
                "password", "e@mail.com"
        );

        userService.save(registrationInput);
    }

    @Test
    public void testGetUserPosts() {
        Set<Post> myPosts = new HashSet<>();
        Post post = new Post("testTitle", "test", "Varia");
        myPosts.add(post);
        testUser.setUserPosts(myPosts);

        Set<Post> posts = userService.getUserPosts("testUser");
        assertTrue(posts.contains(post));
    }

    @Test
    public void testGetUserReplies() {
        Set<Reply> myReplies = new HashSet<>();
        Reply reply = new Reply(1L, "testReply", testUser);
        myReplies.add(reply);
        testUser.setUserReplies(myReplies);

        Set<Reply> replies = userService.getUserReplies("testUser");
        assertTrue(replies.contains(reply));
    }

    @Test
    public void testLoadByUsername() {
        UserDetails user = userService.loadUserByUsername("testUser");

        assertEquals(testUser.getUsername(), user.getUsername());
        assertEquals(testUser.getPassword(), user.getPassword());
    }

    @Test(expected = UsernameNotFoundException.class)
    public void testTryToLoadByUsername() {
        userService.loadUserByUsername("undefined");
    }

    @Test
    public void testGetUsernameFromJWT() {
        String token = JWT.create()
                .withSubject(testUser.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));

        assertEquals("testUser", getUsernameFromJwtToken(token));
        assertEquals("testUser", userService.getUsernameFromJwt(token));
    }
}
