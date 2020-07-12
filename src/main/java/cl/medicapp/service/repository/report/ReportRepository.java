package cl.medicapp.service.repository.report;

import cl.medicapp.service.document.ReportDocument;
import cl.medicapp.service.document.RoleDocument;
import cl.medicapp.service.document.UserDocument;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio de Roles
 */
@Repository
public interface ReportRepository extends PagingAndSortingRepository<ReportDocument, String> {

    List<ReportDocument> findAll();

    List<ReportDocument> findByFrom(UserDocument user);

    List<ReportDocument> findByTo(UserDocument user);

}
