package cl.medicapp.service.controller;

import cl.medicapp.service.dto.GenericResponseDto;
import cl.medicapp.service.dto.ReportDto;
import cl.medicapp.service.services.report.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

//TODO Terminar esta funcionalidad...

/**
 * Controlador de autenticación
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/report")
//@PreAuthorize("hasRole('ADMIN')")
public class ReportController {

    private final ReportService reportService;

    /**
     * Endpoint que crea un usuario
     *
     * @param userDto Usuario a crear
     * @return Usuario creado
     */
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ReportDto create(@RequestBody ReportDto reportDto) {
        return reportService.save(reportDto);
    }

    /**
     * Endpoint que obtiene todos los usuarios
     *
     * @return Lista de usuarios
     */
    @GetMapping("")
    public List<ReportDto> getAll() {
        return reportService.getAll();
    }

    /**
     * Endpoint que obtiene un usuario por su correo
     *
     * @param email Email
     * @return Usuario encontrado
     */
    @GetMapping("/from/{idFrom}")
    public List<ReportDto> getByFrom(@PathVariable String idFrom) {
        return reportService.getByFromUserId(idFrom);
    }

    @GetMapping("/to/{idTo}")
    public List<ReportDto> getByTo(@PathVariable String idTo) {
        return reportService.getByToUserId(idTo);
    }

    /**
     * Endpoint que elimina un usuario por su correo
     *
     * @param email Email
     * @return GenericResponse con detalles
     */
    @DeleteMapping("/{id}")
    public GenericResponseDto deleteById(@PathVariable String id) {
        return reportService.deleteById(id);
    }

}
