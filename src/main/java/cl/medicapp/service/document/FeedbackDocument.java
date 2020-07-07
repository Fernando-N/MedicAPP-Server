package cl.medicapp.service.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * Document Message Chat
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "feedbacks")
public class FeedbackDocument implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Identificador
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * De
     * DBRef para referenciar document User
     */
    @DBRef
    private UserDocument from;

    /**
     * Para
     * DBRef para referenciar documento User
     */
    @DBRef
    private UserDocument to;

    /**
     * Comentario
     */
    private String comment;

    /**
     * Fecha
     */
    @CreatedDate
    private Date date;

    /**
     * Calificación
     */
    private int rate;

}
