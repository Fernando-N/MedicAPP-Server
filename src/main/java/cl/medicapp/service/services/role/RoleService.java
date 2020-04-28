package cl.medicapp.service.services.role;

import cl.medicapp.service.dto.GenericResponseDto;
import cl.medicapp.service.dto.RoleDto;

import java.util.List;

public interface RoleService {

    List<RoleDto> getAll();

    RoleDto getByName(String name);

    RoleDto save(RoleDto roleDto);

    GenericResponseDto deleteRoleByName(String name);

}
