package cl.medicapp.service.controller;

import cl.medicapp.service.dto.FeedbackDto;
import cl.medicapp.service.dto.GenericResponseDto;
import cl.medicapp.service.services.feedback.FeedbackService;
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

/**
 * Controlador de feedbacks de paramedicos
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/feedback")
//@PreAuthorize("hasRole('ADMIN')")
public class FeedbackController {

    private final FeedbackService feedbackService;

    /**
     * Endpoint que crea un usuario
     *
     * @param userDto Usuario a crear
     * @return Usuario creado
     */
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public FeedbackDto create(@Valid @RequestBody FeedbackDto reportDto) {
        return feedbackService.save(reportDto);
    }

    /**
     * Endpoint que obtiene todos los usuarios
     *
     * @return Lista de usuarios
     */
    @GetMapping("")
    public List<FeedbackDto> getAll() {
        return feedbackService.getAll();
    }

    /**
     * Endpoint que obtiene un usuario por su correo
     *
     * @param email Email
     * @return Usuario encontrado
     */
    @GetMapping("/from/{idFrom}")
    public List<FeedbackDto> getByFrom(@PathVariable String idFrom) {
        return feedbackService.getAllByFromUserId(idFrom);
    }

    @GetMapping("/to/{idTo}")
    public List<FeedbackDto> getByTo(@PathVariable String idTo) {
        return feedbackService.getAllByToUserId(idTo);
    }

    /**
     * Endpoint que elimina un usuario por su correo
     *
     * @param email Email
     * @return GenericResponse con detalles
     */
    @DeleteMapping("/{id}")
    public GenericResponseDto deleteById(@PathVariable String id) {
        return feedbackService.deleteById(id);
    }

}
