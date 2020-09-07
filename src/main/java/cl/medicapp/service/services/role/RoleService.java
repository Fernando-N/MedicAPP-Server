package cl.medicapp.service.services.role;

import cl.medicapp.service.dto.GenericResponseDto;
import cl.medicapp.service.dto.RoleDto;

import java.util.List;

/**
 * Interfaz de servicio de roles
 */
public interface RoleService {

    /**
     * @see RoleServiceImpl#getAll()
     */
    List<RoleDto> getAll();

    /**
     * @see RoleServiceImpl#getByName(String)
     */
    RoleDto getByName(String name);

    /**
     * @see RoleServiceImpl#save(RoleDto)
     */
    RoleDto save(RoleDto request);

    /**
     * @see RoleServiceImpl#update(String, RoleDto)
     */
    RoleDto update(String roleName, RoleDto newRoleName);

    /**
     * @see RoleServiceImpl#deleteByName(String)
     */
    GenericResponseDto deleteByName(String name);

}
