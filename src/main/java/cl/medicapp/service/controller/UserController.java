package cl.medicapp.service.controller;

import cl.medicapp.service.constants.Constants;
import cl.medicapp.service.dto.ContentDto;
import cl.medicapp.service.dto.GenericResponseDto;
import cl.medicapp.service.dto.UserDto;
import cl.medicapp.service.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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
     * Endpoint que obtiene lista de los usuarios
     * @return Lista de usuarios
     */
    @GetMapping("")
    public List<UserDto> getAll() {
        return userService.getAll();
    }

    /**
     * Endpoint que obtiene el perfil del usuario logeado
     * @return Datos de usuario logeado
     */
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public UserDto getOwnProfile() {
        return userService.getOwnProfile();
    }

    /**
     * Endpoint que obtiene usuario por su id
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
     * @param id Id de usuario
     * @return GenericResponse con detalles
     */
    @DeleteMapping("/{id}")
    public GenericResponseDto deleteByEmail(@PathVariable String id) {
        return userService.deleteById(id);
    }

    /**
     * Endpoint para obtener lista de usuarios deshabilitados
     * @return Lista de usuarios deshabilitados
     */
    @GetMapping("/disabled")
    public List<UserDto> getAllDisabled() {
        return userService.getAllDisabled();
    }

    /**
     * Endpoint para editar un usuario
     * @param id id de usuario a editar
     * @param userDto Objeto de usuario con cambios
     * @return Objeto de usuario actualizado
     */
    @PutMapping("/{id}")
    public UserDto editUser(@PathVariable String id, @RequestBody @Valid UserDto userDto) {
        return userService.edit(id, userDto);
    }

    /**
     * Endpoint que habilita un usuario
     * @param id Id de usuario a activar
     * @return Resultado de operación
     */
    @PutMapping("/{id}/enable")
    public GenericResponseDto enableUser(@PathVariable String id) {
        return userService.enableUser(id, true);
    }

    /**
     * Endpoint que deshabilita un usuario
     * @param id Id de usuario a desactivar
     * @return Resultado de operación
     */
    @PutMapping("/{id}/disable")
    public GenericResponseDto disableUser(@PathVariable String id) {
        return userService.enableUser(id, false);
    }

}
