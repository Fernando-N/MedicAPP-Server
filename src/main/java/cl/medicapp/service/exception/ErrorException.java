package cl.medicapp.service.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class ErrorException extends RuntimeException {

    private int statusCode = 0;
    private List<String> details;

    public ErrorException(String message, List<String> details) {
        super(message);
        this.details = details;
    }

    public ErrorException(int statusCode, String message, List<String> details) {
        super(message);
        this.statusCode = statusCode;
        this.details = details;
    }
}
