package cl.medicapp.service.repository.nationality;

import cl.medicapp.service.document.NationalityDocument;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio de Nacionalidades
 */
@Repository
public interface NationalityRepository extends PagingAndSortingRepository<NationalityDocument, String> {

    List<NationalityDocument> findAll();

    Optional<NationalityDocument> findByNameIgnoreCase(String name);

    int deleteByNameIgnoreCase(String name);

}
