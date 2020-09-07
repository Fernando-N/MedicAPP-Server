package cl.medicapp.service.util;

import cl.medicapp.service.document.RoleDocument;
import cl.medicapp.service.dto.RoleDto;

/**
 * Clase util para roles
 */
public class RoleUtil {

    /**
     * Convierte RoleDocument a RoleDto
     * @param roleDocument target
     * @return target convertido a RoleDto
     */
    public static RoleDto toRoleDto(RoleDocument roleDocument) {
        return RoleDto.builder()
                .id(roleDocument.getId())
                .name(roleDocument.getName())
                .build();
    }

    /**
     * Convierte RoleDto a RoleDocument
     * @param roleDto target
     * @return target convertido a RoleDocument
     */
    public static RoleDocument toRoleDocument(RoleDto roleDto) {
        return RoleDocument.builder()
                .name(roleDto.getName())
                .build();
    }

    /**
     * Constructor privado para no permitir crear instancias de la clase
     */
    private RoleUtil() {

    }

}
