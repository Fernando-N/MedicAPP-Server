package cl.medicapp.service.repository;

import cl.medicapp.service.entity.UserEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio de Usuarios
 */
@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmailIgnoreCase(String email);

    Optional<List<UserEntity>> findByFirstNameAndLastName(String firstName, String lastName);

    Optional<UserEntity> findByEmailIgnoreCaseAndEnabledTrue(String username);

    Optional<UserEntity> findByResetToken(String resetToken);

    int deleteByEmail(String email);

}
