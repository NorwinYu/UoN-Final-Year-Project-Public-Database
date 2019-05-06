package at.jugger.tracker.service;

import at.jugger.tracker.exceptions.UnableToSendAuthenticationEmailException;
import at.jugger.tracker.service.dto.LoginToken;

import javax.validation.constraints.NotNull;

public interface EmailService {
    void send(@NotNull String to, @NotNull String subject, @NotNull String text);

    void sendAuthenticationEmail(LoginToken loginToken, String authenticationUrl) throws UnableToSendAuthenticationEmailException;
}
