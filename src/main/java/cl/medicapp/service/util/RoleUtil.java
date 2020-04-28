package cl.medicapp.service.util;

import cl.medicapp.service.dto.RoleDto;
import cl.medicapp.service.entity.RoleEntity;
import org.dozer.DozerBeanMapper;

/**
 * Clase util para UserDto, UserEntity, RoleDto y RoleEntity
 */
public class RoleUtil {

    private static final DozerBeanMapper mapper = new DozerBeanMapper();

    public static RoleDto toRoleDto(RoleEntity roleEntity) {
        return mapper.map(roleEntity, RoleDto.class);
    }

    public static RoleEntity toRoleEntity(RoleDto roleDto) {
        return mapper.map(roleDto, RoleEntity.class);
    }

    /**
     * Constructor privado para no permitir crear instancias de la clase
     */
    private RoleUtil() {

    }

}
