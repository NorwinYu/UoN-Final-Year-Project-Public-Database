package api.iti0208.unit.service;

import api.iti0208.data.entity.AppUser;
import api.iti0208.data.entity.Reply;
import api.iti0208.data.input.ReplyInput;
import api.iti0208.data.input.ReplyPatchInput;
import api.iti0208.data.output.ReplyDetails;
import api.iti0208.exception.BadRequestException;
import api.iti0208.mapper.EntityToOutputObjectMapper;
import api.iti0208.repository.PostRepository;
import api.iti0208.repository.ReplyRepository;
import api.iti0208.repository.UserRepository;
import api.iti0208.service.ReplyService;
import com.auth0.jwt.JWT;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static api.iti0208.security.SecurityConstants.EXPIRATION_TIME;
import static api.iti0208.security.SecurityConstants.SECRET;
import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReplyServiceTest {

    @MockBean
    private PostRepository postRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ReplyRepository replyRepository;

    private ReplyService replyService;

    private String authToken;
    private long id_five;
    private String username;
    private AppUser testUser;

    @Before
    public void setUp() {
        id_five = 5L;
        username = "testUser";
        testUser = new AppUser(username, "password",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));

        replyService = new ReplyService(replyRepository, userRepository, postRepository, new EntityToOutputObjectMapper());
        Reply fullReply = new Reply(12L, "test", "", new Date(), new Date(),
                null, testUser);

        Mockito.when(replyRepository.save(any(Reply.class))).thenReturn(fullReply);
        Mockito.when(userRepository.findByUsername(any(String.class))).thenReturn(testUser);

        authToken = JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));
    }

    @Test
    public void testSaveReply() {
        ReplyInput replyInput = new ReplyInput("test", 10L, "");
        ReplyDetails savedReply = replyService.save(replyInput, authToken);

        assertThat(savedReply.getReply()).isNotNull();
        assertEquals("test", savedReply.getReply());
    }

    @Test(expected = BadRequestException.class)
    public void testTryToFindUsernameOfReplier() {
        Mockito.when(replyRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        replyService.findUsernameOfReplier(1L);
    }

    @Test
    public void testPatchReply() {
        String newDescription = "newTitle";
        Reply newReply = new Reply(id_five, newDescription, testUser);
        Mockito.when(replyRepository.findById(id_five)).thenReturn(Optional.of(newReply));

        ReplyDetails response = replyService.patch(
                new ReplyPatchInput(newDescription), id_five
        );
        assertEquals(newDescription, response.getReply());
    }

    @Test(expected = BadRequestException.class)
    public void testTryToPatchReply() {
        Mockito.when(replyRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        replyService.patch(new ReplyPatchInput("newTitle"), id_five);
    }
}
