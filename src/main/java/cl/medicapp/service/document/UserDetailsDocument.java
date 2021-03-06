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
import java.util.Date;

/**
 * Documento detalles de usuario
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "usersDetails")
public class UserDetailsDocument implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Identificador
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * Rut
     */
    private String rut;

    /**
     * Nombres
     */
    private String firstName;

    /**
     * Apellido
     */
    private String lastName;

    /**
     * Fecha de nacimiento
     */
    private Date birthDay;

    /**
     * Comuna
     */
    @DBRef
    private CommuneDocument commune;

    /**
     * Sobre mi
     */
    private String aboutMe;

    /**
     * Nacionalidad
     */
    @DBRef
    private NationalityDocument nationality;

    /**
     * Flag para mostrar dirección en perfil
     */
    private boolean showAddress;

    /**
     * Dirección
     */
    private String address;

    /**
     * Imagen de perfil
     */
    private String profileImageURI;

}
