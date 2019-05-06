package at.jugger.tracker.service;

import at.jugger.tracker.exceptions.UnableToSendAuthenticationEmailException;
import at.jugger.tracker.service.dto.LoginToken;

public interface TemplateService {
    String getAuthenticationEmailService(LoginToken loginToken, String authenticationUrl) throws UnableToSendAuthenticationEmailException;
}
