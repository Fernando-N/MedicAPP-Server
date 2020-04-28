package cl.medicapp.service.services.role;

import cl.medicapp.service.dto.GenericResponseDto;
import cl.medicapp.service.dto.RoleDto;
import cl.medicapp.service.exception.GenericException;
import cl.medicapp.service.repository.RoleRepository;
import cl.medicapp.service.util.RoleUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService{

    private final RoleRepository roleRepository;

    @Override
    public List<RoleDto> getAll() {
        List<RoleDto> roleDtoList = new ArrayList<>();
        roleRepository.findAll().forEach(roleEntity -> roleDtoList.add(RoleUtil.toRoleDto(roleEntity)));
        return roleDtoList;
    }

    @Override
    public RoleDto getByName(String name) {
        return roleRepository.findByName(name).map(RoleUtil::toRoleDto)
                .orElseThrow(() -> new GenericException("Role not found", Collections.singletonList("Role "+name+" not found")));
    }

    @Override
    public RoleDto save(RoleDto roleDto) {
        roleRepository.save(RoleUtil.toRoleEntity(roleDto));
        return roleDto;
    }

    @Override
    public GenericResponseDto deleteRoleByName(String name) {
        int rowsDelete = roleRepository.deleteByName(name);
        if (rowsDelete>1) {
            return GenericResponseDto.builder().message("Role deleted").details(Collections.singletonList("Role "+name+" delete")).build();
        }
        return GenericResponseDto.builder().message("Role not found").details(Collections.singletonList("Role "+name+" not found")).build();
    }
}
