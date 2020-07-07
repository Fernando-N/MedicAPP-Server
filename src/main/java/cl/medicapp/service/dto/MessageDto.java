package cl.medicapp.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Dto para mensajes de chat
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageDto {

    /**
     * Id
     */
    private String id;

    /**
     * Fecha
     */
    private Date date;

    /**
     * De
     */
    private String from;

    /**
     * Para
     */
    private String to;

    /**
     * Mensaje
     */
    private String message;

    /**
     * Fue leido
     */
    private boolean alreadyRead = false;

}
