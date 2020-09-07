package cl.medicapp.service.repository.report;

import cl.medicapp.service.document.ReportDocument;
import cl.medicapp.service.document.UserDocument;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio de reportes
 */
@Repository
public interface ReportRepository extends PagingAndSortingRepository<ReportDocument, String> {

    /**
     * Buscar todos los reportes
     * @return Lista de reportes
     */
    List<ReportDocument> findAll();

    /**
     * Busca todos los reportes creados por un usuario
     * @param user Usuario a buscar
     * @return Lista de reportes
     */
    List<ReportDocument> findByFrom(UserDocument user);

    /**
     * Busca todos los reportes hacia un usuario
     * @param user Usuario a buscar
     * @return Lista de reportes
     */
    List<ReportDocument> findByTo(UserDocument user);

}
