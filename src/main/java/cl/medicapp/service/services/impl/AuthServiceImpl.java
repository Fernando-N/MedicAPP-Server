package cl.medicapp.service.services.impl;

import cl.medicapp.service.constants.Constants;
import cl.medicapp.service.dto.GenericResponseDto;
import cl.medicapp.service.dto.UserDto;
import cl.medicapp.service.entity.UserEntity;
import cl.medicapp.service.exception.GenericException;
import cl.medicapp.service.repository.UserRepository;
import cl.medicapp.service.services.AuthService;
import cl.medicapp.service.services.EmailService;
import cl.medicapp.service.util.SimpleMailMessageUtil;
import cl.medicapp.service.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Clase de servicio de autenticación
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    @Lazy
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * Implementación endpoint de registro
     *
     * @param newUser Nuevo usuario
     * @return Usuario nuevo
     */
    @Override
    public UserDto register(UserDto newUser) {
        throwIfEmailExist(newUser.getEmail());
        setNewUserDefaultValues(newUser);
        UserDto returnUser = UserUtil.toUserDto(userRepository.save(UserUtil.toUserEntity(newUser)));
        returnUser.setPassword(null);
        return returnUser;
    }

    /**
     * Implementación endpoint de recuperar contraseña
     *
     * @param email Email a recuperar contraseña
     * @return Respuesta con mensaje indicando que si existe el correo recibira un mensaje
     */
    //TODO Mejorar esto
    @Override
    public GenericResponseDto recoveryPassword(String email) {

        if (emailExist(email)) {
            UserEntity user = generatePasswordResetToken(email);
            String body = "token to recovery password: " + user.getResetToken();
            SimpleMailMessage recoveryMail = SimpleMailMessageUtil.build("suppor@medicapp.cl", user.getEmail(), "Password recovery", body);
            emailService.sendEmail(recoveryMail);
        }

        return GenericResponseDto.builder()
                .message("Email was sent successfully")
                .details(Collections.singletonList(String.format("if the email %s is registered you will receive an email to reset the password", email))).build();
    }

    /**
     * Implementación endpoint de restablecer contraseña
     *
     * @param token Token de restablecimiento de contraseña
     * @param password Contraseña nueva
     * @return Usuario actualizado
     */
    @Override
    public UserDto resetPassword(String token, String password) {
        Optional<UserEntity> userEntityOptional = userRepository.findByResetToken(token);
        if (userEntityOptional.isEmpty()) throw new UsernameNotFoundException("Email no encontrado!");

        UserEntity userEntity = userEntityOptional.get();
        userEntity.setResetToken(null);
        userEntity.setPassword(passwordEncoder.encode(password));
        userRepository.save(userEntity);

        userEntity.setPassword(null);
        return UserUtil.toUserDto(userEntity);
    }

    /**
     * Genera un UUID random y lo guarda en base de datos para que el usuario pueda restablecer su contraseña
     *
     * @param email Email a restablecer
     * @return Usuario a restablecer
     */
    private UserEntity generatePasswordResetToken(String email) {
        UserEntity user = userRepository.findByEmail(email).get();
        user.setResetToken(UUID.randomUUID().toString());
        return userRepository.save(user);
    }

    /**
     * Asigna valores por defecto y encripta la contraseña a usuarios nuevos
     *
     * @param usuario usuario nuevo
     */
    private void setNewUserDefaultValues(UserDto usuario) {
        usuario.setAttemps(0);
        usuario.setEnabled(true);
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
    }

    /**
     * Lanza una excepción del tipo GenericException en caso de que el email exista
     *
     * @param email Email a validar
     */
    private void throwIfEmailExist(String email) {
        boolean exist = userRepository.existsByEmail(email);
        if (exist) {
            List<String> details = Collections.singletonList(String.format(Constants.EMAIL_X_ALREADY_REGISTER, email));
            throw new GenericException(Constants.EMAIL_ALREADY_REGISTERED, details);
        }
    }

    /**
     * Valida si un email esta registrado
     *
     * @param email Email a validar
     * @return resultado
     */
    private boolean emailExist(String email) {
        return userRepository.existsByEmail(email);
    }

}
