package cl.medicapp.service.util;

import cl.medicapp.service.document.CommuneDocument;
import cl.medicapp.service.document.RegionDocument;
import cl.medicapp.service.document.RoleDocument;
import cl.medicapp.service.holder.DocumentsHolder;
import org.springframework.http.HttpStatus;

public class DocumentsHolderUtil {

    public static RoleDocument getRoleDocumentByName(String name) {
        return DocumentsHolder.getInstance().getRoleDocumentList().stream()
                .filter(roleDocument -> roleDocument.getName().toUpperCase().contains(name.toUpperCase()))
                .findFirst()
                .orElseThrow(
                        () ->
                                GenericResponseUtil.buildGenericException(
                                        HttpStatus.NOT_FOUND,
                                        "Rol no encontrado",
                                        String.format("Rol %s no encontrado", name)
                                )
                );
    }

    public static CommuneDocument getCommuneDocumentById(String communeId) {
        return DocumentsHolder.getInstance().getCommuneDocumentList()
                .stream()
                .filter(communeDocument -> communeDocument.getId().contains(communeId))
                .findFirst()
                .orElseThrow(
                        () ->
                                GenericResponseUtil.buildGenericException(
                                        HttpStatus.NOT_FOUND,
                                        "Communa no encontrada",
                                        String.format("Comuna id %s no encontrada", communeId)
                                )
                );
    }

    public static RegionDocument getRegionDocumentById(String regionId) {
        return DocumentsHolder.getInstance().getRegionDocumentList()
                .stream()
                .filter(regionDocument -> regionDocument.getId().contains(regionId))
                .findFirst()
                .orElseThrow(
                        () ->
                                GenericResponseUtil.buildGenericException(
                                        HttpStatus.NOT_FOUND,
                                        "Region no encontrada",
                                        String.format("Region id %s no encontrada", regionId)
                                )
                );
    }

    private DocumentsHolderUtil () {

    }

}
