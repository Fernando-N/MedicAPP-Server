package cl.medicapp.service.repository;

import cl.medicapp.service.entity.RoleEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio de Roles
 */
@Repository
public interface RoleRepository extends PagingAndSortingRepository<RoleEntity, Long> {

    RoleEntity findByName(String role);

}