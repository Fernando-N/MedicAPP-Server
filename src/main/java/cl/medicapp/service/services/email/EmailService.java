package cl.medicapp.service.services.email;

import cl.medicapp.service.dto.EmailDto;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

    void sendEmail(EmailDto email);

}
