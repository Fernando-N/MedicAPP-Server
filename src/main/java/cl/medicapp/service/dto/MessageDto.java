package cl.medicapp.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Dto para mensajes de chat
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageDto {

    /**
     * Id
     */
    @JsonProperty(value = "_id")
    private String id;

    /**
     * Mensaje
     */
    @JsonProperty(value = "text")
    private String message;

    /**
     * Fecha
     */
    @JsonProperty(value = "createdAt")
    private Date date;

    /**
     * De
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY, value = "user")
    private UserChat from;

    private String to;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class UserChat {

        private String id;

        private String name;

        private String avatar;

    }

}
