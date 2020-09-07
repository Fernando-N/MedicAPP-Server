package cl.medicapp.service.services.location;

import cl.medicapp.service.dto.DrugstoreResponse;
import cl.medicapp.service.dto.LocationDto;
import cl.medicapp.service.dto.PlaceResponseDto;

/**
 * Interfaz de servicio de localizacion
 */
public interface LocationService {

    /**
     * @see LocationServiceImpl#getLongitudeAndLatitude(String)
     */
    LocationDto getLongitudeAndLatitude(String address);

    LocationDto getLocation(double latitude, double longitude);

    PlaceResponseDto getPlaces(String type, String keyword, double latitude, double longitude);

    DrugstoreResponse getDrugstores(double latitude, double longitude);

}
