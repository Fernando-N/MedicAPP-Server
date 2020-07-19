package cl.medicapp.service.repository.user;

import cl.medicapp.service.document.RoleDocument;
import cl.medicapp.service.document.UserDocument;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<UserDocument> findById(String id);

    List<UserDocument> findAll();

    List<UserDocument> findAllByEnabledFalse();

    List<UserDocument> findAllByRole(RoleDocument roleDocument);

    UserDocument save(UserDocument userDocument);

    Optional<UserDocument> findByEmailIgnoreCase(String email);

    Optional<List<UserDocument>> findByFirstNameAndLastName(String firstName, String lastName);

    Optional<UserDocument> findByEmailIgnoreCaseAndEnabledTrue(String username);

    Optional<UserDocument> findByResetToken(String resetToken);

    boolean deleteById(String id);

}
