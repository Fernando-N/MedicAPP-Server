package cl.medicapp.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Objeto transferencia para retornar contenido
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContentDto {

    private String contentType;

    private String content;

}
