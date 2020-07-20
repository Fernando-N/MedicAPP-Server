package cl.medicapp.service.controller;

import cl.medicapp.service.constants.Constants;
import cl.medicapp.service.dto.GenericResponseDto;
import cl.medicapp.service.dto.StatsDto;
import cl.medicapp.service.dto.UserDto;
import cl.medicapp.service.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controlador de paramedicos
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/paramedic")
@PreAuthorize("hasRole('ADMIN')")
public class ParamedicController {

    /**
     * Bean de servicio de usuarios
     */
    private final UserService userService;

    /**
     * Endpoint que obtiene lista de paramedicos
     * @return Lista de usuarios
     */
    @GetMapping("")
    @PreAuthorize("isAuthenticated()")
    public List<UserDto> getAll() {
        return userService.getAllByRole("PARAMEDIC");
    }

    /**
     * Endpoint que obtiene todos los paramedicos de una region
     * @param regionId Id de region a buscar
     * @return Lista de paramedicos encontrados
     */
    @GetMapping("/region/{regionId}")
    @PreAuthorize("isAuthenticated()")
    public List<UserDto> getAllByRegionId(@PathVariable String regionId) {
        return userService.getAllByRegionId(Constants.PARAMEDIC, regionId);
    }

    /**
     * Endpoint que obtiene todos los paramedicos de una comuna
     * @param communeId Id de comuna a buscar
     * @return Lista de paramedicos encontrados
     */
    @GetMapping("/paramedic/commune-{communeId}")
    @PreAuthorize("isAuthenticated()")
    public List<UserDto> getAllByRegionIdAndCommuneId(@PathVariable String communeId) {
        return userService.getAllByCommuneId(Constants.PARAMEDIC, communeId);
    }

    /**
     * Endpoint que obtiene los stats de un paramedico
     * @param id Id de paramedico a buscar
     * @return Stats de paramedico
     */
    @GetMapping("/{id}/stats")
    @PreAuthorize("isAuthenticated()")
    public StatsDto getStats(@PathVariable String id) {
        return userService.getStats(id);
    }

    /**
     * Endpoint que obtiene un paramedico por su correo
     * @param email Email
     * @return Paramedico encontrado
     */
    @GetMapping("/{email}")
    public UserDto getByEmail(@PathVariable String email) {
        return userService.getByEmail(email);
    }

    /**
     * Endpoint que obtiene paramedicos por su nombre y apellido
     * @param firstName Nombre
     * @param lastName  Apellido
     * @return Lista de paramedicos encontrados
     */
    @GetMapping("/{firstName}-{lastName}")
    public List<UserDto> getByName(@PathVariable String firstName, @PathVariable String lastName) {
        return userService.getByName(firstName, lastName);
    }

    /**
     * Endpoint que elimina un usuario por su correo
     * @param email Email
     * @return GenericResponse con detalles
     */
    @DeleteMapping("/{email}")
    public GenericResponseDto deleteByEmail(@PathVariable String email) {
        return userService.deleteById(email);
    }

}
