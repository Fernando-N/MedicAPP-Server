package cl.medicapp.service.controller;

import cl.medicapp.service.dto.GenericResponseDto;
import cl.medicapp.service.dto.ResetPasswordRequestDto;
import cl.medicapp.service.dto.UserDto;
import cl.medicapp.service.services.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * Controlador de endpoints de autenticación
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    /**
     * Bean Servicio AuthService
     */
    private final AuthService authService;

    /**
     * Endpoint que realiza el registro de un usuario
     * @param user Objeto request con datos de usuario a registrar
     * @return user recibido sin contraseña
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto register(@Valid @RequestBody UserDto user) {
        return authService.register(user);
    }

    /**
     * Endpoint de recuperar contraseña
     * @param email Email a enviar token de recuperación
     * @return Respuesta ok siempre, independiente de si no existe la cuenta
     */
    @PostMapping("/forgot")
    public GenericResponseDto forgotPassword(@Valid @NotBlank @RequestParam @Email String email) {
        return authService.recoveryPassword(email);
    }

    /**
     * Endpoint para restablecer la contraseña
     * @param request Request con token y contraseña nueva
     * @return Usuario restablecido
     */
    @PostMapping("/reset")
    public UserDto resetPassword(@Valid @RequestBody ResetPasswordRequestDto request) {
        return authService.resetPassword(request);
    }

}
