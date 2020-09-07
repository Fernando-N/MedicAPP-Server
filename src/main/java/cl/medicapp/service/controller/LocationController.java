package cl.medicapp.service.controller;

import cl.medicapp.service.dto.DrugstoreResponse;
import cl.medicapp.service.dto.LocationDto;
import cl.medicapp.service.dto.PlaceResponseDto;
import cl.medicapp.service.services.location.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador de servicio de locación
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/location")
@PreAuthorize("permitAll()")
public class LocationController {

    /**
     * Bean de servicio de locación
     */
    private final LocationService locationService;

    /**
     * Endpoint que retorna Longitud y Latitud de una dirección
     * @param address Dirección a buscar
     * @return Longitud y latitud de dirección
     */
    @GetMapping("/{address}")
    public LocationDto getLongitudeAndLatitude(@PathVariable String address) {
        return locationService.getLongitudeAndLatitude(address);
    }

    @GetMapping("")
    public LocationDto getLocation(@RequestParam double latitude, @RequestParam double longitude) {
        return locationService.getLocation(latitude, longitude);
    }

    @GetMapping("/hospitals")
    public PlaceResponseDto getPlaces(@RequestParam double latitude, @RequestParam double longitude) {
        return locationService.getPlaces("hospital", "hospital", latitude, longitude);
    }

    @GetMapping("/drugstores")
    public DrugstoreResponse getDrugstores(@RequestParam double latitude, @RequestParam double longitude) {
        return locationService.getDrugstores(latitude, longitude);
    }

}

