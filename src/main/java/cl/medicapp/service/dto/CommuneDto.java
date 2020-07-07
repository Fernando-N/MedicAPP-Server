package cl.medicapp.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Objeto transferencia para regiones
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
    private String value;

    /**
     * Nombre
     */
    private String label;

    /**
     * Region
     */
    @JsonIgnore
    private RegionDto region;

}
