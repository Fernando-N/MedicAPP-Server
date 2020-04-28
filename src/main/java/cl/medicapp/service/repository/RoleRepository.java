package cl.medicapp.service.repository;

import cl.medicapp.service.entity.RoleEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio de Roles
 */
@Repository
public interface RoleRepository extends PagingAndSortingRepository<RoleEntity, Long> {

    Optional<RoleEntity> findByName(String role);

    int deleteByName(String name);

}