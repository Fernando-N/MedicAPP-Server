package cl.medicapp.service.services.role;

import cl.medicapp.service.dto.GenericResponseDto;
import cl.medicapp.service.dto.RoleDto;

import java.util.List;

public interface RoleService {

    List<RoleDto> getAll();

    RoleDto getByName(String name);

    RoleDto save(RoleDto request);

    RoleDto update(String roleName, RoleDto newRoleName);

    GenericResponseDto deleteRoleByName(String name);

}
