package cl.medicapp.service.util;

import cl.medicapp.service.dto.LocationDto;
import cl.medicapp.service.dto.LocationResponseDto;

public class LocationUtil {

    public static LocationDto toLocationDto(LocationResponseDto.Location locationResponseDto) {
        return LocationDto.builder()
                .latitude(locationResponseDto.getLatitude())
                .longitude(locationResponseDto.getLongitude())
                .build();
    }

    private LocationUtil() {

    }

}
