package at.jugger.tracker.service.impl;

import at.jugger.tracker.exceptions.UnableToSendAuthenticationEmailException;
import at.jugger.tracker.service.TemplateService;
import at.jugger.tracker.service.dto.LoginToken;
import at.jugger.tracker.service.impl.template.AuthenticationEmailData;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;

@Service
public class TemplateServiceImpl implements TemplateService {
    private final MustacheFactory mustacheFactory;

    public TemplateServiceImpl() {
        mustacheFactory = new DefaultMustacheFactory();
    }

    @Override
    public String getAuthenticationEmailService(LoginToken loginToken, String authenticationUrl) throws UnableToSendAuthenticationEmailException {
        Mustache mustache = loadTemplate("email/authentication");

        AuthenticationEmailData authenticationEmailData = new AuthenticationEmailData();
        authenticationEmailData.setVorname(loginToken.getUser().getFirstName());
        authenticationEmailData.setAuthenticationUrl(authenticationUrl);
        authenticationEmailData.setAblaufdatum(loginToken.getExpiryDate());

        StringWriter writer = new StringWriter();
        try {
            mustache.execute(writer, authenticationEmailData).flush();
        } catch (IOException e) {
            throw new UnableToSendAuthenticationEmailException(e);
        }
        return writer.toString();
    }

    private Mustache loadTemplate(String templateName) {
        return mustacheFactory.compile(templateName + ".mustache");
    }
}
