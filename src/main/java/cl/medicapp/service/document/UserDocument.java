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
import java.util.List;

/**
 * Document User
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class UserDocument implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Identificador
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * Email
     */
    private String email;

    /**
     * Contraseña
     */
    private String password;

    /**
     * Roles
     */
    @DBRef
    private List<RoleDocument> roleEntities;

    /**
     * Detalles usuario
     */
    @DBRef
    private UserDetailsDocument userDetails;

    /**
     * Detalles paramedico
     * Puede ser null si no es paramedico
     */
    @DBRef
    private ParamedicDetailsDocument paramedicDetails;

    /**
     * Habilitado
     */
    private Boolean enabled = true;

    /**
     * Intentos
     */
    private Integer attempts = 0;

    /**
     * Fecha de creación
     */
    @CreatedDate
    private Date createdOn;

    /**
     * Ultimo inicio de sesión
     */
    private Date lastLogin;

    /**
     * Token de recuperación de contraseña
     */
    private String resetToken;

}
