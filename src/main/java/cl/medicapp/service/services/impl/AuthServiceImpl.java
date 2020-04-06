package cl.medicapp.service.services.impl;

import cl.medicapp.service.dto.UserDto;
import cl.medicapp.service.entity.UserEntity;
import cl.medicapp.service.exception.ErrorException;
import cl.medicapp.service.mapper.SimpleMailMessageMapper;
import cl.medicapp.service.mapper.UserMapper;
import cl.medicapp.service.repository.UserRepository;
import cl.medicapp.service.services.EmailService;
import cl.medicapp.service.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private HttpServletRequest request;

    @Autowired @Lazy
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDto register(UserDto newUser) {
        throwIfEmailExist(newUser.getEmail());
        setNewUserDefaultValues(newUser);
        UserDto returnUser = UserMapper.toUserDto(userRepository.save(UserMapper.toUserEntity(newUser)));
        returnUser.setPassword(null);
        return returnUser;
    }

    @Override
    public UserDto resetPassword(String token, String password) {

        //Validar que pasa si el token viene null

        Optional<UserEntity> userEntityOptional = userRepository.findByResetToken(token);

        if (userEntityOptional.isEmpty()) {
            throw new UsernameNotFoundException("Email no encontrado!");
        }

        UserEntity userEntity = userEntityOptional.get();

        userEntity.setResetToken(null);
        userEntity.setPassword(passwordEncoder.encode(password));

        userRepository.save(userEntity);
        userEntity.setPassword(null);
        return UserMapper.toUserDto(userEntity);
    }

    @Override
    public void recoveryPassword(String email) {
        throwIfEmailNotExist(email);
        UserEntity user = generatePasswordResetToken(email);
        String body = "token to recovery password: " + user.getResetToken();
        SimpleMailMessage recoveryMail = SimpleMailMessageMapper.build("suppor@medicapp.cl", user.getEmail(), "Password recovery", body);
        emailService.sendEmail(recoveryMail);
    }

    private UserEntity generatePasswordResetToken(String email) {
        UserEntity user = userRepository.findByEmail(email).get();
        user.setResetToken(UUID.randomUUID().toString());
        return userRepository.save(user);
    }

    private void setNewUserDefaultValues(UserDto usuario) {
        usuario.setAttemps(0);
        usuario.setEnabled(true);
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setCreatedOn(new Date());
    }

    private void throwIfEmailExist(String email) {
        boolean exist = userRepository.existsByEmail(email);
        if (exist) {
            String message = "Email already registered";
            List<String> details = Collections.singletonList(String.format("Email %s is already registered", email));
            throw new ErrorException(message, details);
        }
    }

    private void throwIfEmailNotExist(String email) {
        boolean exist = userRepository.existsByEmail(email);
        if (!exist) {
            String message = "Email not found";
            List<String> details = Collections.singletonList(String.format("Email %s not found", email));
            throw new ErrorException(message, details);
        }
    }

}
