package cl.medicapp.service.controller;

import cl.medicapp.service.dto.LocationDto;
import cl.medicapp.service.services.location.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador de servicio de locación
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/location")
@PreAuthorize("isAuthenticated()")
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

}

