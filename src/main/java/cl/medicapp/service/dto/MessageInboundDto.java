package cl.medicapp.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Objeto de transferencia para mensaje entrante
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageInboundDto {

    /**
     * Mensaje
     */
    @JsonProperty(value = "text")
    private String text;

    /**
     * Remitente
     */
    @JsonProperty(value = "user")
    private UserChatDto from;

    /**
     * Id destinatario
     */
    @JsonProperty(value = "to")
    private String to;

}
