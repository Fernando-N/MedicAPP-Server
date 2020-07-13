package cl.medicapp.service.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.http.HttpStatus;

public class TokenUtil {

    private TokenUtil() {

    }

    private static final JWTVerifier verifier;

    static {
        Algorithm algorithm = Algorithm.HMAC256("codigosupersecreto");
        verifier = JWT.require(algorithm).build();
    }

    public static void validateJwtToken(String authToken) {
        try {
            verifier.verify(authToken);
        } catch (JWTVerificationException exception){
            throw GenericResponseUtil.buildGenericException(HttpStatus.UNAUTHORIZED, HttpStatus.UNAUTHORIZED.getReasonPhrase());
        }
    }

}
