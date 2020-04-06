package cl.medicapp.service.controller;

import cl.medicapp.service.dto.UserDto;
import cl.medicapp.service.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * Controlador de autenticación
 */
@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/oauth/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto register(@Valid @RequestBody UserDto user) {
        return authService.register(user);
    }

    @PostMapping("/oauth/forgot")
    public void forgotPassword(@Valid @NotBlank @RequestParam @Email String email) {
        authService.recoveryPassword(email);
    }

    @PostMapping("/oauth/reset")
    public UserDto resetPassword(@Valid @NotBlank @RequestParam String resetToken, @Valid @NotBlank @RequestParam String password) {
        return authService.resetPassword(resetToken, password);
    }

}
