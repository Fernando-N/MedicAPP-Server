package cl.medicapp.service.dto;

import cl.medicapp.service.document.UserDocument;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Objeto de transferencia para feedback
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FeedbackDto {

    /**
     * Identificador
     */
    private String id;

    /**
     * Autor de feedback
     */
    private UserDto from;

    /**
     * Para
     */
    private UserDto to;

    private String toUserId;

    /**
     * Comentario
     */
    private String comment;

    /**
     * Flag si es anonimo
     */
    private boolean anon;

    /**
     * Fecha
     */
    private String date;

    /**
     * Calificación
     */
    private int rate;

}
