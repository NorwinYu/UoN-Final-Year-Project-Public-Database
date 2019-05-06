package at.jugger.tracker.service.impl;

import at.jugger.tracker.domain.LoginTokenEntity;
import at.jugger.tracker.domain.UserEntity;
import at.jugger.tracker.dto.User;
import at.jugger.tracker.exceptions.TokenAlreadyUsedException;
import at.jugger.tracker.exceptions.TokenExpiredException;
import at.jugger.tracker.exceptions.TokenNotFoundException;
import at.jugger.tracker.mapper.InsecureUserMapper;
import at.jugger.tracker.mapper.LoginTokenMapper;
import at.jugger.tracker.repository.LoginTokenRepository;
import at.jugger.tracker.service.AuthenticationService;
import at.jugger.tracker.service.SecurityService;
import at.jugger.tracker.service.dto.LoginToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final int TOKEN_VALID_FOR_X_MINUTES = 30;

    private final LoginTokenRepository loginTokenRepository;
    private final InsecureUserMapper insecureUserMapper;
    private final LoginTokenMapper loginTokenMapper;
    private final SecurityService securityService;

    AuthenticationServiceImpl(
            LoginTokenRepository loginTokenRepository, InsecureUserMapper insecureUserMapper,
            LoginTokenMapper loginTokenMapper, SecurityService securityService
    ) {
        this.loginTokenRepository = loginTokenRepository;
        this.insecureUserMapper = insecureUserMapper;
        this.loginTokenMapper = loginTokenMapper;
        this.securityService = securityService;
    }

    @Override
    public LoginToken createLoginToken(User user) {
        LoginTokenEntity loginToken = loginTokenRepository.findByUser_UserId(user.getId());
        if (loginToken == null) {
            loginToken = new LoginTokenEntity();
        }

        loginToken.setUsed(false);
        loginToken.setUser(insecureUserMapper.toEntity(user));
        loginToken.setExpiryDate(LocalDateTime.now().plusMinutes(TOKEN_VALID_FOR_X_MINUTES));
        loginToken.setToken(generateTokenId());

        loginTokenRepository.save(loginToken);

        return loginTokenMapper.toDto(loginToken);
    }

    private String generateTokenId() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    @Override
    public void authenticate(String token)
            throws TokenNotFoundException, TokenAlreadyUsedException, TokenExpiredException {
        LoginTokenEntity loginToken = loginTokenRepository.findByToken(token);
        UserEntity user = loginToken.getUser();
        TokenState tokenState = getTokenState(loginToken);

        switch (tokenState) {
            case NO_TOKEN:
                throw new TokenNotFoundException(token);
            case USED:
                throw new TokenAlreadyUsedException(loginTokenMapper.toDto(loginToken));
            case EXPIRED:
                throw new TokenExpiredException(loginTokenMapper.toDto(loginToken));
            case VALID:
                securityService.authenticate(user.getEmail(), Collections.singletonList(user.getRole()));
                useToken(loginToken);
        }
    }

    @Override
    public void logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    private void useToken(LoginTokenEntity loginTokenEntity) {
        loginTokenEntity.setUsed(true);
        loginTokenRepository.save(loginTokenEntity);
    }

    private TokenState getTokenState(LoginTokenEntity loginTokenEntity) {
        if (loginTokenEntity == null) {
            return TokenState.NO_TOKEN;
        } else if (loginTokenEntity.isUsed()) {
            return TokenState.USED;
        } else if (loginTokenEntity.getExpiryDate().isBefore(LocalDateTime.now())) {
            return TokenState.EXPIRED;
        } else {
            return TokenState.VALID;
        }
    }

    private enum TokenState {
        NO_TOKEN,
        VALID,
        USED,
        EXPIRED
    }
}
