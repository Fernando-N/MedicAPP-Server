package cl.medicapp.service.services.auth;

import cl.medicapp.service.annotation.FormatArgs;
import cl.medicapp.service.constants.Constants;
import cl.medicapp.service.document.RoleDocument;
import cl.medicapp.service.document.UserDocument;
import cl.medicapp.service.dto.GenericResponseDto;
import cl.medicapp.service.dto.ResetPasswordRequestDto;
import cl.medicapp.service.dto.UserDto;
import cl.medicapp.service.repository.RoleRepository;
import cl.medicapp.service.repository.UserRepository;
import cl.medicapp.service.services.email.EmailService;
import cl.medicapp.service.util.GenericResponseUtil;
import cl.medicapp.service.util.SimpleMailMessageUtil;
import cl.medicapp.service.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;

/**
 * Clase de servicio de autenticación
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final EmailService emailService;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * Implementación endpoint de registro
     *
     * @param newUser Nuevo usuario
     * @return Usuario nuevo
     */
    @Override
    @FormatArgs
    public UserDto register(UserDto newUser) {
        userRepository.findByEmailIgnoreCase(newUser.getEmail()).ifPresentOrElse(
                userDocument -> {
                    throw GenericResponseUtil.buildGenericException(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), String.format(Constants.EMAIL_X_ALREADY_REGISTER, newUser.getEmail()));
                },
                () -> {

                    RoleDocument roleUser = roleRepository.findByNameIgnoreCaseEndsWith("ROLE_USER").orElseThrow();

                    UserDocument userDocument = UserUtil.toUserDocument(newUser);
                    userDocument.setPassword(passwordEncoder.encode(newUser.getPassword()));
                    userDocument.setRoleEntities(Collections.singletonList(roleUser));

                    userRepository.save(userDocument);
                }
        );
        return newUser;
    }

    /**
     * Implementación endpoint de recuperar contraseña
     *
     * @param email Email a recuperar contraseña
     * @return Respuesta con mensaje indicando que si existe el correo recibira un mensaje
     */
    //TODO Mover esto a constantes y generar plantilla mail
    @Override
    public GenericResponseDto recoveryPassword(String email) {
        userRepository.findByEmailIgnoreCase(email).ifPresent(userDocument -> {
            userDocument.setResetToken(UUID.randomUUID().toString());
            userRepository.save(userDocument);
            String body = "token to recovery password: ".concat(userDocument.getResetToken());
            SimpleMailMessage recoveryMail = SimpleMailMessageUtil.build("suppor@medicapp.cl", userDocument.getEmail(), "Password recovery", body);
            emailService.sendEmail(recoveryMail);
        });

        return GenericResponseDto.builder()
                .message("Email was sent successfully")
                .details(Collections.singletonList(String.format("if the email %s is registered you will receive an email to reset the password", email))).build();
    }

    /**
     * Implementación endpoint de restablecer contraseña
     *
     * @param request Request con token y contraseña
     *                return Usuario actualizado
     */
    @Override
    public UserDto resetPassword(ResetPasswordRequestDto request) {

        if (!request.getPassword().equals(request.getPasswordConfirmation())) {
            throw GenericResponseUtil.buildGenericException(HttpStatus.BAD_REQUEST.value(), Constants.PASSWORD_MUST_MATCH, Constants.INPUT_PASSWORDS_NOT_MATCH);
        }

        if (request.getPassword().length() < 6 || request.getPassword().length() > 16) {
            throw GenericResponseUtil.buildGenericException(HttpStatus.BAD_REQUEST.value(), Constants.PASSWORD_MUST_BE_BETWEEN, Constants.PASSWORD_MUST_BE_BETWEEN);
        }

        UserDocument user = userRepository.findByResetToken(request.getToken()).map(userDocument -> {
            userDocument.setResetToken(null);
            userDocument.setPassword(passwordEncoder.encode(request.getPassword()));
            userRepository.save(userDocument);
            return userDocument;
        }).orElseThrow(() -> GenericResponseUtil.buildGenericException(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), String.format(Constants.TOKEN_NOT_FOUND, request.getToken())));

        return UserUtil.toUserDto(user);
    }

}
