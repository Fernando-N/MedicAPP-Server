package cl.medicapp.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Objeto de transferencia para Locaciones
 */
@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LocationDto {

    /**
     * Latitud
     */
    private double latitude;

    /**
     * Longitud
     */
    private double longitude;

    /**
     * Dirección
     */
    private String address;

    /**
     * Comuna
     */
    private String communeId;

    private String communeName;

    /**
     * Region
     */
    private String regionId;

    private String regionName;

}
