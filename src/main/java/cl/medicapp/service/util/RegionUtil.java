package cl.medicapp.service.util;

import cl.medicapp.service.document.RegionDocument;
import cl.medicapp.service.dto.RegionDto;

/**
 * Clase util para regiones
 */
public class RegionUtil {

    /**
     * Convierte RegionDocument a RegionDto
     * @param regionDocument target
     * @return target convertido a RegionDto
     */
    public static RegionDto toRegionDto(RegionDocument regionDocument) {
        return RegionDto.builder()
                .id(regionDocument.getId())
                .label(regionDocument.getOrdinal() + " " + regionDocument.getName())
                .build();
    }

    /**
     * Convierte RegionDto a RegionDocument
     * @param regionDto target
     * @return target como RegionDocument
     */
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
