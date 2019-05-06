package at.jugger.tracker.service;

import at.jugger.tracker.dto.User;
import at.jugger.tracker.exceptions.TokenAlreadyUsedException;
import at.jugger.tracker.exceptions.TokenExpiredException;
import at.jugger.tracker.exceptions.TokenNotFoundException;
import at.jugger.tracker.service.dto.LoginToken;

public interface AuthenticationService {
    LoginToken createLoginToken(User userEntity);

    void authenticate(String tokenId) throws TokenNotFoundException, TokenAlreadyUsedException, TokenExpiredException;

    void logout();
}
