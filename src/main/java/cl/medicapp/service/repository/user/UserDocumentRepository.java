package cl.medicapp.service.repository.user;

import cl.medicapp.service.document.RegionDocument;
import cl.medicapp.service.document.RoleDocument;
import cl.medicapp.service.document.UserDetailsDocument;
import cl.medicapp.service.document.UserDocument;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio de Usuarios
 */
@Repository
public interface UserDocumentRepository extends PagingAndSortingRepository<UserDocument, String> {

    List<UserDocument> findAll();

    List<UserDocument> findAllByEnabledFalse();

    List<UserDocument> findAllByRoleEntities(RoleDocument role);

    List<UserDocument> findAllByRoleEntitiesAndUserDetailsIn(RoleDocument role, List<UserDetailsDocument> userDetailsDocuments);

    Optional<UserDocument> findByEmailIgnoreCase(String email);

    UserDocument findByUserDetails(UserDetailsDocument userDetails);

    Optional<UserDocument> findByEmailIgnoreCaseAndEnabledTrue(String username);

    Optional<UserDocument> findByResetToken(String resetToken);

    int deleteByEmail(String email);

}
