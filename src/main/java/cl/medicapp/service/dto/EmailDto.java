package cl.medicapp.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Objeto de transferencia para email
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmailDto implements Serializable {

    /**
     * Email destino
     */
    private String email;

    /**
     * Titulo
     */
    private String title;

    /**
     * Cuerpo
     */
    private String body;

}
