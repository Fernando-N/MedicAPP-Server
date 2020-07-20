package cl.medicapp.service.controller;

import cl.medicapp.service.dto.GenericResponseDto;
import cl.medicapp.service.dto.NationalityDto;
import cl.medicapp.service.services.nationality.NationalityService;
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
 * Controlador de nacionalidades
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/nationality")
@PreAuthorize("hasRole('ADMIN')")
public class NationalityController {

    /**
     * Bean de servicio de nacionalidades
     */
    private final NationalityService nationalityService;

    /**
     * Endpoint para crear nacionalidades
     * @param request Objeto con datos de nacionalidad
     * @return Objeto de nacionalidad creado
     */
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public NationalityDto create(@Valid @RequestBody NationalityDto request) {
        return nationalityService.save(request);
    }

    /**
     * Endpoint que retorna lista de nacionalidades
     * @return Lista de nacionalidades
     */
    @GetMapping("")
    @PreAuthorize("permitAll()")
    public List<NationalityDto> getAll() {
        return nationalityService.getAll();
    }

    /**
     * Endpoint que obtiene una nacionalidad por su nombre
     * @param name Nombre a buscar
     * @return Nacionalidad
     */
    @GetMapping("/{name}")
    public NationalityDto getByName(@PathVariable String name) {
        return nationalityService.getByName(name);
    }

    /**
     * Endpoint que modifica una nacionalidad
     * @param name Nombre de nacionalidad a modificar
     * @param newRoleName Objeto de nacionalidad con cambios
     * @return Objeto de nacionalidad con cambios realizados
     */
    @PutMapping("/{name}")
    public NationalityDto update(@PathVariable String name, @Valid @RequestBody NationalityDto newRoleName) {
        return nationalityService.update(name, newRoleName);
    }

    /**
     * Endpoint que eliminar una nacionalidad por su nombre
     * @param name Nombre nacionalidad a eliminar
     * @return Resultado de eliminación
     */
    @DeleteMapping("/{name}")
    public GenericResponseDto deleteByName(@PathVariable String name) {
        return nationalityService.deleteByName(name);
    }

}

