package cl.medicapp.service.util;

import cl.medicapp.service.document.RegionDocument;
import cl.medicapp.service.dto.RegionDto;
import org.dozer.DozerBeanMapper;

/**
 * Clase util para RegionDto y RegionDocument
 */
public class RegionUtil {

    private static final DozerBeanMapper mapper = new DozerBeanMapper();

    public static RegionDto toRegionDto(RegionDocument regionDocument) {
        return RegionDto.builder().value(regionDocument.getId()).label(regionDocument.getOrdinal() + " " + regionDocument.getName()).build();
    }

    public static RegionDocument toRegionDocument(RegionDto regionDto) {
        return mapper.map(regionDto, RegionDocument.class);
    }

    /**
     * Constructor privado para no permitir crear instancias de la clase
     */
    private RegionUtil() {

    }

}
