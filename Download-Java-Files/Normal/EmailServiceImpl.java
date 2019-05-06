package at.jugger.tracker.service.impl;

import at.jugger.tracker.exceptions.UnableToSendAuthenticationEmailException;
import at.jugger.tracker.service.EmailService;
import at.jugger.tracker.service.TemplateService;
import at.jugger.tracker.service.dto.LoginToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender emailSender;
    private final TemplateService templateService;

    @Value("${spring.mail.username}")
    private String from;

    public EmailServiceImpl(JavaMailSender emailSender, TemplateService templateService) {
        this.emailSender = emailSender;
        this.templateService = templateService;
    }

    @Override
    public void send(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    @Override
    public void sendAuthenticationEmail(LoginToken loginToken, String authenticationUrl) throws UnableToSendAuthenticationEmailException {
        try {
            MimeMessage message = emailSender.createMimeMessage();

            message.setFrom(from);
            message.setRecipients(Message.RecipientType.TO, loginToken.getUser().getEmail());
            String subject = "Login beim Jugger Tracker von Jugger Vienna";
            message.setSubject(subject, "UTF-8");

            String htmlMsg = templateService.getAuthenticationEmailService(loginToken, authenticationUrl);
            message.setContent(htmlMsg, "text/html; charset=UTF-8");

            emailSender.send(message);
        } catch (MessagingException e) {
            throw new UnableToSendAuthenticationEmailException(e);
        }
    }
}
