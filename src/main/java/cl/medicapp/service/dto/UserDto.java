package cl.medicapp.service.dto;

import cl.medicapp.service.annotation.Capitalize;
import cl.medicapp.service.annotation.LowerCase;
import cl.medicapp.service.constants.Constants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Objeto de transferencia para usuario
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto implements Serializable {

    /**
     * Email
     */
    @NotBlank(message = Constants.EMAIL_CANT_BE_EMPTY)
    @Email(message = Constants.EMAIL_SHOULD_BE_VALID)
    @LowerCase
    private String email;

    /**
     * Contraseña
     */
    @NotBlank(message = Constants.PASSWORD_CANT_BE_EMPTY)
    @Size(min = 6, max = 16, message = Constants.PASSWORD_MUST_BE_BETWEEN)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    /**
     * Primer nombre
     */
    @NotBlank(message = Constants.FIRST_NAME_CANT_BE_EMPTY)
    @Capitalize
    private String firstName;

    /**
     * Apellidos
     */
    @NotBlank(message = Constants.LAST_NAME_CANT_BE_EMPTY)
    @Capitalize
    private String lastName;

    /**
     * Fecha de creación
     * JsonIgnore para ocultar en respuestas
     */
    @CreatedDate
    private Date createdOn;

    /**
     * Intentos de inicio de sesión
     * JsonIgnore para ocultar en respuestas
     */
    @JsonIgnore
    private Integer attemps = 0;

    /**
     * Habilitado
     */
    private Boolean enabled = true;

    /**
     * Roles
     * JsonIgnore para ocultar en respuestas
     */
    private List<RoleDto> roleEntities;

}
