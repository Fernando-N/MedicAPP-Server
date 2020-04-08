package cl.medicapp.service.constants;

/**
 * Clase de constantes
 */
public class Constants {

    public static final String FIRST_NAME = "FIRST_NAME";

    public static final String LAST_NAME = "LAST_NAME";

    public static final String EMAIL = "EMAIL";

    public static final String UNAUTHORIZED_RESOURCE = "Access to this resource is denied";

    public static final String USER_NOT_FOUND = "{} not found";

    public static final String USER_JOINED_SUCCESSFUL = "{} ingreso correctamente";

    public static final String EMAIL_ALREADY_REGISTERED = "Email already registered";

    public static final String EMAIL_X_ALREADY_REGISTER = "Email %s is already registered!";

    public static final String X_PARAMETER_IS_MISSING = "%s parameter is missing";

    public static final String X_METHOD_IS_NOT_SUPPORT = " method is not supported for this request. Supported methods are ";

    public static final String X_MEDIA_TYPE_IS_NO_SUPPORT = " media type is not supported. Supported media types are ";

    public static final String EXCEPTION_IN_LOGIN = "Excepcion en login";

    public static final String WILDCARD = "*";

    public static final String[] SECURITY_SCOPES = {"read", "write"};

    public static final String[] SECURITY_AUTHORIZED_GRANT_TYPE = {"password", "refresh_token"};

    public static final int SECURITY_TOKEN_VALIDITY_SECONDS = 600;

    public static final String[] SECURITY_ALLOWED_METHODS = {"POST", "GET", "PUT", "DELETE", "OPTIONS"};

    public static final String[] SECURITY_ALLOWED_HEADERS = {"Authorization", "Content-Type"};

}
