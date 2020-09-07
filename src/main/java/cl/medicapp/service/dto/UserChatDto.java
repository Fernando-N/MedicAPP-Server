package cl.medicapp.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Objeto de transferencia para usuario de chat
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserChatDto {

    /**
     * Id usuario
     */
    @JsonProperty(value = "_id")
    private String id;

    /**
     * Nombre usuario
     */
    @JsonProperty(value = "name")
    private String name;

    /**
     * URI Avatar
     */
    @JsonProperty(value = "avatar")
    private String avatarURI;

}