package cl.medicapp.service.security;

import cl.medicapp.service.constants.Constants;
import cl.medicapp.service.entity.UserEntity;
import cl.medicapp.service.exception.GenericException;
import cl.medicapp.service.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * Clase que maneja eventos de login
 */
@Slf4j
@Component
public class AuthenticationSuccessErrorHandler implements AuthenticationEventPublisher {

    @Autowired
    private UserRepository userRepository;

    /**
     * Evento de autenticación correcta
     *
     * @param authentication Objeto de usuario autenticado
     */
    @Override
    public void publishAuthenticationSuccess(Authentication authentication) {
        UserDetails userAuthenticated = (UserDetails) authentication.getPrincipal();
        log.debug(Constants.USER_JOINED_SUCCESSFUL, userAuthenticated.getUsername());

        UserEntity userEntity = this.userRepository.findByEmail(authentication.getName()).get();
        userEntity.setAttemps(0);
        userEntity.setLastLogin(new Timestamp(System.currentTimeMillis()));
        userRepository.save(userEntity);
    }

    /**
     * Evento de autenticación fallida
     *
     * @param exception      Excepcion ocurrida
     * @param authentication Usuario intento autenticación
     */
    @Override
    public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) throws GenericException {
        log.error(Constants.EXCEPTION_IN_LOGIN, exception);
    }

}
