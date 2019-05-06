package api.iti0208.controller;

import api.iti0208.data.input.UserRegistrationInput;
import api.iti0208.data.entity.Post;
import api.iti0208.data.entity.Reply;
import api.iti0208.exception.BadRequestException;
import api.iti0208.service.UserService;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static api.iti0208.security.SecurityConstants.HEADER_STRING;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("api/register")
    public void registerUserAccount(@RequestBody @Valid UserRegistrationInput userRegistrationInput) {
        userService.save(userRegistrationInput);
    }

    @GetMapping("api/check")
    public void checkIfLoggedIn(@RequestHeader(value = HEADER_STRING) String header) {
        try {
            if (header != null) {
                userService.getUsernameFromJwt(header);
            }
        } catch (JWTVerificationException decodeException) {
            throw new BadRequestException("User is not logged in!");
        }
    }

    @GetMapping("api/usersPosts")
    @PreAuthorize("@userService.getUsernameFromJwt(#header) == authentication.name || hasAuthority('ROLE_ADMIN')")
    public List<Post> getUserPosts(@RequestHeader(value = HEADER_STRING) String header) {
        String username = userService.getUsernameFromJwt(header);
        return userService.getUserPosts(username).stream()
                .sorted(Comparator.comparing(Post::getTitle))
                .collect(Collectors.toList());
    }

    @GetMapping("api/usersReplies")
    @PreAuthorize("@userService.getUsernameFromJwt(#header) == authentication.name || hasAuthority('ROLE_ADMIN')")
    public List<Reply> getReplies(@RequestHeader(value = HEADER_STRING) String header) {
        String username = userService.getUsernameFromJwt(header);
        return userService.getUserReplies(username).stream()
                .sorted(Comparator.comparing(Reply::getReply))
                .collect(Collectors.toList());
    }


}
