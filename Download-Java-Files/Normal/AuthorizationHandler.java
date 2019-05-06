package propra2.leihOrDie.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propra2.leihOrDie.dataaccess.SessionRepository;
import propra2.leihOrDie.model.Item;
import propra2.leihOrDie.model.User;

@Service
public class AuthorizationHandler {
    @Autowired
    private SessionRepository sessionRepository;

    public AuthorizationHandler(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public boolean isAuthorized(String sessionId, Item item) {
        User user = sessionRepository.findUserBySessionCookie(sessionId);

        return user.getUsername().equals(item.getUser().getUsername());
    }

    public boolean isAuthorized(String sessionId, User user) {
        User sessionUser = sessionRepository.findUserBySessionCookie(sessionId);

        return sessionUser.getUsername().equals(user.getUsername());
    }

    public boolean isAdmin(String sessionId) {
        try {
            return sessionRepository.findUserBySessionCookie(sessionId).getRole().equals("ADMIN");
        } catch (NullPointerException e) {
            return false;
        }
    }
}
