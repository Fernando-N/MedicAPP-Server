package cl.medicapp.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * Objeto transferencia para lugares
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlaceResponseDto implements Serializable {

    /**
     * Tipo de lugar
     */
    private String name;

    private List<PlaceDto> places;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlaceDto implements Serializable {
        private String name;
        private double latitude;
        private double longitude;
        private String address;
    }

    @Builder
    @Getter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class PlacesAPIResponseDto implements Serializable {

        @JsonProperty("results")
        private List<ResultDto> results;

        @Builder
        @Getter
        @ToString
        @AllArgsConstructor
        @NoArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public static class ResultDto implements Serializable {

            @JsonProperty("business_status")
            private String businessStatus;

            @JsonProperty("geometry")
            private Geometry geometry;

            @JsonProperty("name")
            private String name;

            @JsonProperty("vicinity")
            private String vicinity;

            @Builder
            @Getter
            @ToString
            @AllArgsConstructor
            @NoArgsConstructor
            @JsonInclude(JsonInclude.Include.NON_NULL)
            public static class Geometry implements Serializable {

                @JsonProperty("location")
                private Location location;

                @Builder
                @Getter
                @ToString
                @AllArgsConstructor
                @NoArgsConstructor
                @JsonInclude(JsonInclude.Include.NON_NULL)
                public static class Location implements Serializable {

                    @JsonProperty("lat")
                    private double latitude;

                    @JsonProperty("lng")
                    private double longitude;

                }

            }

        }

    }

}
