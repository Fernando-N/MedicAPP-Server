package cl.medicapp.service.mapper;

import cl.medicapp.service.dto.RoleDto;
import cl.medicapp.service.dto.UserDto;
import cl.medicapp.service.entity.RoleEntity;
import cl.medicapp.service.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.dozer.DozerBeanMapper;

/**
 * Clase map para Users
 */
@Slf4j
public class UserMapper {

    private static DozerBeanMapper mapper = new DozerBeanMapper();

    public static UserDto toUserDto(UserEntity userEntity) {
        return mapper.map(userEntity, UserDto.class);
    }

    public static UserEntity toUserEntity(UserDto userDto) {
        return mapper.map(userDto, UserEntity.class);
    }

    public static RoleDto toRoleDto(RoleEntity roleEntity) {
        return mapper.map(roleEntity, RoleDto.class);
    }

    public static RoleEntity toRoleEntity(RoleDto roleDto) {
        return mapper.map(roleDto, RoleEntity.class);
    }

}
