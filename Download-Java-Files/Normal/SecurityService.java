package at.jugger.tracker.service;

import at.jugger.tracker.dto.User;

import java.util.List;

public interface SecurityService {
    boolean currentUserHasRole(String role);

    boolean isCurrentUser(User user);

    void authenticate(String email, List<String> roles);
}
