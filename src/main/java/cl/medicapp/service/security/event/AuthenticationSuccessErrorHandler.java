package cl.medicapp.service.security.event;

import cl.medicapp.service.entity.UserEntity;
import cl.medicapp.service.exception.ErrorException;
import cl.medicapp.service.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Optional;

/**
 * Clase que captura y controla eventos de login
 */
@Slf4j
@Component
public class AuthenticationSuccessErrorHandler implements AuthenticationEventPublisher {

    /**
     * Repositorio de usuario
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Evento de autenticación correcta
     *
     * @param authentication objeto de usuario autenticado
     */
    @Override
    public void publishAuthenticationSuccess(Authentication authentication) {
        UserDetails user = (UserDetails) authentication.getPrincipal();
        log.info("{} ingreso correctamente", user.getUsername());
        UserEntity usuario = this.userRepository.findByEmail(authentication.getName()).get();

        usuario.setAttemps(0);
        usuario.setLastLogin(new Timestamp(System.currentTimeMillis()));
        userRepository.save(usuario);
    }

    /**
     * Evento de autenticación fallida
     *
     * @param e              excepcion ocurrida
     * @param authentication usuario intento autenticación
     */
    @Override
    public void publishAuthenticationFailure(AuthenticationException e, Authentication authentication) throws ErrorException{
        log.info("Excepcion en login [{}] ", e.getMessage());


/*
        Logica para agregar intentos de login

        Optional<UserEntity> optionalUser = userRepository.findByEmail(authentication.getName());

        if (optionalUser.isEmpty()) {
            throw new ErrorException("invalid_grant", Collections.singletonList(String.format("User %s not found", authentication.getName())));
        }

        UserEntity userEntity = optionalUser.get();

        log.info("Intentos actual del usuario [{}] es de [{}]", userEntity.getEmail(), userEntity.getAttemps());

        if (!userEntity.getEnabled()) {
            throw new ErrorException("invalid_grant", Collections.singletonList(String.format("User %s is disabled", authentication.getName())));
        }

        userEntity.setAttemps(userEntity.getAttemps() + 1);

        if (userEntity.getAttemps() >= 3) {
            log.error("Usuario [{}] se ha deshabilitado por 3 intentos fallidos", userEntity.getEmail());
            userEntity.setEnabled(false);
        }

        userRepository.save(userEntity);*/
    }

}
