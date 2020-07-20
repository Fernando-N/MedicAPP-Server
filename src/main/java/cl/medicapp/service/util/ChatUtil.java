package cl.medicapp.service.util;

import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;

import java.util.List;
import java.util.Optional;

/**
 * Clase utilitaria para chat
 */
public class ChatUtil {

    /**
     * Metodo que valida que el header de un mensaje tiene Authorization
     * @param simpMessageHeaderAccessor Headers mensaje
     */
    public static void validateHeaderContainAuthorizationValid(SimpMessageHeaderAccessor simpMessageHeaderAccessor) {

        Optional<List<String>> token = Optional.ofNullable(simpMessageHeaderAccessor.getNativeHeader("Authorization"));

        if (!token.isPresent()) {
            throw GenericResponseUtil.buildGenericException(HttpStatus.UNAUTHORIZED, HttpStatus.UNAUTHORIZED.getReasonPhrase());
        }

        TokenUtil.validateJwtToken(getTokenFromSimpMessageHeaderAccessor(simpMessageHeaderAccessor));

    }

    /**
     * Obtener email remitente de header
     * @param simpMessageHeaderAccessor Headers
     * @return Email remitente
     */
    public static String getEmailSender(SimpMessageHeaderAccessor simpMessageHeaderAccessor) {
        return TokenUtil.getClaim(getTokenFromSimpMessageHeaderAccessor(simpMessageHeaderAccessor), "EMAIL");
    }

    /**
     * Obtener id remitente de header
     * @param simpMessageHeaderAccessor Headers
     * @return Id remitente
     */
    public static String getIdSender(SimpMessageHeaderAccessor simpMessageHeaderAccessor) {
        return TokenUtil.getClaim(getTokenFromSimpMessageHeaderAccessor(simpMessageHeaderAccessor), "USER_ID");
    }

    /**
     * Obtiene token de header
     * @param simpMessageHeaderAccessor Headers
     * @return Token
     */
    private static String getTokenFromSimpMessageHeaderAccessor(SimpMessageHeaderAccessor simpMessageHeaderAccessor) {
        String[] tokenSplit = simpMessageHeaderAccessor.getNativeHeader("Authorization").get(0).split(" ");
        return tokenSplit[1];
    }

    /**
     * Constructor privado para no permitir crear instancias de la clase
     */
    private ChatUtil() {

    }

}
