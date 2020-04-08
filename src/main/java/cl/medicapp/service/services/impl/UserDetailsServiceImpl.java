package cl.medicapp.service.services.impl;

import cl.medicapp.service.entity.UserEntity;
import cl.medicapp.service.repository.UserRepository;
import cl.medicapp.service.util.UserDetailsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Clase implementación de UserDetailsService
 */
@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Carga el usuario a logearse
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Inicializado por {}", username);

        Optional<UserEntity> userOptional = userRepository.findByEmailIgnoreCaseAndEnabledTrue(username);
        userOptional.orElseThrow(() -> new UsernameNotFoundException(username.concat(" not found")));

        return UserDetailsUtil.build(userOptional.get());
    }

}
