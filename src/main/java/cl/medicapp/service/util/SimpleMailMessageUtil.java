package cl.medicapp.service.util;

import org.springframework.mail.SimpleMailMessage;

/**
 * Clase util de SimpleMailMessageMapper
 */
public class SimpleMailMessageUtil {

    public static SimpleMailMessage build(String from, String to, String subject, String body) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(body);
        return simpleMailMessage;
    }

}
