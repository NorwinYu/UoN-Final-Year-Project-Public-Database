package at.jugger.tracker.service.impl;

import at.jugger.tracker.dto.User;
import at.jugger.tracker.service.SecurityService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SecurityServiceImpl implements SecurityService {

    private static final String AUTHORITY_ROLE_PREFIX = "ROLE_";

    @Override
    public boolean currentUserHasRole(String role) {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                                    .stream().anyMatch(a -> a.equals(createAuthorityForRole(role)));
    }

    @Override
    public boolean isCurrentUser(User user) {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals(user.getEmail());
    }

    @Override
    public void authenticate(String email, List<String> roles) {
        UsernamePasswordAuthenticationToken springToken = new UsernamePasswordAuthenticationToken(
                email, "",
                roles.stream().map(this::createAuthorityForRole).collect(Collectors.toList())
        );
        SecurityContextHolder.getContext().setAuthentication(springToken);
    }

    private GrantedAuthority createAuthorityForRole(String role) {
        return new SimpleGrantedAuthority(AUTHORITY_ROLE_PREFIX + role);
    }
}
