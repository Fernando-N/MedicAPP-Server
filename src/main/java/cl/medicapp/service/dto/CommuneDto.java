package cl.medicapp.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Objeto transferencia para comuna
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommuneDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Identificador
     */
    @JsonProperty(value = "value")
    private String id;

    /**
     * Nombre
     */
    @JsonProperty(value = "label")
    private String name;

    /**
     * Region
     */
    @JsonIgnore
    private RegionDto region;

}
