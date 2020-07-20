package cl.medicapp.service.services.location;

import cl.medicapp.service.dto.LocationDto;
import cl.medicapp.service.dto.LocationResponseDto;
import cl.medicapp.service.util.GenericResponseUtil;
import cl.medicapp.service.util.LocationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Implementacion de servicio de localizacion
 */
@Service
@Slf4j
public class LocationServiceImpl implements LocationService {

    /**
     * API Key de PositionStack
     */
    @Value("${api.positionStack.key}")
    private String apiKey;

    /**
     * API Uri de PositionStack
     */
    @Value("${api.positionStack.uri}")
    private String apiUri;

    /**
     * Obtener longitud y latitud de una direccion
     * @param address Direccion
     * @return Objeto con respuesta
     */
    @Override
    public LocationDto getLongitudeAndLatitude(String address) {
        return LocationUtil.toLocationDto(consumeAPI(address));
    }

    /**
     * Metodo que consume la API
     * @param address Direccion
     * @return Respuesta de API
     */
    private LocationResponseDto.Location consumeAPI(String address) {
        RestTemplate restTemplate = new RestTemplate();
        String endpoint = String.format("%s/forward?access_key=%s&query=%s", apiUri, apiKey, address);

        LocationResponseDto response;

        try {
             response = restTemplate.getForObject(endpoint, LocationResponseDto.class);
        }catch (Exception e) {
            log.error("Error obteniendo API PostionStack", e);
            throw GenericResponseUtil.buildGenericException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        }

        if (response == null || response.getData() == null || response.getData().isEmpty()) {
            throw GenericResponseUtil.buildGenericException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        }

        return response.getData().get(0);
    }

}
