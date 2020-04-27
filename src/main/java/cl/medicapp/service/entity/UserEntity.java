package cl.medicapp.service.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Entity User
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Identificador
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    /**
     * Email
     */
    @Column(name = "email", unique = true, length = 100, nullable = false)
    private String email;

    /**
     * Contraseña
     */
    @Column(name = "password", length = 60, nullable = false)
    private String password;

    /**
     * Nombres
     */
    @Column(name = "first_name")
    private String firstName;

    /**
     * Apellido
     */
    @Column(name = "last_name")
    private String lastName;

    /**
     * Roles
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role_id"})})
    private List<RoleEntity> roleEntities;

    /**
     * Habilitado
     */
    @Column(name = "enabled", columnDefinition = "boolean default true")
    private Boolean enabled;

    /**
     * Intentos
     */
    @Column(name = "attemps", columnDefinition = "integer default 0")
    private Integer attemps;

    /**
     * Fecha de creación
     */
    @CreatedDate
    @Column(name = "created_on")
    private Date createdOn;

    /**
     * Ultimo inicio de sesión
     */
    @Column(name = "last_login")
    private Timestamp lastLogin;

    /**
     * Token de recuperación de contraseña
     */
    @Column(name = "reset_token")
    private String resetToken;

}
