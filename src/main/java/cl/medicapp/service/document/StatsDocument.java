package cl.medicapp.service.document;

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
 * Documento Stats usuarios
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "stats")
public class StatsDocument implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Identificador
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * DBRef para referenciar document User
     */
    @DBRef
    private UserDocument user;

    /**
     * Cantidad de valoreaciones/feedbacks
     */
    private int valuations;

    /**
     * Cantidad de Contactos
     */
    private int contacts;

    /**
     * Rating
     */
    private int rating;

}
