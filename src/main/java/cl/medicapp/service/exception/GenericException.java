package cl.medicapp.service.exception;

import lombok.Getter;

import java.util.List;

/**
 * Excepción personalizada para lanzar cuando haya un error de negocio
 */
@Getter
public class GenericException extends RuntimeException {

    private final int statusCode;
    private final List<String> details;

    public GenericException(int statusCode, String message, List<String> details) {
        super(message);
        this.statusCode = statusCode;
        this.details = details;
    }

    public GenericException(String message, List<String> details) {
        super(message);
        this.statusCode = 500;
        this.details = details;
    }

}
