package cl.medicapp.service.holder;

import cl.medicapp.service.document.CommuneDocument;
import cl.medicapp.service.document.NationalityDocument;
import cl.medicapp.service.document.RegionDocument;
import cl.medicapp.service.document.RoleDocument;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Clase estatica que se encarga de mantener los resultados de base de datos de los repositorios:
 * - RoleRepository
 * - RegionRepository
 * - CommuneRepository
 * - NationalityRepository
 * Esto se implementa para guardar en una instancia los resultados y evitar consultar a la base de datos los resultados.
 */
@Getter
@Setter
public class DocumentsHolder {

    /**
     * Constructor privado para evitar crear instancias de la clase
     */
    private DocumentsHolder() {

    }

    /**
     * Instancia estatica de la propia clase
     */
    @Getter
    private static final DocumentsHolder instance = new DocumentsHolder();

    /**
     * Lista de regiones
     */
    private List<RegionDocument> regionDocumentList;

    /**
     * Lista de comunas
     */
    private List<CommuneDocument> communeDocumentList;

    /**
     * Lista de roles
     */
    private List<RoleDocument> roleDocumentList;

    /**
     * Lista de nacionalidades
     */
    private List<NationalityDocument> nationalityDocumentList;

}
