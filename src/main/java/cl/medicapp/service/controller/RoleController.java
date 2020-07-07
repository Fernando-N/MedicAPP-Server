package cl.medicapp.service.controller;

import cl.medicapp.service.dto.GenericResponseDto;
import cl.medicapp.service.dto.RoleDto;
import cl.medicapp.service.services.role.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * Controlador de roles
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/role")
@PreAuthorize("hasRole('ADMIN')")
public class RoleController {

    private final RoleService roleService;

    /**
     * Endpoint que crea un rol
     *
     * @param request Request con nombre del rol
     * @return Rol creado
     */
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public RoleDto create(@Valid @RequestBody RoleDto request) {
        return roleService.save(request);
    }

    /**
     * Endpoint que obtiene todos los roles
     *
     * @return Listado de roles
     */
    @GetMapping("")
    public List<RoleDto> getAll() {
        return roleService.getAll();
    }

    /**
     * Endpoint que trae un rol
     *
     * @param name Rol
     * @return Rol encontrado
     */
    @GetMapping("/{name}")
    public RoleDto getByName(@PathVariable String name) {
        return roleService.getByName(name);
    }

    @PutMapping("/{name}")
    public RoleDto update(@PathVariable String name, @Valid @RequestBody RoleDto newRoleName) {
        return roleService.update(name, newRoleName);
    }

    /**
     * Endpoint que elimina un rol por su nombre
     *
     * @param name Nombre del rol
     * @return GenericResponse con detalles
     */
    @DeleteMapping("/{name}")
    public GenericResponseDto deleteByName(@PathVariable String name) {
        return roleService.deleteByName(name);
    }

}

