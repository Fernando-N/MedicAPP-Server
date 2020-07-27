package cl.medicapp.service.util;

import cl.medicapp.service.document.CommuneDocument;
import cl.medicapp.service.dto.DrugstoreResponse;
import cl.medicapp.service.dto.LocationDto;
import cl.medicapp.service.dto.LocationResponseDto;
import cl.medicapp.service.dto.PlaceResponseDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Clase utilitaria para locations
 */
public class LocationUtil {

    /**
     * Transforma respuesta de API a LocationDto
     * @param locationResponseDto target
     * @return target convertido a LocationDto
     */
    public static LocationDto toLocationDto(LocationResponseDto.Location locationResponseDto) {
        CommuneDocument communeDocument = DocumentsHolderUtil.getCommuneDocumentByName(locationResponseDto.getLocality());
        return LocationDto.builder()
                .latitude(locationResponseDto.getLatitude())
                .longitude(locationResponseDto.getLongitude())
                .address(locationResponseDto.getLabel())
                .communeId(communeDocument.getId())
                .communeName(communeDocument.getName())
                .regionId(communeDocument.getRegion().getId())
                .regionName(communeDocument.getRegion().getName())
                .build();
    }

    public static PlaceResponseDto toPlaceResponseDto(PlaceResponseDto.PlacesAPIResponseDto fetchGoogleMapsPlace) {
        return PlaceResponseDto.builder()
                .places(listToPlaceDto(fetchGoogleMapsPlace.getResults()))
                .build();
    }

    private static List<PlaceResponseDto.PlaceDto> listToPlaceDto(List<PlaceResponseDto.PlacesAPIResponseDto.ResultDto> list) {
        return list.stream().map(LocationUtil::toPlaceDto).collect(Collectors.toList());
    }

    private static PlaceResponseDto.PlaceDto toPlaceDto(PlaceResponseDto.PlacesAPIResponseDto.ResultDto place) {
        return PlaceResponseDto.PlaceDto.builder()
                .name(place.getName())
                .address(place.getVicinity())
                .latitude(place.getGeometry().getLocation().getLatitude())
                .longitude(place.getGeometry().getLocation().getLongitude())
                .build();
    }


    /**
     * Constructor privado para no permitir crear instancias de la clase
     */
    private LocationUtil() {

    }

    public static DrugstoreResponse filterDrugstores(LocationDto locationDto, PlaceResponseDto googlePlacesList, List<DrugstoreResponse.ResponseAPI> farmanetList) {

        List<DrugstoreResponse.Drugstore> drugstoreFarmanet = farmanetList.stream()
                .filter(drugstore -> drugstore.getCommune().trim().toUpperCase().contains(locationDto.getCommuneName().trim().toUpperCase()))
                .map(LocationUtil::toDrugstore)
                .collect(Collectors.toList());

        List<DrugstoreResponse.Drugstore> drugstoreGmapsPlaces = googlePlacesList.getPlaces().stream()
                .filter(place -> !place.getName().toUpperCase().contains("BOTILLERIA") && !place.getAddress().trim().toUpperCase().equalsIgnoreCase(locationDto.getCommuneName().trim().toUpperCase()))
                .map(LocationUtil::toDrugstore)
                .map(LocationUtil::clearPrefix)
                .collect(Collectors.toList());

        return DrugstoreResponse.builder()
                .communeId(locationDto.getCommuneId())
                .regionId(locationDto.getRegionId())
                .drugstores(drugstoreGmapsPlaces)
                .drugstoresAllNight(drugstoreFarmanet)
                .build();
    }

    private static DrugstoreResponse.Drugstore clearPrefix(DrugstoreResponse.Drugstore drugstore) {
        String newString = drugstore.getName().toUpperCase();

        newString = newString.replace("PHARMACY", "");
        newString = newString.replace("PHARMACIES", "");
        newString = newString.replace("FARMACIA", "");
        newString = newString.replace("FARMACIAS", "");
        newString = "Farmacia ".concat(newString);


        drugstore.setName(newString);

        return drugstore;
    }

    private static DrugstoreResponse.Drugstore toDrugstore(DrugstoreResponse.ResponseAPI responseAPI) {
        return DrugstoreResponse.Drugstore.builder()
                .address(responseAPI.getAddress())
                .allNight(true)
                .aperture(responseAPI.getAperture())
                .close(responseAPI.getClose())
                .commune(responseAPI.getCommune())
                .name(responseAPI.getName())
                .phone(responseAPI.getPhone())
                .build();
    }

    private static DrugstoreResponse.Drugstore toDrugstore(PlaceResponseDto.PlaceDto responseAPI) {

        responseAPI.setAddress(responseAPI.getAddress().replace("pharmacy", ""));
        responseAPI.setAddress(responseAPI.getAddress().replace("farmacia", ""));


        return DrugstoreResponse.Drugstore.builder()
                .address(responseAPI.getAddress())
                .allNight(false)
                .name(responseAPI.getName())
                .build();
    }

}
