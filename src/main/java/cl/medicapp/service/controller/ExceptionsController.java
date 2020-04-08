package cl.medicapp.service.controller;

import cl.medicapp.service.constants.Constants;
import cl.medicapp.service.dto.GenericResponseDto;
import cl.medicapp.service.exception.GenericException;
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
     * Manejador de excepciones de tipo MethodArgumentNotValidException que es lanzado cuando algún request contenga datos erroneos
     *
     * @param exception Excepcion capturada
     * @param headers   headers http request
     * @param status    httpStatus
     * @param request   Request que causo la excepcion
     * @return ErrorResponse con detalles del error ocurrido
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logError(exception);
        List<String> details = new ArrayList<>();
        exception.getBindingResult().getAllErrors().forEach(ex -> details.add(ex.getDefaultMessage()));
        GenericResponseDto error = GenericResponseDto.builder().message(HttpStatus.BAD_REQUEST.getReasonPhrase()).details(details).build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Manejador de excepciones de tipo UsernameNotFountException que es lanzada cuando un usuario no existe
     *
     * @param exception Excepcion capturada
     * @return ErrorResponse con detalles del error ocurrido
     */
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Object> handleUsernameNotFoundException(UsernameNotFoundException exception) {
        logError(exception);
        List<String> details = Collections.singletonList(String.format("%s", exception.getLocalizedMessage()));
        GenericResponseDto error = GenericResponseDto.builder().message(HttpStatus.NOT_FOUND.getReasonPhrase()).details(details).build();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Manejador de excepciones de tipo ErrorException que son excepciones propias personalizadas
     *
     * @param exception Excepcion capturada
     * @return ErrorResponse con detalles del error ocurrido
     */
    @ExceptionHandler(GenericException.class)
    public ResponseEntity<Object> handleErrorException(GenericException exception) {
        logError(exception);
        GenericResponseDto error = GenericResponseDto.builder().message(exception.getMessage()).details(exception.getDetails()).build();
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Manejador de excepciones de tipo AccessDeniedException que son lanzadas cuando un usuario no tiene acceso a un recurso
     *
     * @param exception Excepcion capturada
     * @return ErrorResponse con detalles del error ocurrido
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException exception) {
        logError(exception);
        GenericResponseDto error = GenericResponseDto.builder().message(exception.getMessage()).details(Collections.singletonList(Constants.UNAUTHORIZED_RESOURCE)).build();
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Manejador de excepciones de cualquier tipo, es decir cualquier excepcion no controlada
     *
     * @param exception Excepcion capturada
     * @return ErrorResponse con detalles del error ocurrido
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception exception) {
        logError(exception);
        GenericResponseDto error = GenericResponseDto.builder().message(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()).build();
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Metodo generico para trazear excepciones capturadas
     *
     * @param exception Excepcion ocurrida
     */
    private void logError(Exception exception) {
        log.error("[ExceptionController] Excepcion capturada", exception);
    }

}
