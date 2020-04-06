package cl.medicapp.service.controller;

import cl.medicapp.service.dto.ErrorDto;
import cl.medicapp.service.exception.ErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Clase controladora de excepciones
 */
@ControllerAdvice
@RestController
@Slf4j
public class ExceptionsController extends ResponseEntityExceptionHandler {

    /**
     * Metodo que captura errores de badRequest validados con @Valid
     * @param exception Excepcion controlada
     * @param headers headers http
     * @param status httpStatus
     * @param request Request que causo excepcion
     * @return ErrorResponse con detalles del error ocurrido
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logError(exception);
        List<String> details = new ArrayList<>();
        exception.getBindingResult().getAllErrors().forEach(ex -> details.add(ex.getDefaultMessage()));
        ErrorDto error = ErrorDto.builder().message(HttpStatus.BAD_REQUEST.getReasonPhrase()).details(details).build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Object> handleUsernameNotFoundException(Exception e, WebRequest request) {
        logError(e);
        List<String> details = Collections.singletonList(String.format("%s", e.getLocalizedMessage()));
        ErrorDto error = ErrorDto.builder().message(HttpStatus.NOT_FOUND.getReasonPhrase()).details(details).build();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ErrorException.class)
    public ResponseEntity<Object> handleErrorException(ErrorException e) {
        logError(e);
        ErrorDto error = ErrorDto.builder().message(e.getMessage()).details(e.getDetails()).build();
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException e) {
        logError(e);
        ErrorDto error = ErrorDto.builder().message(e.getMessage()).details(Collections.singletonList("Access to this resource is denied")).build();
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Metodo que captura cualquier excepcion no controlada
     * @param ex Excepcion controlada
     * @return ErrorResponse con mensaje de error interno sin detalle
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception ex) {
        logError(ex);
        ErrorDto error = ErrorDto.builder().message(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()).build();
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Metodo generico para trazear las excepciones capturas y imprimir el stacktrace en consola
     * @param e Excepcion ocurrida
     */
    private void logError(Exception e) {
        log.error("Excepcion capturada [{}], Causa: [{}]", e.getClass(), e.getLocalizedMessage());
        if(log.isDebugEnabled()) {
            e.printStackTrace();
        }
    }

}
