package at.jugger.tracker.config.mock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.io.InputStream;
import java.util.Arrays;

@Slf4j
public class MockMailServer implements JavaMailSender {
    @Override
    public MimeMessage createMimeMessage() {
        return new MimeMessage((Session) null);
    }

    @Override
    public MimeMessage createMimeMessage(InputStream contentStream) throws MailException {
        try {
            return new MimeMessage(null, contentStream);
        } catch (MessagingException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void send(MimeMessage mimeMessage) throws MailException {
        try {
            log.info(Arrays.toString(mimeMessage.getAllRecipients()));
            log.info(mimeMessage.getContent().toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void send(MimeMessage... mimeMessages) throws MailException {
        for (MimeMessage mimeMessage : mimeMessages) {
            log.info(mimeMessage.toString());
        }
    }

    @Override
    public void send(MimeMessagePreparator mimeMessagePreparator) throws MailException {
        log.info(mimeMessagePreparator.toString());
    }

    @Override
    public void send(MimeMessagePreparator... mimeMessagePreparators) throws MailException {
        for (MimeMessagePreparator mimeMessagePreparator : mimeMessagePreparators) {
            log.info(mimeMessagePreparator.toString());
        }
    }

    @Override
    public void send(SimpleMailMessage simpleMessage) throws MailException {
        log.info(simpleMessage.toString());
    }

    @Override
    public void send(SimpleMailMessage... simpleMessages) throws MailException {
        for (SimpleMailMessage simpleMessage : simpleMessages) {
            log.info(simpleMessage.toString());
        }
    }
}
