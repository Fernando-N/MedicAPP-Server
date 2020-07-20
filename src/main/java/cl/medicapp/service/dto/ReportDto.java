package cl.medicapp.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Dto para mensajes de chat
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReportDto {

    /**
     * Identificador
     */
    private String id;

    private String toUserId;

    /**
     * De
     * DBRef para referenciar document User
     */
    private UserDto fromUser;

    /**
     * Para
     */
    private UserDto toUser;

    /**
     * Mensajes
     */
    private String message;

    /**
     * Fecha
     */
    private String date;

    /**
     * Fue leido
     */
    private boolean alreadyRead;

}
