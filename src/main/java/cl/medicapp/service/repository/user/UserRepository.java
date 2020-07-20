package cl.medicapp.service.repository.user;

import cl.medicapp.service.document.CommuneDocument;
import cl.medicapp.service.document.RegionDocument;
import cl.medicapp.service.document.RoleDocument;
import cl.medicapp.service.document.UserDocument;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio de usuario
 */
public interface UserRepository {

    /**
     * @see UserRepositoryImpl#findById(String)
     */
    Optional<UserDocument> findById(String id);

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
    List<UserDocument> findAllByRole(RoleDocument roleDocument);

    /**
     * @see UserRepositoryImpl#findAllByRoleAndRegion(RoleDocument, RegionDocument)
     */
    List<UserDocument> findAllByRoleAndRegion(RoleDocument roleDocument, RegionDocument regionDocument);

    /**
     * @see UserRepositoryImpl#findAllByRoleAndCommune(RoleDocument, CommuneDocument)
     */
    List<UserDocument> findAllByRoleAndCommune(RoleDocument roleDocument, CommuneDocument regionDocument);

    /**
     * @see UserRepositoryImpl#save(UserDocument)
     */
    UserDocument save(UserDocument userDocument);

    /**
     * @see UserRepositoryImpl#findByEmailIgnoreCase(String)
     */
    Optional<UserDocument> findByEmailIgnoreCase(String email);

    /**
     * @see UserRepositoryImpl#findByFirstNameAndLastName(String, String)
     */
    List<Optional<UserDocument>> findByFirstNameAndLastName(String firstName, String lastName);

    /**
     * @see UserRepositoryImpl#findByEmailIgnoreCaseAndEnabledTrue(String)
     */
    Optional<UserDocument> findByEmailIgnoreCaseAndEnabledTrue(String username);

    /**
     * @see UserRepositoryImpl#findByResetToken(String)
     */
    Optional<UserDocument> findByResetToken(String resetToken);

    /**
     * @see UserRepositoryImpl#findByRut(String)
     */
    Optional<UserDocument> findByRut(String rut);

    /**
     * @see UserRepositoryImpl#deleteById(String)
     */
    boolean deleteById(String id);

}
