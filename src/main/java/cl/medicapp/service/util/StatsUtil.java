package cl.medicapp.service.util;

import cl.medicapp.service.document.StatsDocument;
import cl.medicapp.service.dto.StatsDto;

/**
 * Clase util para estadisticas
 */
public class StatsUtil {

    /**
     * Convierte StatsDocument a StatsDto
     * @param statsDocument target
     * @return target convertido a StatsDto
     */
    public static StatsDto toStatsDto(StatsDocument statsDocument) {
        return StatsDto.builder()
                .valuations(statsDocument.getValuations())
                .rating(statsDocument.getRating())
                .contacts(statsDocument.getContacts())
                .build();
    }

    /**
     * Constructor privado para no permitir crear instancias de la clase
     */
    private StatsUtil() {

    }

}
