package cl.medicapp.service.util;

import cl.medicapp.service.dto.UserDto;
import cl.medicapp.service.entity.UserEntity;
import org.dozer.DozerBeanMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Clase util para UserDto, UserEntity, RoleDto y RoleEntity
 */
public class UserUtil {

    private static final DozerBeanMapper mapper = new DozerBeanMapper();

    /**
     * Transforma UserEntity -> UserDto
     * @param userEntity userEntity
     * @return UserDto
     */
    public static UserDto toUserDto(UserEntity userEntity) {
        return mapper.map(userEntity, UserDto.class);
    }

    /**
     * Transforma UserDto -> UserEntity
     * @param userDto userDto
     * @return UserEntity
     */
    public static UserEntity toUserEntity(UserDto userDto) {
        return mapper.map(userDto, UserEntity.class);
    }

    /**
     * Obtiene el email del usuario logeado
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
