package cl.medicapp.service.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.common.util.JsonParser;
import org.springframework.security.oauth2.common.util.JsonParserFactory;

import java.util.Map;

/**
 * Clase utilitaria de tokens
 */
public class TokenUtil {

    /**
     * Verificador
     */
    private static final JWTVerifier verifier;

    /**
     * Parseador
     */
    private static final JsonParser parser;

    static {
        Algorithm algorithm = Algorithm.HMAC256("codigosupersecreto");
        verifier = JWT.require(algorithm).build();
        parser = JsonParserFactory.create();
    }

    /**
     * Valida un token, en caso de no estar validado lanza una excepcion
     * @param authToken token
     */
    public static void validateJwtToken(String authToken) {
        try {
            verifier.verify(authToken);
        } catch (JWTVerificationException exception){
            throw GenericResponseUtil.buildGenericException(HttpStatus.UNAUTHORIZED, HttpStatus.UNAUTHORIZED.getReasonPhrase());
        }
    }

    /**
     * Obtiene un claim del token
     * @param authToken Token
     * @param key Llave de claim
     * @return Claim obtenido
     */
    public static String getClaim(String authToken, String key){
        String token = authToken.replace("Bearer ", "");
        Jwt decode = JwtHelper.decode(token);
        Map<String, Object> claims = parser.parseMap(decode.getClaims());

        if(claims.get(key) == null) {
            throw GenericResponseUtil.buildGenericException(HttpStatus.UNAUTHORIZED, HttpStatus.UNAUTHORIZED.getReasonPhrase());
        }

        return claims.get(key).toString();
    }

    /**
     * Constructor privado para no permitir crear instancias de la clase
     */
    private TokenUtil() {

    }

}
