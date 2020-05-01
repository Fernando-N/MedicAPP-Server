package cl.medicapp.service.dto;

import cl.medicapp.service.annotation.UpperCase;
import cl.medicapp.service.constants.Constants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * Objeto transferencia para role
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto implements Serializable {

    /**
     * Identificado
     */
    @JsonIgnore
    private String id;

    /**
     * Nombre
     */
    @NotBlank(message = Constants.ROLE_NAME_CANT_BE_EMPTY)
    @UpperCase
    private String name;

}