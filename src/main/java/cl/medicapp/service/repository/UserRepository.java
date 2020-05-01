package cl.medicapp.service.repository;

import cl.medicapp.service.document.UserDocument;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio de Usuarios
 */
@Repository
public interface UserRepository extends PagingAndSortingRepository<UserDocument, Long> {

    Optional<UserDocument> findByEmailIgnoreCase(String email);

    Optional<List<UserDocument>> findByFirstNameAndLastName(String firstName, String lastName);

    Optional<UserDocument> findByEmailIgnoreCaseAndEnabledTrue(String username);

    Optional<UserDocument> findByResetToken(String resetToken);

    int deleteByEmail(String email);

}
