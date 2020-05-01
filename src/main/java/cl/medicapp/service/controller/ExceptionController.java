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
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Clase controladora de excepciones
 */
@ControllerAdvice
@RestController
@Slf4j
public class ExceptionController extends ResponseEntityExceptionHandler {

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
     * Manejador de excepciones de tipo MissingServletRequestParameterException que son lanzadas cuando falta un requestParam
     *
     * @param exception Excepcion capturada
     * @param headers   headers http request
     * @param status    httpStatus
     * @param request   Request que causo la excepcion
     * @return ErrorResponse con detalles del error ocurrido
     */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String detail = String.format(Constants.X_PARAMETER_IS_MISSING, exception.getParameterName());
        GenericResponseDto error = GenericResponseDto.builder().message(HttpStatus.BAD_REQUEST.getReasonPhrase()).details(Collections.singletonList(detail)).build();
        return new ResponseEntity<>(error, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Manejador de excepciones de tipo HttpRequestMethodNotSupportedException que son lanzadas cuando el request method es incorrecto
     *
     * @param exception Excepcion capturada
     * @param headers   headers http request
     * @param status    httpStatus
     * @param request   Request que causo la excepcion
     * @return ErrorResponse con detalles del error ocurrido
     */
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
        StringBuilder detail = new StringBuilder();
        detail.append(exception.getMethod()).append(Constants.X_METHOD_IS_NOT_SUPPORT);
        Objects.requireNonNull(exception.getSupportedHttpMethods()).forEach(httpMethod -> detail.append(httpMethod).append(" "));
        GenericResponseDto error = GenericResponseDto.builder().message(HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase()).details(Collections.singletonList(detail.toString().trim())).build();
        return new ResponseEntity<>(error, new HttpHeaders(), HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * Manejador de excepciones de tipo HttpMediaTypeNotSupportedException que son lanzadas cuando el request tiene un archivo no soportado
     *
     * @param exception Excepcion capturada
     * @param headers   headers http request
     * @param status    httpStatus
     * @param request   Request que causo la excepcion
     * @return ErrorResponse con detalles del error ocurrido
     */
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
        StringBuilder detail = new StringBuilder();
        detail.append(exception.getContentType()).append(Constants.X_MEDIA_TYPE_IS_NO_SUPPORT);
        exception.getSupportedMediaTypes().forEach(mediaType -> detail.append(mediaType).append(", "));
        GenericResponseDto error = GenericResponseDto.builder().message(HttpStatus.UNSUPPORTED_MEDIA_TYPE.getReasonPhrase()).details(Collections.singletonList(detail.toString())).build();
        return new ResponseEntity<>(error, new HttpHeaders(), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
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
     * Manejador de excepciones de tipo ConstraintViolationException que son lanzadas por alguna violación de constraint de base de datos
     *
     * @param exception Excepcion capturada
     * @param request   Request que causo la excepcion
     * @return ErrorResponse con detalles del error ocurrido
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException exception, WebRequest request) {
        List<String> details = new ArrayList<>();
        exception.getConstraintViolations().forEach(constraintViolation ->
                details.add(constraintViolation.getRootBeanClass().getName() + " " + constraintViolation.getPropertyPath() + ": " + constraintViolation.getMessage())
        );

        GenericResponseDto error = GenericResponseDto.builder().message(HttpStatus.BAD_REQUEST.getReasonPhrase()).details(details).build();
        return new ResponseEntity<>(error, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Manejador de excepciones de cualquier tipo, es decir cualquier excepcion no controlada
     *
     * @param exception Excepcion capturada
     * @param headers   headers http request
     * @param status    httpStatus
     * @param request   Request que causo la excepcion
     * @return ErrorResponse con detalles del error ocurrido
     */
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logError(exception);
        GenericResponseDto error = GenericResponseDto.builder().message(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()).build();
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
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
