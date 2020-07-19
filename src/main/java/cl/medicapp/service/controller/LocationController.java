package cl.medicapp.service.controller;

import cl.medicapp.service.dto.LocationDto;
import cl.medicapp.service.services.location.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador de roles
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/location")
//@PreAuthorize("isAuthenticated()")
public class LocationController {

    private final LocationService locationService;

    @GetMapping("/{address}")
    public LocationDto getLongitudeAndLatitude(@PathVariable String address) {
        return locationService.getLongitudeAndLatitude(address);
    }

}

