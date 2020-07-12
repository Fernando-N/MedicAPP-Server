package cl.medicapp.service.dto;

import cl.medicapp.service.document.UserDocument;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

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

    /**
     * De
     * DBRef para referenciar document User
     */
    private UserDto from;

    /**
     * Para
     */
    private UserDto to;

    /**
     * Mensajes
     */
    private String message;

    /**
     * Fecha
     */
    private Date date;

    /**
     * Fue leido
     */
    private boolean alreadyRead;

}
