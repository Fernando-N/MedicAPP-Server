package cl.medicapp.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageInboundDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY, value = "_id")
    private String id;

    @JsonProperty(value = "text")
    private String text;

    @JsonProperty(value = "createdAt")
    private Date date;

    @JsonProperty(value = "user")
    private UserChat from;

    @JsonProperty(value = "to")
    private String to;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class UserChat {

        @JsonProperty(value = "_id")
        private String id;

        @JsonProperty(value = "name")
        private String name;

        @JsonProperty(value = "avatar")
        private String avatarURI;

    }

}
