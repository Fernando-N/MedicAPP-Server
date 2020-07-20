package cl.medicapp.service.services.location;

import cl.medicapp.service.dto.LocationDto;

/**
 * Interfaz de servicio de localizacion
 */
public interface LocationService {

    /**
     * @see LocationServiceImpl#getLongitudeAndLatitude(String)
     */
    LocationDto getLongitudeAndLatitude(String address);

}
