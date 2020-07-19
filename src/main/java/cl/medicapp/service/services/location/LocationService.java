package cl.medicapp.service.services.location;

import cl.medicapp.service.dto.LocationDto;

public interface LocationService {

    LocationDto getLongitudeAndLatitude(String address);

}
