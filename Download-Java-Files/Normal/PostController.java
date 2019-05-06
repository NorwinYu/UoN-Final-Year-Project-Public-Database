package api.iti0208.controller;

import api.iti0208.data.input.PostInput;
import api.iti0208.data.output.PostDetails;
import api.iti0208.data.output.PostListResponse;
import api.iti0208.data.input.PostPatchInput;
import api.iti0208.service.PostService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static api.iti0208.security.SecurityConstants.*;

// PS! PostListResponse holds the response and the amount of pages!

@RestController
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("api/posts/{id}")
    public PostDetails getPostItemById(@PathVariable Long id,
                                       @RequestHeader(value = HEADER_STRING) String header) {
        return postService.getPostItemById(id, header);
    }

    @GetMapping("api/posts")
    public PostListResponse getPosts(@RequestParam(value = "page", defaultValue = "0") int page,
                                     @RequestParam(value = "size", defaultValue = "15") int size,
                                     @RequestParam(value = "topic", defaultValue = "all") String topic,
                                     @RequestParam(value = "order", defaultValue = "ascending") String order,
                                     @RequestParam(value = "sortBy", defaultValue = "postedAt") String sortBy) {
        if (topic.equals("home")) {
            topic = "all";
        }
        return postService.getPosts(page, size, topic, order, sortBy);
    }

    @GetMapping("api/posts/find")
    public PostListResponse findPosts(@RequestParam(value = "page", defaultValue = "0") int page,
                                      @RequestParam(value = "size", defaultValue = "15") int size,
                                      @RequestParam(value = "searchTerm", defaultValue = "") String searchTerm,
                                      @RequestParam(value = "order", defaultValue = "ascending") String order,
                                      @RequestParam(value = "sortBy", defaultValue = "postedAt") String sortBy) {
        return postService.findPosts(page, size, searchTerm, order, sortBy);
    }

    @PostMapping("api/add/post")
    public PostDetails savePost(@RequestBody @Valid PostInput item,
                         @RequestHeader(value = HEADER_STRING) String header) {
        return postService.savePost(item, header);
    }

    @DeleteMapping("api/delete/post/{id}")
    @PreAuthorize("@postService.findUsernameOfPoster(#id) == authentication.name || hasAuthority('ROLE_ADMIN')")
    public void delete(@PathVariable Long id) {
        postService.deleteById(id);
    }

    @PatchMapping("api/edit/post/{id}")
    @PreAuthorize("@postService.findUsernameOfPoster(#id) == authentication.name || hasAuthority('ROLE_ADMIN')")
    public PostDetails patchPost(@RequestBody @Valid PostPatchInput obj, @PathVariable Long id) {
        return postService.patchPost(obj, id);
    }

}
