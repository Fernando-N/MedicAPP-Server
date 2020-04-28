package cl.medicapp.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto implements Serializable {

    /**
     * Email
     */
    @NotBlank(message = "Email no puede estar vacio")
    @Email(message = "email should be a valid email")
    private String email;

    /**
     * Contraseña
     */
    @NotBlank(message = "Contraseña no puede estar vacio")
    @Size(min = 6, max = 16, message = "Password must be between 6 and 16 characters long")
    private String password;

    /**
     * Primer nombre
     */
    @NotBlank(message = "Nombre no puede estar vacio")
    private String firstName;

    /**
     * Apellidos
     */
    @NotBlank(message = "Apellido no puede estar vacio")
    private String lastName;

    /**
     * Fecha de creación
     * JsonIgnore para ocultar en respuestas
     */
    @JsonIgnore
    private Date createdOn;

    /**
     * Intentos de inicio de sesión
     * JsonIgnore para ocultar en respuestas
     */
    @JsonIgnore
    private Integer attemps = 0;

    /**
     * Habilitado
     * JsonIgnore para ocultar en respuestas
     */
    @JsonIgnore
    private Boolean enabled = true;

    /**
     * Roles
     * JsonIgnore para ocultar en respuestas
     */
    @JsonIgnore
    private List<RoleDto> roleEntities;

}
