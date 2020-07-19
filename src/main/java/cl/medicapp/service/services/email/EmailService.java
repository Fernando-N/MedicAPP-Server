package cl.medicapp.service.services.email;

import cl.medicapp.service.dto.EmailDto;

public interface EmailService {

    void sendEmail(EmailDto email);

}
