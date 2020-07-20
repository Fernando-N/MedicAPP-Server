package cl.medicapp.service.util;

import cl.medicapp.service.dto.GenericResponseDto;
import cl.medicapp.service.exception.GenericException;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

/**
 * Clase utilitaria para respuestas genericas
 */
public class GenericResponseUtil {

    /**
     * Construye una respuesta generica a partir de un mensaje y detalles
     * @param message Mensaje (requerido)
     * @param details Detalles... (no requerido)
     * @return GenericResponseDto
     */
    public static GenericResponseDto buildGenericResponse(String message, String... details) {
        return GenericResponseDto.builder()
                .message(message)
                .details(Arrays.asList(details))
                .build();
    }

    /**
     * Construye una excepcion generica a partir de un httpStatus, mensaje y detalles
     * @param httpStatus HttpStatus (requerido)
     * @param message Mensaje (requerido)
     * @param details Detalles... (no requerido)
     * @return
     */
    public static GenericException buildGenericException(HttpStatus httpStatus, String message, String... details) {
        return new GenericException(httpStatus, message, Arrays.asList(details));
    }

    /**
     * Obtiene una excepcion generica 500 - Internal Server Error
     * @return Excepcion generica http 500
     */
    public static GenericException getGenericException() {
        return new GenericException(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), null);
    }

    /**
     * Constructor privado para no permitir crear instancias de la clase
     */
    private GenericResponseUtil() {

    }

}
