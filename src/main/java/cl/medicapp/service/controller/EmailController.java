package cl.medicapp.service.controller;

import cl.medicapp.service.dto.EmailDto;
import cl.medicapp.service.services.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
@PreAuthorize("hasRole('ADMIN')")
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/send")
    @ResponseStatus(HttpStatus.CREATED)
    public void sendEmail(@Valid @RequestBody EmailDto user) {
        emailService.sendEmail(user);
    }

}
