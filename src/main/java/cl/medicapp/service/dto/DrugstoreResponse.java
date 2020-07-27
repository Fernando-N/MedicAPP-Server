package cl.medicapp.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * Objeto de transferencia para Locaciones
 */
@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DrugstoreResponse {

    /**
     * Farmacias
     */
    private List<Drugstore> drugstores;

    private List<Drugstore> drugstoresAllNight;

    /**
     * Comuna
     */
    private String communeId;

    /**
     * Region
     */
    private String regionId;

    @Builder
    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Drugstore implements Serializable {

        private String name;
        private String address;
        private String commune;
        private String aperture;
        private String close;
        private String phone;
        private boolean allNight;

    }


    @Builder
    @Getter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ResponseAPI implements Serializable {

        @JsonProperty("fecha")
        private String date;

        @JsonProperty("local_id")
        private String localId;

        @JsonProperty("local_nombre")
        private String name;

        @JsonProperty("comuna_nombre")
        private String commune;

        @JsonProperty("fk_localidad")
        private String fkLocation;

        @JsonProperty("localidad_nombre")
        private String localityName;

        @JsonProperty("local_direccion")
        private String address;

        @JsonProperty("funcionamiento_hora_apertura")
        private String aperture;

        @JsonProperty("funcionamiento_hora_cierre")
        private String close;

        @JsonProperty("local_telefono")
        private String phone;

        @JsonProperty("local_lat")
        private String latitude;

        @JsonProperty("local_lng")
        private String longitude;

        @JsonProperty("funcionamiento_dia")
        private String dayOpen;

        @JsonProperty("fk_region")
        private String fkRegion;

        @JsonProperty("fk_comuna")
        private String fkCommune;

    }
}
