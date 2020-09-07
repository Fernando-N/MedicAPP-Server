package cl.medicapp.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Objeto de transferencia para stats
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatsDto implements Serializable {

    /**
     * Cantidad de valoreaciones/feedbacks
     */
    private int valuations;

    /**
     * Cantidad de Contactos
     */
    private int contacts;

    /**
     * Rating
     */
    private int rating;

}
