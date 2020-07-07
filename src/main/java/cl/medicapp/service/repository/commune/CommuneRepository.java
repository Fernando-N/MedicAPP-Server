package cl.medicapp.service.repository.commune;

import cl.medicapp.service.document.CommuneDocument;
import cl.medicapp.service.document.RegionDocument;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio de Usuarios
 */
@Repository
public interface CommuneRepository extends PagingAndSortingRepository<CommuneDocument, String> {

    List<CommuneDocument> findAll();

    List<CommuneDocument> findAllByRegion(RegionDocument region);

    Optional<CommuneDocument> findByNameIgnoreCase(String name);

    int deleteByNameIgnoreCase(String name);

}
