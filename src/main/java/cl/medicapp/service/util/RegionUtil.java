package cl.medicapp.service.util;

import cl.medicapp.service.document.RegionDocument;
import cl.medicapp.service.dto.RegionDto;

/**
 * Clase util para RegionDto y RegionDocument
 */
public class RegionUtil {

    public static RegionDto toRegionDto(RegionDocument regionDocument) {
        return RegionDto.builder()
                .id(regionDocument.getId())
                .label(regionDocument.getOrdinal() + " " + regionDocument.getName())
                .build();
    }

    public static RegionDocument toRegionDocument(RegionDto regionDto) {
        return RegionDocument.builder()
                .name(regionDto.getLabel())
                .ordinal(regionDto.getLabel())
                .build();
    }

    /**
     * Constructor privado para no permitir crear instancias de la clase
     */
    private RegionUtil() {

    }

}
