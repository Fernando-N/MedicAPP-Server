package cl.medicapp.service.services.auth;

import cl.medicapp.service.constants.Constants;
import cl.medicapp.service.dto.GenericResponseDto;
import cl.medicapp.service.dto.UserDto;
import cl.medicapp.service.entity.RoleEntity;
import cl.medicapp.service.entity.UserEntity;
import cl.medicapp.service.exception.GenericException;
import cl.medicapp.service.repository.RoleRepository;
import cl.medicapp.service.repository.UserRepository;
import cl.medicapp.service.services.email.EmailService;
import cl.medicapp.service.util.SimpleMailMessageUtil;
import cl.medicapp.service.util.UserUtil;
import lombok.RequiredArgsConstructor;
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
    public UserDto register(UserDto newUser) {
        userRepository.findByEmail(newUser.getEmail()).ifPresentOrElse(
                userEntity -> {
                    List<String> details = Collections.singletonList(String.format(Constants.EMAIL_X_ALREADY_REGISTER, newUser.getEmail()));
                    throw new GenericException(Constants.EMAIL_ALREADY_REGISTERED, details);
                },
                () -> {

                    RoleEntity roleUser = roleRepository.findByName("ROLE_USER").orElse(null);

                    UserEntity userEntity = UserUtil.toUserEntity(newUser);
                    userEntity.setPassword(passwordEncoder.encode(newUser.getPassword()));
                    userEntity.setRoleEntities(Collections.singletonList(roleUser));

                    userRepository.save(userEntity);
                }
        );

        newUser.setPassword(null);
        return newUser;
    }

    /**
     * Implementación endpoint de recuperar contraseña
     *
     * @param email Email a recuperar contraseña
     * @return Respuesta con mensaje indicando que si existe el correo recibira un mensaje
     */
    @Override
    public GenericResponseDto recoveryPassword(String email) {
        userRepository.findByEmail(email).ifPresent(userEntity -> {
            userEntity.setResetToken(UUID.randomUUID().toString());
            userRepository.save(userEntity);
            String body = "token to recovery password: ".concat(userEntity.getResetToken());
            SimpleMailMessage recoveryMail = SimpleMailMessageUtil.build("suppor@medicapp.cl", userEntity.getEmail(), "Password recovery", body);
            emailService.sendEmail(recoveryMail);
        });

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
        Optional<UserEntity> userOptional = userRepository.findByResetToken(token);

        UserEntity user = userOptional.map(userEntity -> {
            userEntity.setResetToken(null);
            userEntity.setPassword(passwordEncoder.encode(password));
            userRepository.save(userEntity);
            userEntity.setPassword(null);
            return userEntity;
        }).orElseThrow(() -> new UsernameNotFoundException("Email no encontrado!"));

        return UserUtil.toUserDto(user);
    }

}
