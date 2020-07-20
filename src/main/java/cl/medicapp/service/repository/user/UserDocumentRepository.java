package cl.medicapp.service.repository.user;

import cl.medicapp.service.document.RoleDocument;
import cl.medicapp.service.document.UserDetailsDocument;
import cl.medicapp.service.document.UserDocument;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio de usuarios
 */
@Repository
public interface UserDocumentRepository extends PagingAndSortingRepository<UserDocument, String> {

    /**
     * @see UserRepositoryImpl#findAll()
     */
    List<UserDocument> findAll();

    /**
     * @see UserRepositoryImpl#findAllByEnabledFalse()
     */
    List<UserDocument> findAllByEnabledFalse();

    /**
     * @see UserRepositoryImpl#findAllByRole(RoleDocument)
     */
    List<UserDocument> findAllByRoleEntities(RoleDocument role);

    /**
     * Buscar usuarios por rol y lista de UserDetailsDocuments
     * @param role Rol a buscar
     * @param userDetailsDocuments Lista de UserDetailsDocument
     * @return Lista de usuarios
     */
    List<UserDocument> findAllByRoleEntitiesAndUserDetailsIn(RoleDocument role, List<UserDetailsDocument> userDetailsDocuments);

    /**
     * @see UserRepositoryImpl#findByEmailIgnoreCase(String)
     */
    Optional<UserDocument> findByEmailIgnoreCase(String email);

    /**
     * Buscar usuario por UserDetailsDocument
     * @param userDetails UserDetailsDocument a buscar
     * @return Usuario encontrado
     */
    UserDocument findByUserDetails(UserDetailsDocument userDetails);

    /**
     * @see UserRepositoryImpl#findByEmailIgnoreCaseAndEnabledTrue(String)
     */
    Optional<UserDocument> findByEmailIgnoreCaseAndEnabledTrue(String email);

    /**
     * @see UserRepositoryImpl#findByResetToken(String)
     */
    Optional<UserDocument> findByResetToken(String resetToken);

    /**
     * Eliminar usuario por email
     * @param email Email a eliminar
     * @return Resultado
     */
    int deleteByEmail(String email);

}
