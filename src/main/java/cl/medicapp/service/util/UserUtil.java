package cl.medicapp.service.util;

import cl.medicapp.service.document.UserDocument;
import cl.medicapp.service.dto.UserDto;
import org.dozer.DozerBeanMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Clase util para UserDto, UserDocument
 */
public class UserUtil {

    private static final DozerBeanMapper mapper = new DozerBeanMapper();

    /**
     * Transforma UserDocument -> UserDto
     *
     * @param userDocument target
     * @return UserDto
     */
    public static UserDto toUserDto(UserDocument userDocument) {
        return mapper.map(userDocument, UserDto.class);
    }

    /**
     * Transforma UserDto -> UserDocument
     *
     * @param userDto target
     * @return UserDocument
     */
    public static UserDocument toUserDocument(UserDto userDto) {
        return mapper.map(userDto, UserDocument.class);
    }

    /**
     * Obtiene el email del usuario logeado
     *
     * @return email usuario logeado
     */
    public static String getEmailUserLogged() {
        return ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }

    /**
     * Constructor privado para no permitir crear instancias de la clase
     */
    private UserUtil() {

    }

}
