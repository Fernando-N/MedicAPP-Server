package cl.medicapp.service.util;

import cl.medicapp.service.document.RoleDocument;
import cl.medicapp.service.dto.RoleDto;
import org.dozer.DozerBeanMapper;

/**
 * Clase util para RoleDto y RoleDocument
 */
public class RoleUtil {

    private static final DozerBeanMapper mapper = new DozerBeanMapper();

    public static RoleDto toRoleDto(RoleDocument roleDocument) {
        return mapper.map(roleDocument, RoleDto.class);
    }

    public static RoleDocument toRoleDocument(RoleDto roleDto) {
        return mapper.map(roleDto, RoleDocument.class);
    }

    /**
     * Constructor privado para no permitir crear instancias de la clase
     */
    private RoleUtil() {

    }

}
