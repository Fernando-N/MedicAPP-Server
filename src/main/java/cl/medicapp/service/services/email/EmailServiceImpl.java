package cl.medicapp.service.services.email;

import cl.medicapp.service.dto.EmailDto;
import cl.medicapp.service.util.SimpleMailMessageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    /**
     * Bean de JavaMailSender
     */
    private final JavaMailSender mailSender;

    /**
     * Metodo que envia un mail de manera asíncrona
     * @param email Objeto SimpleMailMessage a enviar
     */
    @Async
    public void sendEmail(EmailDto email) {
        SimpleMailMessage recoveryMail = SimpleMailMessageUtil.build("suppor@medicapp.cl", email.getEmail(), email.getTitle(), email.getBody());
        sendEmail(recoveryMail);
    }

    //TODO Ver como mejorar esto

    /**
     * Enviar email
     * @param email objeto email
     */
    @Async
    void sendEmail(SimpleMailMessage email) {
        mailSender.send(email);
        log.info("[EmailService] Password recovery token mail send to {}", Objects.requireNonNull(email.getTo())[0]);
    }

}
