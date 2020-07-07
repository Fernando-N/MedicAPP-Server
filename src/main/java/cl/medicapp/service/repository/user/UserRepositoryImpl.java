package cl.medicapp.service.repository.user;

import cl.medicapp.service.document.CommuneDocument;
import cl.medicapp.service.document.RoleDocument;
import cl.medicapp.service.document.UserDetailsDocument;
import cl.medicapp.service.document.UserDocument;
import cl.medicapp.service.repository.commune.CommuneRepository;
import cl.medicapp.service.repository.userdetails.UserDetailsDocumentRepository;
import cl.medicapp.service.util.GenericResponseUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserDocumentRepository userRepository;
    private final UserDetailsDocumentRepository userDetailsRepository;
    private final CommuneRepository communeRepository;

    @Override
    public Optional<UserDocument> findById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public List<UserDocument> findAll() {
        return userRepository.findAll();
    }

    @Override
    public List<UserDocument> findAllByEnabledFalse() {
        return userRepository.findAllByEnabledFalse();
    }

    @Override
    public List<UserDocument> findAllByRole(RoleDocument roleDocument) {
        return userRepository.findAllByRoleEntities(roleDocument);
    }

    @Override
    public UserDocument save(UserDocument userDocument) {
        UserDetailsDocument userDetailsDocumentSaved = userDetailsRepository.save(userDocument.getUserDetails());
        CommuneDocument communeDocumentObtained = communeRepository.findByNameIgnoreCase(userDocument.getUserDetails().getCommune().getName()).orElseThrow(GenericResponseUtil::getGenericException);
        userDocument.setUserDetails(userDetailsDocumentSaved);
        userDocument.getUserDetails().setCommune(communeDocumentObtained);
        return userRepository.save(userDocument);
    }

    @Override
    public Optional<UserDocument> findByEmailIgnoreCase(String email) {
        return userRepository.findByEmailIgnoreCase(email);
    }

    @Override
    public Optional<List<UserDocument>> findByFirstNameAndLastName(String firstName, String lastName) {
        List<UserDetailsDocument> usersDetailsDocuments = userDetailsRepository.findByFirstNameAndLastName(firstName, lastName);
        return Optional.of(usersDetailsDocuments.stream()
                .map(userRepository::findByUserDetails)
                .collect(Collectors.toList()));
    }

    @Override
    public Optional<UserDocument> findByEmailIgnoreCaseAndEnabledTrue(String username) {
        return userRepository.findByEmailIgnoreCaseAndEnabledTrue(username);
    }

    @Override
    public Optional<UserDocument> findByResetToken(String resetToken) {
        return userRepository.findByResetToken(resetToken);
    }

    @Override
    public boolean deleteByEmail(String email) {
        return userRepository.deleteByEmail(email) >= 1;
    }
}
