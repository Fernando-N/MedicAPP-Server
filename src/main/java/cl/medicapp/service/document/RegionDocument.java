package cl.medicapp.service.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Documento de region
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "regions")
public class RegionDocument implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Identificador
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * Nombre
     */
    private String name;

    /**
     * Ordinal
     */
    private String ordinal;

}
