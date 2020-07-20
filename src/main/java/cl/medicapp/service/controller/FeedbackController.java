package cl.medicapp.service.controller;

import cl.medicapp.service.dto.FeedbackDto;
import cl.medicapp.service.dto.GenericResponseDto;
import cl.medicapp.service.services.feedback.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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

/**
 * Controlador de feedbacks de paramedicos
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/feedback")
@PreAuthorize("hasRole('ADMIN')")
public class FeedbackController {

    /**
     * Bean de servicio de feedbacks
     */
    private final FeedbackService feedbackService;

    /**
     * Endpoint que crea un feedback
     * @param feedback Objeto con feedback a crear
     * @return Objeto con feedback creado
     */
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("isAuthenticated()")
    public FeedbackDto create(@Valid @RequestBody FeedbackDto feedback) {
        return feedbackService.save(feedback);
    }

    /**
     * Endpoint que obtiene todos los feedbacks
     * @return Lista de feedbacks
     */
    @GetMapping("")
    public List<FeedbackDto> getAll() {
        return feedbackService.getAll();
    }

    /**
     * Endpoint que obtiene feedbacks creados por un usuario
     * @param idFrom Id de usuario a buscar
     * @return Lista de feedbacks creados por usuario
     */
    @GetMapping("/from/{idFrom}")
    public List<FeedbackDto> getByFrom(@PathVariable String idFrom) {
        return feedbackService.getAllByFromUserId(idFrom);
    }

    /**
     * Endpoint que obtiene feedbacks dirigidos a un usuario
     * @param idTo Id usuario a buscar
     * @return Lista de feedbacks
     */
    @GetMapping("/to/{idTo}")
    public List<FeedbackDto> getByTo(@PathVariable String idTo) {
        return feedbackService.getAllByToUserId(idTo);
    }

    /**
     * Endpoint que elimina un feedback
     * @param id Id de feedback a eliminar
     * @return Resultado de eliminación
     */
    @DeleteMapping("/{id}")
    public GenericResponseDto deleteById(@PathVariable String id) {
        return feedbackService.deleteById(id);
    }

}
