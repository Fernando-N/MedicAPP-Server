package cl.medicapp.service.repository.paramedicdetails;

import cl.medicapp.service.document.ParamedicDetailsDocument;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio de detalle de paramedicos
 */
@Repository
public interface ParamedicDetailsDocumentRepository extends PagingAndSortingRepository<ParamedicDetailsDocument, String> {

    /*
        Sin nada porque se utilizan metodos de PagingAndSortingRepository
     */

}
