package api.iti0208.service;

import api.iti0208.data.entity.AppUser;
import api.iti0208.data.entity.Post;
import api.iti0208.data.input.PostInput;
import api.iti0208.data.output.PostDetails;
import api.iti0208.data.output.PostListResponse;
import api.iti0208.data.input.PostPatchInput;
import api.iti0208.exception.BadRequestException;
import api.iti0208.exception.PageNotFoundException;
import api.iti0208.mapper.EntityToOutputObjectMapper;
import api.iti0208.repository.PostRepository;
import api.iti0208.repository.UserRepository;
import com.auth0.jwt.exceptions.JWTDecodeException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static api.iti0208.service.UserService.getUsernameFromJwtToken;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final EntityToOutputObjectMapper mapper;

    public PostService(PostRepository postRepository, UserRepository userRepository, EntityToOutputObjectMapper mapper) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    /**
     * Get methods to receive posts.
     */

    public PostDetails getPostItemById(Long id, String header) {
        Post post = getPostItemById(id);
        AppUser user = null;

        try {
            if (header != null && header.length() > 1) {
                String username = getUsernameFromJwtToken(header);
                user = userRepository.findByUsername(username);
            }
        } catch (JWTDecodeException e) {
            user = null;
        }

        return mapper.postToPostDetails(post, user);
    }

    public Post getPostItemById(Long id) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent()) {
            return post.get();
        } else {
            throw new PageNotFoundException("Sorry, this page does not exist!");
        }
    }

    public PostListResponse getPosts(int page, int size, String topic, String order, String sortBy) {
        Pageable pageableRequest = getPageable(page, size, order, sortBy);

        Page<Post> posts;
        if (topic.equals("all")) {
            posts = postRepository.findAll(pageableRequest);
        } else {
            posts = postRepository.findAllByTopic(topic, pageableRequest);
        }

        return getPostsResponse(posts, page);
    }

    public PostListResponse findPosts(int page, int size, String searchTerm, String order, String sortBy) {
        Pageable pageableRequest = getPageable(page, size, order, sortBy);
        Page<Post> posts = postRepository.findBySearchTerm(searchTerm, pageableRequest);

        return getPostsResponse(posts, page);
    }

    private PostListResponse getPostsResponse(Page<Post> posts, int page) {
        if (page > posts.getTotalPages()) {
            throw new PageNotFoundException("This page does not exist!");
        }

        return new PostListResponse(mapper.postListToPostOverviewList(posts.getContent()), posts.getTotalPages());
    }

    private Pageable getPageable(int page, int size, String order, String sortBy) {
        Pageable pageableRequest = null;

        if (order.equals("ascending")) {
            pageableRequest = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        } else {
            pageableRequest = PageRequest.of(page, size, Sort.by(sortBy).descending());
        }

        return pageableRequest;
    }

    /**
     * Save methods to save posts to DB.
     */

    public PostDetails savePost(PostInput input, String header) {
        String username = null;
        AppUser user = null;
        Post item = new Post();

        if (header != null) {
            username = getUsernameFromJwtToken(header);
        }
        if (username != null) {
            item.setTopic(input.getTopic());
            item.setTitle(input.getTitle());
            item.setDescription(input.getDescription());
            item.setRewardDescription(input.getRewardDescription());
            item.setFileLocation(input.getFileLocation());
            user = userRepository.findByUsername(username);
            item.setPostedBy(user);
        }
        return mapper.postToPostDetails(postRepository.save(item), user);
    }

    /**
     * Patch methods to change posts in DB.
     */

    public PostDetails patchPost(PostPatchInput obj, Long postId) {
        Optional<Post> post = postRepository.findById(postId);

        System.out.println(post.toString());
        if (post.isPresent()) {
            String newTitle = obj.getTitle();
            String newDescription = obj.getDescription();

            if (newTitle != null && newTitle.length() != 0) {
                postRepository.updateTitle(postId, newTitle);
            }
            if (newDescription != null && newDescription.length() != 0) {
                postRepository.updateDescription(postId, newDescription);
            }

            return mapper.postToPostDetails(getPostItemById(postId), null);
        }

        throw new BadRequestException("Problem updating Your post!");
    }

    /**
     * Delete methods to remove posts from DB.
     */

    public void deleteById(Long id) {
        postRepository.deleteById(id);
    }

    /**
     * Methods for authorization.
     */

    public String findUsernameOfPoster(Long id) {
        return getPostItemById(id).getPostedBy().getUsername();
    }
}
