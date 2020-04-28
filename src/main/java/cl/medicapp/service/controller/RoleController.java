package cl.medicapp.service.controller;

import cl.medicapp.service.dto.GenericResponseDto;
import cl.medicapp.service.dto.RoleDto;
import cl.medicapp.service.services.role.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
//@PreAuthorize("hasRole('ADMIN')")
public class RoleController {

    private final RoleService roleService;

    @PostMapping("")
    public RoleDto create(@Valid @RequestBody RoleDto roleDto) {
        return roleService.save(roleDto);
    }

    @GetMapping("")
    public List<RoleDto> getAll() {
        return roleService.getAll();
    }

    @GetMapping("/{name}")
    @ResponseStatus(HttpStatus.CREATED)
    public RoleDto getByName(@RequestParam String name) {
        return roleService.getByName(name);
    }

    @DeleteMapping("/{name}")
    public GenericResponseDto deleteByName(String name) {
        return roleService.deleteRoleByName(name);
    }

}
