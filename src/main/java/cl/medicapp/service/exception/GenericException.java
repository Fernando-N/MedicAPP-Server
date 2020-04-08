package cl.medicapp.service.exception;

import lombok.Getter;

import java.util.List;

/**
 * Excepción personalizada para lanzar cuando haya un error de negocio
 */
@Getter
public class GenericException extends RuntimeException {

    private int statusCode = 0;
    private List<String> details;

    public GenericException(String message, List<String> details) {
        super(message);
        this.details = details;
    }

    public GenericException(int statusCode, String message, List<String> details) {
        super(message);
        this.statusCode = statusCode;
        this.details = details;
    }
}
