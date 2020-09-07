package cl.medicapp.service.controller;

import cl.medicapp.service.dto.CommuneDto;
import cl.medicapp.service.dto.GenericResponseDto;
import cl.medicapp.service.dto.RegionDto;
import cl.medicapp.service.services.communes.CommuneService;
import cl.medicapp.service.services.region.RegionService;
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
 * Controlador de regiones
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/region")
@PreAuthorize("hasRole('ADMIN')")
public class RegionController {

    /**
     * Bean de servicio de regiones
     */
    private final RegionService regionService;

    /**
     * Bean de servicio de comunas
     */
    private final CommuneService communeService;

    /**
     * Endpoint para crear región
     * @param request Objeto de región a crear
     * @return Objeto de región creada
     */
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public RegionDto create(@Valid @RequestBody RegionDto request) {
        return regionService.save(request);
    }

    /**
     * Endpoint para obtener todas las regiones
     * @return Lista de regiones
     */
    @GetMapping("")
    @PreAuthorize("permitAll()")
    public List<RegionDto> getAll() {
        return regionService.getAll();
    }

    /**
     * Endpoint para obtener una region por su nombre
     * @param name Nombre de region a buscar
     * @return Region encontrada
     */
    @GetMapping("/{name}")
    public RegionDto getByName(@PathVariable String name) {
        return regionService.getByName(name);
    }

    /**
     * Endpoint que obtiene comunas de una region
     * @param id Id región a buscar sus comunas
     * @return Lista de comunas de region encontrada
     */
    @GetMapping("/{id}/communes")
    @PreAuthorize("permitAll()")
    public List<CommuneDto> getCommunesByRegionId(@PathVariable String id) {
        return communeService.getCommunesByRegionId(id);
    }

    /**
     * Endpoint para editar una region
     * @param name Nombre de region a actualizar
     * @param newRegion Objeto de región con cambios
     * @return Objeto de región con cambios realizados
     */
    @PutMapping("/{name}")
    public RegionDto update(@PathVariable String name, @Valid @RequestBody RegionDto newRegion) {
        return regionService.update(name, newRegion);
    }

    /**
     * Endpoint que elimina una región
     * @param name Nombre de región a eliminar
     * @return Resultado de eliminación
     */
    @DeleteMapping("/{name}")
    public GenericResponseDto deleteByName(@PathVariable String name) {
        return regionService.deleteByName(name);
    }

}

