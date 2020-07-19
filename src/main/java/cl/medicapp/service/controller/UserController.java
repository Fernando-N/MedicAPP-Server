package cl.medicapp.service.controller;

import cl.medicapp.service.dto.ContentDto;
import cl.medicapp.service.dto.GenericResponseDto;
import cl.medicapp.service.dto.UserDto;
import cl.medicapp.service.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador de autenticación
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {

    private final UserService userService;

    /**
     * Endpoint que obtiene todos los usuarios
     *
     * @return Lista de usuarios
     */
    @GetMapping("")
    public List<UserDto> getAll() {
        return userService.getAll();
    }

    /**
     * Endpoint que obtiene el perfil del usuario logead
     *
     * @return Datos de usuario logeado
     */
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public UserDto getOwnProfile() {
        return userService.getOwnProfile();
    }

    /**
     * Endpoint que obtiene todos los usuarios
     *
     * @return Lista de usuarios
     */
    @GetMapping("/paramedic")
    @PreAuthorize("isAuthenticated()")
    public List<UserDto> getAllByRole() {
        return userService.getAllByRole("PARAMEDIC");
    }

    @GetMapping("/paramedic/{regionId}/")
    @PreAuthorize("isAuthenticated()")
    public List<UserDto> getAllByRegionId(@PathVariable String regionId) {
        return userService.getAllByRegionId("PARAMEDIC", regionId);
    }

    /**
     * Endpoint que obtiene usuarios por su id
     *
     * @param id Id de usuario
     * @return Usuario encontrado
     */
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public UserDto getById(@PathVariable String id) {
        return userService.getById(id);
    }

    /**
     * Endpoint que devuelve el url de la imagen de perfil de un usuario, esto es para mantener actualizado en la app
     * @param id Id de usuario
     * @return URI de imagen de perfil de usuario
     */
    @GetMapping("/profile-image/{id}")
    @PreAuthorize("permitAll()")
    public ContentDto getProfileImage(@PathVariable String id) {
        return userService.getUserImage(id);
    }

    /**
     * Endpoint que elimina un usuario por su id
     *
     * @param id Id de usuario
     * @return GenericResponse con detalles
     */
    @DeleteMapping("/{id}")
    public GenericResponseDto deleteByEmail(@PathVariable String id) {
        return userService.deleteById(id);
    }

    @GetMapping("/disabled")
    public List<UserDto> getAllDisabled() {
        return userService.getAllDisabled();
    }

    @PutMapping("/{id}/enable")
    public GenericResponseDto enableUser(@PathVariable String id) {
        return userService.enableUser(id, true);
    }

    @PutMapping("/{id}/disable")
    public GenericResponseDto disableUser(@PathVariable String id) {
        return userService.enableUser(id, false);
    }

}
