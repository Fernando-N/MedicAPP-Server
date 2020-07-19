package cl.medicapp.service.controller;

import cl.medicapp.service.dto.CommuneDto;
import cl.medicapp.service.dto.GenericResponseDto;
import cl.medicapp.service.services.communes.CommuneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/communes")
public class CommunesController {

    private final CommuneService communeService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public CommuneDto create(@Valid @RequestBody CommuneDto request) {
        return communeService.save(request);
    }

    @GetMapping("")
    public List<CommuneDto> getAll() {
        return communeService.getAll();
    }

    @GetMapping("/{name}")
    public CommuneDto getByName(@PathVariable String name) {
        return communeService.getByName(name);
    }

    @PutMapping("/{name}")
    public CommuneDto update(@PathVariable String name, @Valid @RequestBody CommuneDto newCommuneDto) {
        return communeService.update(name, newCommuneDto);
    }

    @DeleteMapping("/{name}")
    public GenericResponseDto deleteByName(@PathVariable String name) {
        return communeService.deleteByName(name);
    }

}

