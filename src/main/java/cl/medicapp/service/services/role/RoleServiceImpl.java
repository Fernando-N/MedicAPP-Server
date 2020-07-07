package cl.medicapp.service.services.role;

import cl.medicapp.service.annotation.FormatArgs;
import cl.medicapp.service.annotation.UpperCase;
import cl.medicapp.service.constants.Constants;
import cl.medicapp.service.document.RoleDocument;
import cl.medicapp.service.dto.GenericResponseDto;
import cl.medicapp.service.dto.RoleDto;
import cl.medicapp.service.repository.role.RoleRepository;
import cl.medicapp.service.util.GenericResponseUtil;
import cl.medicapp.service.util.RoleUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
//TODO aplicar logica utilizando la clase DocumentsHolder para evitar carga a la bd
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public List<RoleDto> getAll() {
        List<RoleDto> roleDtoList = new ArrayList<>();
        roleRepository.findAll().forEach(roleDocument -> roleDtoList.add(RoleUtil.toRoleDto(roleDocument)));
        return roleDtoList;
    }

    @Override
    public RoleDto getByName(String name) {
        return roleRepository.findByNameIgnoreCaseEndsWith(name).map(RoleUtil::toRoleDto)
                .orElseThrow(() -> GenericResponseUtil.buildGenericException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.getReasonPhrase(), String.format(Constants.ROLE_X_NOT_FOUND, name)));
    }

    @Override
    @FormatArgs
    public RoleDto save(RoleDto request) {
        if (!request.getName().startsWith(Constants.ROLE)) {
            request.setName(Constants.ROLE.concat(request.getName()));
        }

        Optional<RoleDocument> role = roleRepository.findByNameIgnoreCaseEndsWith(request.getName());

        if (role.isPresent()) {
            throw GenericResponseUtil.buildGenericException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(), String.format(Constants.ROLE_X_ALREADY_EXIST, request.getName()));
        }

        roleRepository.save(RoleUtil.toRoleDocument(request));

        return request;
    }

    @Override
    @FormatArgs
    public RoleDto update(@UpperCase String roleName, RoleDto newRoleName) {
        if (!roleName.startsWith(Constants.ROLE)) {
            roleName = Constants.ROLE.concat(roleName);
        }

        if (!newRoleName.getName().startsWith(Constants.ROLE)) {
            newRoleName.setName(Constants.ROLE.concat(newRoleName.getName()));
        }

        String tmpRoleName = roleName;
        Optional<RoleDocument> role = roleRepository.findByNameIgnoreCaseEndsWith(roleName);

        if (!role.isPresent()) {
            throw GenericResponseUtil.buildGenericException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.getReasonPhrase(), String.format(Constants.ROLE_X_NOT_FOUND, tmpRoleName));
        }

        role.get().setName(newRoleName.getName());
        roleRepository.save(role.get());

        return newRoleName;
    }

    @Override
    @FormatArgs
    public GenericResponseDto deleteByName(@UpperCase String name) {
        if (!name.startsWith(Constants.ROLE)) {
            name = Constants.ROLE.concat(name);
        }

        if (roleRepository.deleteByNameIgnoreCase(name) > 0) {
            return GenericResponseUtil.buildGenericResponse(Constants.ROLE_DELETED, String.format(Constants.ROLE_X_DELETED, name));
        }
        return GenericResponseUtil.buildGenericResponse(HttpStatus.NOT_FOUND.getReasonPhrase(), String.format(Constants.ROLE_X_NOT_FOUND, name));
    }
}
