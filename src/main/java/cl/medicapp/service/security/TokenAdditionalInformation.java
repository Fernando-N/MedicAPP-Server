package cl.medicapp.service.security;

import cl.medicapp.service.constants.Constants;
import cl.medicapp.service.entity.UserEntity;
import cl.medicapp.service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
public class TokenAdditionalInformation implements TokenEnhancer {

    @Autowired
    private UserRepository usuarioRepository;

    /**
     * Agrega datos extra al token
     *
     * @param accessToken    token de acceso
     * @param authentication objeto de autenticación
     * @return
     */
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Optional<UserEntity> optionalUser = usuarioRepository.findByEmail(authentication.getName());

        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException(authentication.getName().concat(Constants.USER_NOT_FOUND));
        }

        UserEntity user = optionalUser.get();

        Map<String, Object> extra = new HashMap<>();
        extra.put(Constants.FIRST_NAME,  user.getFirstName());
        extra.put(Constants.LAST_NAME,   user.getLastName());
        extra.put(Constants.EMAIL,       user.getEmail());

        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(extra);

        return accessToken;
    }

}
