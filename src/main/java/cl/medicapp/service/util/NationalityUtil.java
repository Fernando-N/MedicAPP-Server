package cl.medicapp.service.util;

import cl.medicapp.service.document.NationalityDocument;
import cl.medicapp.service.dto.NationalityDto;

/**
 * Clase util para NationalityDto y NationalityDocument
 */
public class NationalityUtil {

    public static NationalityDto toNationalityDto(NationalityDocument nationalityDocument) {
        return NationalityDto.builder()
                .value(nationalityDocument.getId())
                .label(nationalityDocument.getName())
                .build();
    }

    public static NationalityDocument toNationalityDocument(NationalityDto nationalityDto) {
        return NationalityDocument.builder()
                .name(nationalityDto.getLabel())
                .build();
    }

    /**
     * Constructor privado para no permitir crear instancias de la clase
     */
    private NationalityUtil() {

    }

}
