package cl.medicapp.service.dto;

import cl.medicapp.service.constants.Constants;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * Objeto transferencia para regiones
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegionDto implements Serializable {

    /**
     * Identificado
     */
    @JsonProperty(value = "value")
    private String id;

    /**
     * Nombre
     */
    @JsonProperty(value = "label")
    @NotBlank(message = Constants.ROLE_NAME_CANT_BE_EMPTY)
    private String label;

}
