package cl.medicapp.service.util;

import cl.medicapp.service.document.CommuneDocument;
import cl.medicapp.service.dto.CommuneDto;

/**
 * Clase util para CommuneDto y CommuneDocument
 */
public class CommuneUtil {

    public static CommuneDto toCommuneDto(CommuneDocument communeDocument) {
        return CommuneDto.builder()
                .value(communeDocument.getId())
                .label(communeDocument.getName())
                .region(RegionUtil.toRegionDto(communeDocument.getRegion()))
                .build();
    }

    public static CommuneDocument toCommuneDocument(CommuneDto communeDto) {
        return CommuneDocument.builder()
                .id(communeDto.getValue())
                .name(communeDto.getLabel())
                .region(RegionUtil.toRegionDocument(communeDto.getRegion()))
                .build();
    }

    /**
     * Constructor privado para no permitir crear instancias de la clase
     */
    private CommuneUtil() {

    }

}
