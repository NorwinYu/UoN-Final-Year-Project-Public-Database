package api.iti0208.unit.service;

import api.iti0208.data.entity.AppUser;
import api.iti0208.data.entity.Post;
import api.iti0208.data.input.PostInput;
import api.iti0208.data.input.PostPatchInput;
import api.iti0208.data.output.PostDetails;
import api.iti0208.data.output.PostListResponse;
import api.iti0208.exception.BadRequestException;
import api.iti0208.exception.PageNotFoundException;
import api.iti0208.mapper.EntityToOutputObjectMapper;
import api.iti0208.repository.PostRepository;
import api.iti0208.repository.ReplyRepository;
import api.iti0208.repository.UserRepository;
import api.iti0208.service.PostService;
import com.auth0.jwt.JWT;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
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
public class PostServiceTest {

    @MockBean
    private PostRepository postRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ReplyRepository replyRepository;

    private PostService postService;

    private String authToken;
    private String username;
    private AppUser testUser;
    private Post fullPost;
    private Post fullPost2;
    private long id_twelve;
    private long id_thirteen;

    @Before
    public void setUp() {
        id_twelve = 12L;
        id_thirteen = 13L;
        username = "testUser";
        testUser = new AppUser(username, "password",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        postService = new PostService(postRepository, userRepository, new EntityToOutputObjectMapper());
        fullPost = new Post(id_twelve, "Varia", "unittest", "unittest", "",
                "", new Date(), new Date(), testUser, Collections.emptyList());
        fullPost2 = new Post(id_thirteen, "Mathematics", "unitTtest", "unittest", "",
                "", new Date(), new Date(), testUser, Collections.emptyList());

        Mockito.when(userRepository.findByUsername(any(String.class))).thenReturn(testUser);
        Mockito.when(postRepository.save(any(Post.class))).thenReturn(fullPost);
        Mockito.when(postRepository.findById(id_twelve)).thenReturn(Optional.of(fullPost));
        Mockito.when(postRepository.findAllByTopic(any(String.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(Arrays.asList(fullPost)));
        Mockito.when(postRepository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(Arrays.asList(fullPost, fullPost2)));
        Mockito.when(postRepository.findBySearchTerm(any(String.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.singletonList(fullPost2)));

        authToken = JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));
    }

    @Test
    public void testSavePost() {
        PostInput postInput = new PostInput();
        postInput.setTopic("Varia");
        postInput.setTitle("test");
        postInput.setDescription("test");
        PostDetails savedPost = postService.savePost(postInput, authToken);

        assertThat(savedPost.getTitle()).isNotNull();
        assertEquals("testUser", savedPost.getPostedBy());
    }

    @Test(expected = PageNotFoundException.class)
    public void testTryToFindPostByID() {
        postService.getPostItemById(1L);
    }

    @Test
    public void testFindPostByID() {
        Post post = postService.getPostItemById(id_twelve);
        assertEquals("unittest", post.getTitle());
    }

    @Test
    public void testFindPostByTopic() {
        int page = 0;
        int size = 10;
        String topic = "Varia";
        String order = "ascending";
        String sortBy = "title";

        PostListResponse response = postService.getPosts(page, size, topic, order, sortBy);

        assertEquals(1, response.getAmountOfPages());
        assertEquals("unittest", response.getPosts().get(0).getTitle());
    }

    @Test
    public void testFindAllPosts() {
        int page = 0;
        int size = 10;
        String topic = "all";
        String order = "ascending";
        String sortBy = "title";

        PostListResponse response = postService.getPosts(page, size, topic, order, sortBy);

        assertEquals(1, response.getAmountOfPages());
        assertEquals("unittest", response.getPosts().get(0).getTitle());
    }

    @Test
    public void testFindThroughSearch() {
        int page = 0;
        int size = 10;
        String searchTerm = "ttt";
        String order = "ascending";
        String sortBy = "title";

        PostListResponse response = postService.findPosts(page, size, searchTerm, order, sortBy);

        assertEquals(1, response.getAmountOfPages());
        assertEquals("unitTtest", response.getPosts().get(0).getTitle());
    }

    @Test(expected = BadRequestException.class)
    public void testTryToPatchPost() {
        postService.patchPost(new PostPatchInput("123456", "123456"), 1L);
    }

    @Test
    public void testPatchPost() {
        String newTitle = "newTitle";
        String newDescription = "newDescripion";

        PostDetails response = postService.patchPost(
                new PostPatchInput(newTitle, newDescription), id_twelve
        );
        assertEquals("unittest", response.getTitle());
    }
}
