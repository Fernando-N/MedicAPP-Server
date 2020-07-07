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
 * Controlador de roles
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/region")
public class RegionController {

    private final RegionService regionService;
    private final CommuneService communeService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public RegionDto create(@Valid @RequestBody RegionDto request) {
        return regionService.save(request);
    }

    @GetMapping("")
    public List<RegionDto> getAll() {
        return regionService.getAll();
    }

    @GetMapping("/{name}")
    public RegionDto getByName(@PathVariable String name) {
        return regionService.getByName(name);
    }

    @GetMapping("/{id}/communes")
    public List<CommuneDto> getCommunesByRegionId(@PathVariable String id) {
        return communeService.getCommunesByRegionId(id);
    }

    @PutMapping("/{name}")
    @PreAuthorize("hasRole('ADMIN')")
    public RegionDto update(@PathVariable String name, @Valid @RequestBody RegionDto newRoleName) {
        return regionService.update(name, newRoleName);
    }

    @DeleteMapping("/{name}")
    @PreAuthorize("hasRole('ADMIN')")
    public GenericResponseDto deleteByName(@PathVariable String name) {
        return regionService.deleteByName(name);
    }

}

