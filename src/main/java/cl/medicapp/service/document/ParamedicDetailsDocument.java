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
 * Documento de detalles de paramedico
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "paramedicsDetails")
public class ParamedicDetailsDocument implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Identificador
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * Imagen de titulo técnico/universitario
     */
    private String titleImageURI;

    /**
     * Año de egreso
     */
    private int graduationYear;

    /**
     * Certificado de inscripción en registro nacional de prestadores individuales
     */
    private String certificateNationalHealthURI;

    /**
     * Imagen de carnet
     */
    private String carnetImageURI;

}
