package cl.medicapp.service.util;

import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;

import java.util.List;
import java.util.Optional;

/**
 * Clase utilitaria para chat
 */
public class ChatUtil {

    private ChatUtil() {

    }

    public static void validateHeaderContainAuthorizationValid(SimpMessageHeaderAccessor simpMessageHeaderAccessor) {

        Optional<List<String>> token = Optional.ofNullable(simpMessageHeaderAccessor.getNativeHeader("Authorization"));

        if (!token.isPresent()) {
            throw GenericResponseUtil.buildGenericException(HttpStatus.UNAUTHORIZED, HttpStatus.UNAUTHORIZED.getReasonPhrase());
        }

        TokenUtil.validateJwtToken(getTokenFromSimpMessageHeaderAccessor(simpMessageHeaderAccessor));

    }

    public static String getEmailSender(SimpMessageHeaderAccessor simpMessageHeaderAccessor) {
        return TokenUtil.getClaim(getTokenFromSimpMessageHeaderAccessor(simpMessageHeaderAccessor), "EMAIL");
    }

    public static String getIdSender(SimpMessageHeaderAccessor simpMessageHeaderAccessor) {
        return TokenUtil.getClaim(getTokenFromSimpMessageHeaderAccessor(simpMessageHeaderAccessor), "USER_ID");
    }

    private static String getTokenFromSimpMessageHeaderAccessor(SimpMessageHeaderAccessor simpMessageHeaderAccessor) {
        String[] tokenSplit = simpMessageHeaderAccessor.getNativeHeader("Authorization").get(0).split(" ");
        return tokenSplit[1];
    }


}
