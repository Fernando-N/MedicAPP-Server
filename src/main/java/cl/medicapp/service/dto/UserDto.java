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
     * Id de usuario
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY, value = "key")
    private String id;

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
    @Size(min = 6, max = 16, message = Constants.PASSWORD_MUST_BE_BETWEEN)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    /**
     * Rut
     */
    @NotBlank(message = "RUT can't be empty!")
    private String rut;

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
     * Fecha de nacimiento
     */
    private Date birthDay;

    /**
     * Comuna
     */
    private CommuneDto commune;

    /**
     * Region
     */
    private RegionDto region;

    /**
     * Flag para mostrar dirección en perfil
     */
    private boolean showAddress;

    /**
     * Direccion
     */
    @NotBlank(message = "Address can't be empty!")
    private String address;

    /**
     * Sobre mi
     */
    private String aboutMe;

    /**
     * Flag es paramedico
     */
    private boolean isParamedic;

    /**
     * B64 de Imagen de perfil
     */
    private String profileImage;

    /**
     * B64 de Imagen de titulo técnico/universitario
     */
    private String titleImage;

    /**
     * Año de graduación
     */
    private int graduationYear;

    /**
     * B64 de Imagen de certificado de inscripción en registro de prestadores individuales
     */
    private String certificateNationalHealth;

    /**
     * B64 de Imagen de carnet
     */
    private String carnetImage;

    /**
     * Fecha de creación
     * JsonIgnore para ocultar en respuestas
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date createdOn;

    /**
     * Intentos de inicio de sesión
     * JsonIgnore para ocultar en respuestas
     */
    @JsonIgnore
    private Integer attempts = 0;

    /**
     * Habilitado
     */
    private Boolean enabled = true;

    /**
     * Stats de paramedico
     * puede ser null si no es paramedico
     */
    private StatsDto stats;

    /**
     * Roles
     * JsonIgnore para ocultar en respuestas
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<RoleDto> roleEntities;

}
