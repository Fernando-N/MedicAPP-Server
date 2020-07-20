package cl.medicapp.service.util;

import cl.medicapp.service.document.CommuneDocument;
import cl.medicapp.service.document.RegionDocument;
import cl.medicapp.service.document.RoleDocument;
import cl.medicapp.service.holder.DocumentsHolder;
import org.springframework.http.HttpStatus;

/**
 * Clase utilitaria para DocumentsHolder
 */
public class DocumentsHolderUtil {

    /**
     * Busca un rol por su nombre
     * @param name Nombre
     * @return Rol encontrado
     */
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

    /**
     * Busca una comuna por su id
     * @param communeId Id de comuna
     * @return Comuna encontrada
     */
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

    /**
     * Busca region por su id
     * @param regionId Id de region a buscar
     * @return Region encontrada
     */
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

    /**
     * Constructor privado para no permitir crear instancias de la clase
     */
    private DocumentsHolderUtil () {

    }

}
