package cl.medicapp.service.services.email;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    /**
     * Metodo que envia un mail de manera asíncrona
     *
     * @param email Objeto SimpleMailMessage a enviar
     */
    @Async
    public void sendEmail(SimpleMailMessage email) {
        log.info("[EmailService] Sending password recovery token mail to {}", email.getTo()[0]);
        mailSender.send(email);
        log.info("[EmailService] Email send!");
    }

}
