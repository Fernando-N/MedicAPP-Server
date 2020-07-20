package cl.medicapp.service.controller;

import cl.medicapp.service.dto.GenericResponseDto;
import cl.medicapp.service.dto.ReportDto;
import cl.medicapp.service.services.report.ReportService;
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

//TODO Terminar esta funcionalidad...

/**
 * Controlador de reportes
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/report")
@PreAuthorize("hasRole('ADMIN')")
public class ReportController {

    /**
     * Bean de servicio de reportes
     */
    private final ReportService reportService;

    /**
     * Endpoint para crear un reporte
     * @param reportDto Objeto con datos de reporte
     * @return Objeto con datos de reporte creado
     */
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("isAuthenticated()")
    public ReportDto create(@Valid @RequestBody ReportDto reportDto) {
        return reportService.save(reportDto);
    }

    /**
     * Endpoint que obtiene todos los reportes
     * @return Lista de reportes
     */
    @GetMapping("")
    public List<ReportDto> getAll() {
        return reportService.getAll();
    }

    /**
     * Endpoint que obtiene los reportes creados por un usuario
     * @param idFrom Id usuario a buscar
     * @return Lista de reportes encontrados
     */
    @GetMapping("/from/{idFrom}")
    public List<ReportDto> getByFrom(@PathVariable String idFrom) {
        return reportService.getByFromUserId(idFrom);
    }

    /**
     * Endpoint que obtiene los reportes creados para un usuario
     * @param idTo Id usuario a buscar
     * @return Lista de reportes encontrados
     */
    @GetMapping("/to/{idTo}")
    public List<ReportDto> getByTo(@PathVariable String idTo) {
        return reportService.getByToUserId(idTo);
    }

    /**
     * Endpoint que marca como resuelto un reporte
     */
    @PutMapping("/{id}/resolve")
    public GenericResponseDto resolve(@PathVariable String id) {
        return reportService.resolveReportId(id);
    }

    /**
     * Endpoint que elimina un reporte
     * @param id Id de reporte a eliminar
     * @return Resultado de eliminación
     */
    @DeleteMapping("/{id}")
    public GenericResponseDto deleteById(@PathVariable String id) {
        return reportService.deleteById(id);
    }

}
