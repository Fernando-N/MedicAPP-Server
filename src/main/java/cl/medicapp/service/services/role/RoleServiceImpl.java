package cl.medicapp.service.services.role;

import cl.medicapp.service.annotation.FormatArgs;
import cl.medicapp.service.annotation.UpperCase;
import cl.medicapp.service.constants.Constants;
import cl.medicapp.service.document.RoleDocument;
import cl.medicapp.service.dto.GenericResponseDto;
import cl.medicapp.service.dto.RoleDto;
import cl.medicapp.service.holder.DocumentsHolder;
import cl.medicapp.service.repository.role.RoleRepository;
import cl.medicapp.service.util.GenericResponseUtil;
import cl.medicapp.service.util.RoleUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementacion servicio de roles
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    /**
     * Bean de repositorio de roles
     */
    private final RoleRepository roleRepository;

    /**
     * Obtener todos los roles
     * @return Lista de roles
     */
    @Override
    public List<RoleDto> getAll() {
        return DocumentsHolder.getInstance().getRoleDocumentList()
                .stream()
                .map(RoleUtil::toRoleDto)
                .collect(Collectors.toList());
    }

    /**
     * Obtener rol por su nombre
     * @param name
     * @return
     */
    @Override
    public RoleDto getByName(String name) {
        return DocumentsHolder.getInstance().getRoleDocumentList()
                .stream()
                .filter(role -> role.getName().toUpperCase().contains(name.toUpperCase()))
                .findFirst()
                .map(RoleUtil::toRoleDto)
                .orElseThrow(() -> GenericResponseUtil.buildGenericException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.getReasonPhrase(), String.format(Constants.ROLE_X_NOT_FOUND, name)));
    }

    /**
     * Guardar un rol
     * @param request Objeto con datos del rol
     * @return Rol guardado
     */
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

    /**
     * Actualizar un rol
     * @param roleName Nombre rol a actualizar
     * @param newRoleName Rol con cambios
     * @return Rol guardado
     */
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

    /**
     * Elimina un rol por su nombre
     * @param name Nombre de rol
     * @return Resultado
     */
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
