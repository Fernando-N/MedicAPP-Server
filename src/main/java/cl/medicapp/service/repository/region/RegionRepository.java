package cl.medicapp.service.repository.region;

import cl.medicapp.service.document.RegionDocument;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio de Usuarios
 */
@Repository
public interface RegionRepository extends PagingAndSortingRepository<RegionDocument, String> {

    List<RegionDocument> findAll();

    Optional<RegionDocument> findByNameIgnoreCase(String name);

    int deleteByNameIgnoreCase(String name);

}
