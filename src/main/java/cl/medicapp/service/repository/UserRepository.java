package cl.medicapp.service.repository;

import cl.medicapp.service.entity.UserEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * Repositorio de Usuarios
 */
@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByEmailIgnoreCaseAndEnabledTrue(String username);

    Optional<UserEntity> findByResetToken(String resetToken);

    boolean existsByEmail(String email);

    @Transactional
    @Modifying
    @Query(value = "UPDATE users u SET u.last_login = CURRENT_TIMESTAMP WHERE u.email = :email", nativeQuery = true)
    void updateLastLogin(String email);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE users u SET u.first_name = :firstName, u.last_name = :lastName, u.password = :password WHERE u.email = :email", nativeQuery = true)
    Optional<UserEntity> update(String email, String firstName, String lastName, String password);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE users u SET u.attemps = u.attemps+1 WHERE u.email = :email", nativeQuery = true)
    void incrementAttemps(String email);

}
