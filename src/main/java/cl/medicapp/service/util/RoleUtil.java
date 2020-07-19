package cl.medicapp.service.util;

import cl.medicapp.service.document.RoleDocument;
import cl.medicapp.service.dto.RoleDto;

/**
 * Clase util para RoleDto y RoleDocument
 */
public class RoleUtil {

    public static RoleDto toRoleDto(RoleDocument roleDocument) {
        return RoleDto.builder()
                .id(roleDocument.getId())
                .name(roleDocument.getName())
                .build();
    }

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
