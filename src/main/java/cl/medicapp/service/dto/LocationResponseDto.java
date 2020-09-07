package cl.medicapp.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * Objeto para recibir respuesta de API
 */
@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LocationResponseDto implements Serializable {

    /**
     * Lista obtenida de API
     */
    private List<Location> data;

    /**
     * Formato de respueta API
     */
    @Builder
    @Getter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Location implements Serializable {
        @JsonProperty("latitude")
        private double latitude;

        @JsonProperty("longitude")
        private double longitude;

        @JsonProperty("type")
        private String type;

        @JsonProperty("name")
        private String name;

        @JsonProperty("number")
        private String number;

        @JsonProperty("postal_code")
        private String postalCode;

        @JsonProperty("street")
        private String street;

        @JsonProperty("confidential")
        private int confidential;

        @JsonProperty("region")
        private String region;

        @JsonProperty("region_code")
        private String regionCode;

        @JsonProperty("county")
        private String county;

        @JsonProperty("locality")
        private String locality;

        @JsonProperty("administrative_area")
        private String administrativeArea;

        @JsonProperty("neighbourhood")
        private String neighbourhood;

        @JsonProperty("country")
        private String country;

        @JsonProperty("country_code")
        private String countryCode;

        @JsonProperty("continent")
        private String continent;

        @JsonProperty("label")
        private String label;
    }
}
