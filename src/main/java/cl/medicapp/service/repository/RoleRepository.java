package cl.medicapp.service.repository;

import cl.medicapp.service.document.RoleDocument;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio de Roles
 */
@Repository
public interface RoleRepository extends PagingAndSortingRepository<RoleDocument, Long> {

    Optional<RoleDocument> findByNameIgnoreCaseEndsWith(String role);

    int deleteByNameIgnoreCase(String name);

}