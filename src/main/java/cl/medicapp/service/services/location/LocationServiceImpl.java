package cl.medicapp.service.services.location;

import cl.medicapp.service.dto.DrugstoreResponse;
import cl.medicapp.service.dto.LocationDto;
import cl.medicapp.service.dto.LocationResponseDto;
import cl.medicapp.service.dto.PlaceResponseDto;
import cl.medicapp.service.util.GenericResponseUtil;
import cl.medicapp.service.util.LocationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

/**
 * Implementacion de servicio de localizacion
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class LocationServiceImpl implements LocationService {

    /**
     * Rest template para peticiones a API
     */
    private final RestTemplate restTemplate;

    /**
     * API Key de PositionStack
     */
    @Value("${api.positionStack.key}")
    private String positionStackApiKey;

    /**
     * API Uri de PositionStack
     */
    @Value("${api.positionStack.uri}")
    private String positionStackApiUri;

    /**
     * API Key de GoogleMaps Places
     */
    @Value("${api.googlemaps.key}")
    private String googleMapsApiKey;

    /**
     * API Uri de PositionStack
     */
    @Value("${api.googlemaps.places.uri}")
    private String googleMapsPlacesApiUri;

    @Value("${api.googlemaps.places.radio}")
    private int googleMapsPlacesRadio;

    @Value("${api.farmanet.uri}")
    private String farmanetUri;

    /**
     * Obtener longitud y latitud de una direccion
     * @param address Direccion
     * @return Objeto con respuesta
     */
    @Override
    public LocationDto getLongitudeAndLatitude(String address) {
        return LocationUtil.toLocationDto(fetchPositionStack("forward", address));
    }

    @Override
    public LocationDto getLocation(double latitude, double longitude) {
        return LocationUtil.toLocationDto(fetchPositionStack("reverse", latitude + "," + longitude));
    }

    @Override
    public PlaceResponseDto getPlaces(String type, String keyword, double latitude, double longitude) {
        return LocationUtil.toPlaceResponseDto(fetchGoogleMapsPlace(type, keyword, latitude, longitude));
    }

    @Override
    public DrugstoreResponse getDrugstores(double latitude, double longitude) {
        List<DrugstoreResponse.ResponseAPI> farmanetList = fetchFarmanet();
        LocationDto locationDto = getLocation(latitude, longitude);
        PlaceResponseDto googlePlacesList = getPlaces("drugstore", "farmacia", latitude, longitude);

        return LocationUtil.filterDrugstores(locationDto, googlePlacesList, farmanetList);
    }

    /**
     * Metodo que consume la API PositionStack
     * @param query Dirección a buscar o Latitud y longitud separadas por coma.
     * @return Respuesta de API
     */
    private LocationResponseDto.Location fetchPositionStack(String type, String query) {
        String endpoint = String.format("%s/%s?access_key=%s&query=%s", positionStackApiUri, type, positionStackApiKey, query);

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

    /**
     * Metodo que consume la API Google Maps Places
     * @param query Dirección a buscar o Latitud y longitud separadas por coma.
     * @return Respuesta de API
     */
    private PlaceResponseDto.PlacesAPIResponseDto fetchGoogleMapsPlace(String type, String keyword, double latitude, double longitude) {
        String endpoint = String.format("%s?location=%s,%s&radius=%s&type=%s&keyword=%s&key=%s", googleMapsPlacesApiUri, latitude, longitude, googleMapsPlacesRadio, type, keyword, googleMapsApiKey);

        PlaceResponseDto.PlacesAPIResponseDto response;

        try {
            response = restTemplate.getForObject(endpoint, PlaceResponseDto.PlacesAPIResponseDto.class);
        }catch (Exception e) {
            log.error("Error obteniendo API PostionStack", e);
            throw GenericResponseUtil.buildGenericException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        }

        if (response == null || response.getResults() == null || response.getResults().isEmpty()) {
            throw GenericResponseUtil.buildGenericException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        }

        return response;
    }

    /**
     * Metodo que consume la API Google Maps Places
     * @param query Dirección a buscar o Latitud y longitud separadas por coma.
     * @return Respuesta de API
     */
    private List<DrugstoreResponse.ResponseAPI> fetchFarmanet() {
        ResponseEntity<List<DrugstoreResponse.ResponseAPI>> response;

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(httpHeaders);

        try {
            response = restTemplate.exchange(farmanetUri, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<DrugstoreResponse.ResponseAPI>>() {});
        }catch (Exception e) {
            log.error("Error obteniendo API PostionStack", e);
            throw GenericResponseUtil.buildGenericException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        }

        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null || response.getBody().isEmpty()) {
            throw GenericResponseUtil.buildGenericException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        }

        return response.getBody();
    }

}
