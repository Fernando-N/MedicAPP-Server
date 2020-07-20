package cl.medicapp.service.services.email;

import cl.medicapp.service.dto.EmailDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.*;

class EmailServiceImplTest {
    @Mock
    JavaMailSender mailSender;
    @Mock
    Logger log;
    @InjectMocks
    EmailServiceImpl emailServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testSendEmail() {
        emailServiceImpl.sendEmail(new EmailDto("email", "title", "body"));
    }

    @Test
    void testSendEmail2() {
        emailServiceImpl.sendEmail(EmailDto.builder().build());
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme