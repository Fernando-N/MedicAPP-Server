package cl.medicapp.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Objeto de transferencia para mensaje saliente
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageOutboundDto {

    /**
     * Id mensaje
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY, value = "_id")
    private String id;

    /**
     * Mensaje
     */
    @JsonProperty(value = "text")
    private String text;

    /**
     * Fecha de creación
     */
    @JsonProperty(value = "createdAt")
    private String date;

    /**
     * Remitente
     */
    @JsonProperty(value = "user")
    private UserChatDto user;

}
