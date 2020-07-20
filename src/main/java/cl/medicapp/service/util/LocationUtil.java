package cl.medicapp.service.util;

import cl.medicapp.service.dto.LocationDto;
import cl.medicapp.service.dto.LocationResponseDto;

/**
 * Clase utilitaria para locations
 */
public class LocationUtil {

    /**
     * Transforma respuesta de API a LocationDto
     * @param locationResponseDto target
     * @return target convertido a LocationDto
     */
    public static LocationDto toLocationDto(LocationResponseDto.Location locationResponseDto) {
        return LocationDto.builder()
                .latitude(locationResponseDto.getLatitude())
                .longitude(locationResponseDto.getLongitude())
                .build();
    }

    /**
     * Constructor privado para no permitir crear instancias de la clase
     */
    private LocationUtil() {

    }

}
