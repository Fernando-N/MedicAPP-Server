package cl.medicapp.service.security;

import cl.medicapp.service.constants.Constants;
import cl.medicapp.service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Clase que maneja eventos de login
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class AuthenticationSuccessErrorHandler implements AuthenticationEventPublisher {

    private final UserRepository userRepository;

    /**
     * Evento de autenticación correcta
     *
     * @param authentication Objeto de usuario autenticado
     */
    @Override
    public void publishAuthenticationSuccess(Authentication authentication) {
        UserDetails userAuthenticated = (UserDetails) authentication.getPrincipal();
        log.debug(Constants.USER_JOINED_SUCCESSFUL, userAuthenticated.getUsername());

        userRepository.findByEmailIgnoreCase(authentication.getName()).ifPresent(userEntity -> {
            userEntity.setAttemps(0);
            userEntity.setLastLogin(new Date());
            userRepository.save(userEntity);
        });
    }

    /**
     * Evento de autenticación fallida
     *
     * @param exception      Excepcion ocurrida
     * @param authentication Usuario intento autenticación
     */
    @Override
    public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
        log.error(Constants.EXCEPTION_IN_LOGIN, exception);
    }

}
