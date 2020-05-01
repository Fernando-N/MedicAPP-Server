package cl.medicapp.service.security;

import cl.medicapp.service.constants.Constants;
import cl.medicapp.service.entity.UserEntity;
import cl.medicapp.service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Componente que agrega información adicional al token de acceso
 */
@Component
@RequiredArgsConstructor
public class TokenAdditionalInformation implements TokenEnhancer {

    private final UserRepository userRepository;

    /**
     * Agrega datos extra al token
     *
     * @param accessToken    token de acceso
     * @param authentication objeto de autenticación
     * @return OAuth2AccessToken
     */
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Optional<UserEntity> optionalUser = userRepository.findByEmailIgnoreCase(authentication.getName());

        Map<String, Object> additionalInformation = optionalUser.map(userEntity -> {
            Map<String, Object> extra = new HashMap<>();
            extra.put(Constants.FIRST_NAME, userEntity.getFirstName());
            extra.put(Constants.LAST_NAME, userEntity.getLastName());
            extra.put(Constants.EMAIL, userEntity.getEmail());
            return extra;
        }).orElseThrow(() -> new UsernameNotFoundException(String.format(Constants.USER_X_NOT_FOUND, authentication.getName())));

        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);

        return accessToken;
    }

}
