package cl.medicapp.service.controller;

import cl.medicapp.service.dto.CommuneDto;
import cl.medicapp.service.dto.GenericResponseDto;
import cl.medicapp.service.services.communes.CommuneService;
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
 * Controlador de comunas
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/communes")
@PreAuthorize("hasRole('ADMIN')")
public class CommunesController {

    /**
     * Bean Servicio de comunas
     */
    private final CommuneService communeService;

    /**
     * Endpoint para crear comunas
     * @param request Request que contiene datos de comuna
     * @return Comuna creada
     */
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public CommuneDto create(@Valid @RequestBody CommuneDto request) {
        return communeService.save(request);
    }

    /**
     * Endpoint que retorna todas las comunas en bd
     * @return Lista de comunas
     */
    @GetMapping("")
    @PreAuthorize("isAuthenticated()")
    public List<CommuneDto> getAll() {
        return communeService.getAll();
    }

    /**
     * Endpoint que retorna comuna por su nombre
     * @param name Nombre comuna a buscar
     * @return Comuna encontrada
     */
    @GetMapping("/{name}")
    @PreAuthorize("isAuthenticated()")
    public CommuneDto getByName(@PathVariable String name) {
        return communeService.getByName(name);
    }

    /**
     * Endpoint para modificar comuna
     * @param name Nombre de comuna
     * @param newCommuneDto Objeto de comuna con cambios
     * @return Objeto de comuna con cambios
     */
    @PutMapping("/{name}")
    public CommuneDto update(@PathVariable String name, @Valid @RequestBody CommuneDto newCommuneDto) {
        return communeService.update(name, newCommuneDto);
    }

    /**
     * Endpoint que elimina una comuna
     * @param name Nombre de comuna
     * @return Resultado de eliminación
     */
    @DeleteMapping("/{name}")
    public GenericResponseDto deleteByName(@PathVariable String name) {
        return communeService.deleteByName(name);
    }

}

