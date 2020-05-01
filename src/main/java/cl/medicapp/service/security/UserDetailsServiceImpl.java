package cl.medicapp.service.security;

import cl.medicapp.service.constants.Constants;
import cl.medicapp.service.document.UserDocument;
import cl.medicapp.service.repository.UserRepository;
import cl.medicapp.service.util.UserDetailsUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Clase implementación de UserDetailsService
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Carga el usuario a logearse
     *
     * @param username Nombre de usuario
     * @return UserDetails
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        log.info("Inicializado por {}", username);
        Optional<UserDocument> userOptional = userRepository.findByEmailIgnoreCaseAndEnabledTrue(username);
        return UserDetailsUtil.build(userOptional.orElseThrow(() -> new UsernameNotFoundException(String.format(Constants.USER_X_NOT_FOUND, username))));
    }

}
