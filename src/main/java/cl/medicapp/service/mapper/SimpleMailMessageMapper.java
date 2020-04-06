package cl.medicapp.service.mapper;

import org.springframework.mail.SimpleMailMessage;

public class SimpleMailMessageMapper {

    public static SimpleMailMessage build(String from, String to, String subject, String body) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(body);
        return simpleMailMessage;
    }

}
