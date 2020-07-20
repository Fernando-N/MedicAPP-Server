package cl.medicapp.service.repository.user;

import cl.medicapp.service.document.CommuneDocument;
import cl.medicapp.service.document.RegionDocument;
import cl.medicapp.service.document.RoleDocument;
import cl.medicapp.service.document.UserDetailsDocument;
import cl.medicapp.service.document.UserDocument;
import cl.medicapp.service.holder.DocumentsHolder;
import cl.medicapp.service.repository.commune.CommuneRepository;
import cl.medicapp.service.repository.paramedicdetails.ParamedicDetailsDocumentRepository;
import cl.medicapp.service.repository.userdetails.UserDetailsDocumentRepository;
import cl.medicapp.service.util.GenericResponseUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementacion de repositorio de usuario
 */
@AllArgsConstructor
@Repository
public class UserRepositoryImpl implements UserRepository {

    /**
     * Bean repositorio de detalles de usuario
     */
    private final UserDocumentRepository userRepository;

    /**
     * Bean repositorio de usuarios
     */
    private final UserDetailsDocumentRepository userDetailsRepository;

    /**
     * Bean repositorio de detalles de paramedicos
     */
    private final ParamedicDetailsDocumentRepository paramedicDetailsDocumentRepository;

    /**
     * Bean repositorio de comunas
     */
    private final CommuneRepository communeRepository;

    /**
     * Buscar usuario por id
     * @param id Id a buscar
     * @return Usuario encontrado
     */
    @Override
    public Optional<UserDocument> findById(String id) {
        return userRepository.findById(id);
    }

    /**
     * Buscar todos los usuarios
     * @return Lista de usuarios
     */
    @Override
    public List<UserDocument> findAll() {
        return userRepository.findAll();
    }

    /**
     * Buscar todos los usuarios que esten deshabilitados
     * @return Lista de usuarios
     */
    @Override
    public List<UserDocument> findAllByEnabledFalse() {
        return userRepository.findAllByEnabledFalse();
    }

    /**
     * Buscar todos los usuarios por rol
     * @param roleDocument Rol a buscar
     * @return Lista de usuarios
     */
    @Override
    public List<UserDocument> findAllByRole(RoleDocument roleDocument) {
        return userRepository.findAllByRoleEntities(roleDocument);
    }

    /**
     * Busca todos los usuarios de un rol y region
     * @param roleDocument Rol a buscar
     * @param regionDocument Region a buscar
     * @return Lista de usuarios
     */
    @Override
    public List<UserDocument> findAllByRoleAndRegion(RoleDocument roleDocument, RegionDocument regionDocument) {
        List<CommuneDocument> communeDocumentList = DocumentsHolder.getInstance()
                .getCommuneDocumentList()
                .stream()
                .filter(communeDocument -> communeDocument.getRegion().equals(regionDocument))
                .collect(Collectors.toList());

        List<UserDetailsDocument> userDetailsDocumentList = userDetailsRepository.findAllByCommuneIn(communeDocumentList);

        return userRepository.findAllByRoleEntitiesAndUserDetailsIn(roleDocument, userDetailsDocumentList);
    }

    /**
     * Buscar usuarios por rol y comuna
     * @param roleDocument Rol a buscar
     * @param communeDocument Comuna a buscar
     * @return Lista de usuarios
     */
    @Override
    public List<UserDocument> findAllByRoleAndCommune(RoleDocument roleDocument, CommuneDocument communeDocument) {
        List<UserDetailsDocument> userDetailsDocumentList = userDetailsRepository.findAllByCommuneIn(Collections.singletonList(communeDocument));
        return userRepository.findAllByRoleEntitiesAndUserDetailsIn(roleDocument, userDetailsDocumentList);
    }

    /**
     * Guardar un usuario
     * @param userDocument Usuario a guardar
     * @return Usuario guardado
     */
    @Override
    public UserDocument save(UserDocument userDocument) {
        UserDetailsDocument userDetailsDocumentSaved = userDetailsRepository.save(userDocument.getUserDetails());
        CommuneDocument communeDocumentObtained = communeRepository.findByNameIgnoreCase(userDocument.getUserDetails().getCommune().getName()).orElseThrow(GenericResponseUtil::getGenericException);
        userDocument.setUserDetails(userDetailsDocumentSaved);
        userDocument.getUserDetails().setCommune(communeDocumentObtained);
        return userRepository.save(userDocument);
    }

    /**
     * Buscar usuario por email
     * @param email Email a buscar
     * @return Usuario encontrado
     */
    @Override
    public Optional<UserDocument> findByEmailIgnoreCase(String email) {
        return userRepository.findByEmailIgnoreCase(email);
    }

    /**
     * Buscar usuarios por su nombre y apellido
     * @param firstName Nombre
     * @param lastName Apellido
     * @return Lista de usuarios encontrados
     */
    @Override
    public Optional<List<UserDocument>> findByFirstNameAndLastName(String firstName, String lastName) {
        List<UserDetailsDocument> usersDetailsDocuments = userDetailsRepository.findByFirstNameAndLastName(firstName, lastName);
        return usersDetailsDocuments.stream()
                .map(userRepository::findByUserDetails)
                .collect(Collectors.toList());
    }

    /**
     * Buscar usuario por email y que este habilitado
     * @param email Email
     * @return Usuario encontrado
     */
    @Override
    public Optional<UserDocument> findByEmailIgnoreCaseAndEnabledTrue(String email) {
        return userRepository.findByEmailIgnoreCaseAndEnabledTrue(email);
    }

    /**
     * Buscar usuario por token de restablecimiento
     * @param resetToken token de restablecimiento
     * @return Usuario encontrado
     */
    @Override
    public Optional<UserDocument> findByResetToken(String resetToken) {
        return userRepository.findByResetToken(resetToken);
    }

    @Override
    public Optional<UserDocument> findByRut(String rut) {
        Optional<UserDetailsDocument> userDetailsDocumentOptional = userDetailsRepository.findByRut(rut);

        if (!userDetailsDocumentOptional.isPresent()) {
            throw GenericResponseUtil.buildGenericException(HttpStatus.NOT_FOUND, "User not found", String.format("User id %s not found", rut));
        }

        return userRepository.findByUserDetails(userDetailsDocumentOptional.get());
    }

    /**
     * Eliminar un usuario por su id
     * @param id Id usuario a eliminar
     * @return Resultado
     */
    @Override
    public boolean deleteById(String id) {

        Optional<UserDocument> userDocumentOptional = userRepository.findById(id);

        if (!userDocumentOptional.isPresent()) {
            return false;
        }

        if (userDocumentOptional.get().getParamedicDetails() != null) {
            paramedicDetailsDocumentRepository.delete(userDocumentOptional.get().getParamedicDetails());
        }

        userDetailsRepository.delete(userDocumentOptional.get().getUserDetails());
        userRepository.delete(userDocumentOptional.get());

        return true;
    }
}
