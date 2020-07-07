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
 * Controlador de roles
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/nationality")
public class NationalityController {

    private final NationalityService nationalityService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public NationalityDto create(@Valid @RequestBody NationalityDto request) {
        return nationalityService.save(request);
    }

    @GetMapping("")
    public List<NationalityDto> getAll() {
        return nationalityService.getAll();
    }

    @GetMapping("/{name}")
    @PreAuthorize("hasRole('ADMIN')")
    public NationalityDto getByName(@PathVariable String name) {
        return nationalityService.getByName(name);
    }

    @PutMapping("/{name}")
    @PreAuthorize("hasRole('ADMIN')")
    public NationalityDto update(@PathVariable String name, @Valid @RequestBody NationalityDto newRoleName) {
        return nationalityService.update(name, newRoleName);
    }

    @DeleteMapping("/{name}")
    @PreAuthorize("hasRole('ADMIN')")
    public GenericResponseDto deleteByName(@PathVariable String name) {
        return nationalityService.deleteByName(name);
    }

}

