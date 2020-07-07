package cl.medicapp.service.util;

import cl.medicapp.service.document.NationalityDocument;
import cl.medicapp.service.dto.NationalityDto;
import org.dozer.DozerBeanMapper;

/**
 * Clase util para NationalityDto y NationalityDocument
 */
public class NationalityUtil {

    private static final DozerBeanMapper mapper = new DozerBeanMapper();

    public static NationalityDto toNationalityDto(NationalityDocument nationalityDocument) {
        return NationalityDto.builder().value(nationalityDocument.getId()).label(nationalityDocument.getName()).build();
    }

    public static NationalityDocument toNationalityDocument(NationalityDto nationalityDto) {
        return mapper.map(nationalityDto, NationalityDocument.class);
    }

    /**
     * Constructor privado para no permitir crear instancias de la clase
     */
    private NationalityUtil() {

    }

}
