package api.iti0208.service;

import api.iti0208.data.entity.Post;
import api.iti0208.data.entity.Reply;
import api.iti0208.data.entity.AppUser;
import api.iti0208.data.input.UserRegistrationInput;
import api.iti0208.exception.BadRequestException;
import api.iti0208.exception.PageNotFoundException;
import api.iti0208.repository.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

import static api.iti0208.security.SecurityConstants.SECRET;
import static api.iti0208.security.SecurityConstants.TOKEN_PREFIX;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public AppUser save(UserRegistrationInput registration) {
        if (registration == null) {
            throw new BadRequestException("Something went wrong!");
        }
        if (userRepository.findByUsername(registration.getUsername()) != null) {
            throw new BadRequestException("This username is already in use!");
        }
        if (userRepository.findByEmail(registration.getEmail()) != null) {
            throw new BadRequestException("This email is already in use!");
        }

        AppUser appUser = new AppUser();
        appUser.setFirstName(registration.getFirstName());
        appUser.setLastName(registration.getLastName());
        appUser.setEmail(registration.getEmail());
        appUser.setUsername(registration.getUsername());
        appUser.setPassword(bCryptPasswordEncoder.encode(registration.getPassword()));
        appUser.setGrantedAuthorities(
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );

        return userRepository.save(appUser);
    }

    public List<GrantedAuthority> getGrantedAuthorities(String username) {
        AppUser user = userRepository.findByUsername(username);

        if (user == null) {
            throw new PageNotFoundException("Couldn't find a user with this username!");
        } else {

            return user.getGrantedAuthorities();
        }
    }

    public Set<Post> getUserPosts(String username) {
        return userRepository.findByUsername(username).getUserPosts();
    }

    public Set<Reply> getUserReplies(String username) {
        return userRepository.findByUsername(username).getUserReplies();
    }

    public String getUsernameFromJwt(String token) {
        return getUsernameFromJwtToken(token);
    }

    public static String getUsernameFromJwtToken(String token) {
        return JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                .build()
                .verify(token.replace(TOKEN_PREFIX, ""))
                .getSubject();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = userRepository.findByUsername(username);
        if (appUser == null) {
            throw new UsernameNotFoundException(username);
        }

        return new User(appUser.getUsername(), appUser.getPassword(), appUser.getGrantedAuthorities());
    }
}
