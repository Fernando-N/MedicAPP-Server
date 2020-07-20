package cl.medicapp.service.util;

import cl.medicapp.service.document.CommuneDocument;
import cl.medicapp.service.dto.CommuneDto;

/**
 * Clase utilitaria para comunas
 */
public class CommuneUtil {

    /**
     * Transforma a CommuneDto
     * @param communeDocument target
     * @return target convertido a CommuneDto
     */
    public static CommuneDto toCommuneDto(CommuneDocument communeDocument) {
        return CommuneDto.builder()
                .id(communeDocument.getId())
                .name(communeDocument.getName())
                .region(RegionUtil.toRegionDto(communeDocument.getRegion()))
                .build();
    }

    /**
     * Transforma a CommuneDocument
     * @param communeDto target
     * @return target convertido a CommuneDocument
     */
    public static CommuneDocument toCommuneDocument(CommuneDto communeDto) {
        return CommuneDocument.builder()
                .id(communeDto.getId())
                .name(communeDto.getName())
                .region(communeDto.getRegion() != null ? RegionUtil.toRegionDocument(communeDto.getRegion()) : null)
                .build();
    }

    /**
     * Constructor privado para no permitir crear instancias de la clase
     */
    private CommuneUtil() {

    }

}
