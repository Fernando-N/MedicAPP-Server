package cl.medicapp.service.repository.region;

import cl.medicapp.service.document.RegionDocument;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio de Usuarios
 */
@Repository
public interface RegionRepository extends PagingAndSortingRepository<RegionDocument, String> {

    Optional<RegionDocument> findByNameIgnoreCase(String name);

    int deleteByNameIgnoreCase(String name);

}
