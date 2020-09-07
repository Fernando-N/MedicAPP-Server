package cl.medicapp.service.services.email;

import cl.medicapp.service.dto.EmailDto;

/**
 * Interfaz de servicio de email
 */
public interface EmailService {

    /**
     * @see EmailServiceImpl#sendEmail(EmailDto)
     */
    void sendEmail(EmailDto email);

}
