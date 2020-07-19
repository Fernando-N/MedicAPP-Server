package cl.medicapp.service.controller;

import cl.medicapp.service.dto.GenericResponseDto;
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
@PreAuthorize("isAuthenticated()")
public class ParamedicController {

    private final UserService userService;

    /**
     * Endpoint que obtiene todos los paramedicos
     *
     * @return Lista de usuarios
     */
    @GetMapping("")
    public List<UserDto> getAll() {
        return userService.getAllByRole("PARAMEDIC");
    }

    /**
     * Endpoint que obtiene un usuario por su correo
     *
     * @param email Email
     * @return Usuario encontrado
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{email}")
    public UserDto getByEmail(@PathVariable String email) {
        return userService.getByEmail(email);
    }

    /**
     * Endpoint que obtiene usuarios por su nombre y apellido
     *
     * @param firstName Nombre
     * @param lastName  Apellido
     * @return Lista de usuarios encontrados
     */
    @GetMapping("/{firstName}-{lastName}")
    public List<UserDto> getByName(@PathVariable String firstName, @PathVariable String lastName) {
        return userService.getByName(firstName, lastName);
    }

    /**
     * Endpoint que elimina un usuario por su correo
     *
     * @param email Email
     * @return GenericResponse con detalles
     */
    @DeleteMapping("/{email}")
    public GenericResponseDto deleteByEmail(@PathVariable String email) {
        return userService.deleteById(email);
    }

}
