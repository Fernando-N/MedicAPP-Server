package cl.medicapp.service.services.user;

import cl.medicapp.service.constants.Constants;
import cl.medicapp.service.document.CommuneDocument;
import cl.medicapp.service.document.FeedbackDocument;
import cl.medicapp.service.document.RegionDocument;
import cl.medicapp.service.document.UserDocument;
import cl.medicapp.service.dto.ContentDto;
import cl.medicapp.service.dto.GenericResponseDto;
import cl.medicapp.service.dto.StatsDto;
import cl.medicapp.service.dto.UserDto;
import cl.medicapp.service.repository.commune.CommuneRepository;
import cl.medicapp.service.repository.feedback.FeedbackRepository;
import cl.medicapp.service.repository.region.RegionRepository;
import cl.medicapp.service.repository.user.UserRepository;
import cl.medicapp.service.services.chat.ChatService;
import cl.medicapp.service.util.DocumentsHolderUtil;
import cl.medicapp.service.util.FeedbackUtil;
import cl.medicapp.service.util.GenericResponseUtil;
import cl.medicapp.service.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementacion servicio de usuarios
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    /**
     * Bean de repositorio de usuarios
     */
    private final UserRepository userRepository;

    /**
     * Bean de repositorio de comunas
     */
    private final CommuneRepository communeRepository;

    /**
     * Bean de repositorio de regiones
     */
    private final RegionRepository regionRepository;

    /**
     * Bean de repositorio de feedbacks
     */
    private final FeedbackRepository feedbackRepository;

    /**
     * Bean de repositorio de chat
     */
    private final ChatService chatService;

    /**
     * Bean de BCryptPasswordEncoder
     */
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * Obtener todos los usuarios
     * @return Lista de usuarios
     */
    @Override
    public List<UserDto> getAll() {
        return userRepository.findAll().stream().map(UserUtil::toUserDto).collect(Collectors.toList());
    }

    /**
     * Obtener todos los usuarios deshabilitados
     * @return Lista de usuarios
     */
    @Override
    public List<UserDto> getAllDisabled() {
        return userRepository.findAllByEnabledFalse().stream().map(UserUtil::toUserDto).collect(Collectors.toList());
    }

    /**
     * Obtener usuario en sesion
     * @return Usuario en sesion
     */
    @Override
    public UserDto getOwnProfile() {
        String emailUser = UserUtil.getEmailUserLogged();
        return UserUtil.toUserDto(userRepository.findByEmailIgnoreCase(emailUser).orElseThrow(GenericResponseUtil::getGenericException));
    }

    /**
     * Obtener todos los usuarios de un rol
     * @param role Rol a buscar
     * @return Lista de usuarios
     */
    @Override
    public List<UserDto> getAllByRole(String role) {
        List<UserDto> userDtos = userRepository.findAllByRole(DocumentsHolderUtil.getRoleDocumentByName(role))
                .stream()
                .map(UserUtil::toUserDto)
                .collect(Collectors.toList());

        userDtos.forEach(user -> user.setStats(getStats(user.getId())));

        return userDtos;
    }

    /**
     * Obtener todos los usuarios de un rol y una region
     * @param role Rol a buscar
     * @param regionId Id de region a buscar
     * @return Lista de usuarios encontrados
     */
    @Override
    public List<UserDto> getAllByRoleAndRegionId(String role, String regionId) {
        return userRepository.findAllByRoleAndRegion(
                DocumentsHolderUtil.getRoleDocumentByName(role),
                DocumentsHolderUtil.getRegionDocumentById(regionId)
        )
                .stream()
                .map(UserUtil::toUserDto)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene todos los usuarios de un rol y una comuna
     * @param role Rol a buscar
     * @param communeId Id de comuna a buscar
     * @return Lista de usuarios encontrados
     */
    @Override
    public List<UserDto> getAllByRoleAndCommuneId(String role, String communeId) {
        return userRepository.findAllByRoleAndCommune(
                DocumentsHolderUtil.getRoleDocumentByName(role),
                DocumentsHolderUtil.getCommuneDocumentById(communeId)
        )
                .stream()
                .map(UserUtil::toUserDto)
                .collect(Collectors.toList());
    }

    /**
     * Obtener usuario por email
     * @param email email a buscar
     * @return Usuario encontrado
     */
    @Override
    public UserDto getByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email).map(UserUtil::toUserDto)
                .orElseThrow(() -> GenericResponseUtil.buildGenericException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.getReasonPhrase(), String.format(Constants.USER_X_NOT_FOUND, email)));
    }

    /**
     * Obtener usuario por nombre y apellido
     * @param firstName Nombre
     * @param lastName Apellido
     * @return Lista de usuarios encontrados
     */
    @Override
    public List<UserDto> getByName(String firstName, String lastName) {
        List<UserDto> userDtoList = new ArrayList<>();

        List<Optional<UserDocument>> listUser = userRepository.findByFirstNameAndLastName(firstName, lastName);

        listUser.forEach(userDocument -> {

            if (!userDocument.isPresent()) {
                throw GenericResponseUtil.buildGenericException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.getReasonPhrase(), String.format(Constants.USER_X_X_NOT_FOUND, firstName, lastName));
            }

            userDtoList.add(UserUtil.toUserDto(userDocument.get()));
        });

        return userDtoList;
    }

    /**
     * Obtener usuario por su id
     * @param id Id usuario
     * @return Usuario encontrado
     */
    @Override
    public UserDto getById(String id) {
        return userRepository.findById(id).map(UserUtil::toUserDto)
                .orElseThrow(() -> GenericResponseUtil.buildGenericException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.getReasonPhrase(), String.format(Constants.USER_X_NOT_FOUND, id)));
    }

    /**
     * Obtener usuario por rut
     * @param rut Rut a buscar
     * @return Usuario encontrado
     */
    @Override
    public UserDto getByRut(String rut) {
        return userRepository.findByRut(rut).map(UserUtil::toUserDto)
                .orElseThrow(() -> GenericResponseUtil.buildGenericException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.getReasonPhrase(), String.format(Constants.USER_X_NOT_FOUND, rut)));
    }

    /**
     * Obtiene la imagen de perfil de un usuario
     * @param id User id
     * @return ContentDto con URI a imagen
     */
    @Override
    public ContentDto getUserImage(String id) {
        Optional<UserDocument> user = userRepository.findById(id);

        if (!user.isPresent()) {
            throw GenericResponseUtil.buildGenericException(HttpStatus.INTERNAL_SERVER_ERROR, "");
        }

        return ContentDto.builder().contentType("image").content(user.get().getUserDetails().getProfileImageURI()).build();
    }

    /**
     * Obtiene las estadisticas de un paramedico
     * @param userId Id de usuario
     * @return Estadisticas
     */
    @Override
    public StatsDto getStats(String userId) {
        Optional<UserDocument> user = userRepository.findById(userId);

        if (!user.isPresent() || !user.get().getRoleEntities().contains(DocumentsHolderUtil.getRoleDocumentByName(Constants.PARAMEDIC))) {
            throw GenericResponseUtil.buildGenericException(HttpStatus.INTERNAL_SERVER_ERROR, "");
        }

        List<FeedbackDocument> feedbackDocuments = feedbackRepository.findAllByTo(user.get());

        int contacts = chatService.getMessagesToUser(userId, false).size();
        int valuations = feedbackDocuments.size();
        int rating = FeedbackUtil.calculateRating(feedbackDocuments);

        return StatsDto.builder().contacts(contacts).rating(rating).valuations(valuations).build();
    }

    /**
     * Editar un usuario
     * @param userId Id usuario
     * @param newUser Usuario con nuevos datos
     * @return Usuario actualizado
     */
    @Override
    public UserDto edit(String userId, UserDto newUser) {
        Optional<UserDocument> actualUser = userRepository.findById(userId);
        Optional<CommuneDocument> communeDocument = communeRepository.findById(newUser.getCommune().getId());
        Optional<RegionDocument> regionDocument = regionRepository.findById(newUser.getRegion().getId());

        if (!actualUser.isPresent()) {
            throw GenericResponseUtil.buildGenericException(HttpStatus.NOT_FOUND, "User not found", String.format("User id %s not found", userId));
        }

        if (!communeDocument.isPresent()) {
            throw GenericResponseUtil.buildGenericException(HttpStatus.NOT_FOUND, "Commune not found", String.format("Commune id %s not found", userId));
        }

        if (!regionDocument.isPresent()) {
            throw GenericResponseUtil.buildGenericException(HttpStatus.NOT_FOUND, "Region not found", String.format("Region id %s not found", userId));
        }

        UserDocument userMerged = UserUtil.merge(newUser, actualUser.get());
        userMerged.getUserDetails().setId(actualUser.get().getUserDetails().getId());
        userMerged.getUserDetails().setCommune(communeDocument.get());

        if (newUser.isParamedic()) {
            userMerged.getParamedicDetails().setId(actualUser.get().getParamedicDetails().getId());
        }

        if (newUser.getPassword() != null) {
            userMerged.setPassword(passwordEncoder.encode(newUser.getPassword()));
        }else {
            userMerged.setPassword(actualUser.get().getPassword());
        }

        return UserUtil.toUserDto(userRepository.save(userMerged));
    }

    /**
     * Guarda un nuevo usuario
     * @param userDto Usuario a guardar
     * @return Usuario guardado
     */
    @Override
    public UserDto save(UserDto userDto) {
        userRepository.save(UserUtil.toUserDocument(userDto));
        return userDto;
    }

    /**
     * Eliminar usuario por id
     * @param id Id usuario
     * @return Resultado
     */
    @Override
    public GenericResponseDto deleteById(String id) {
        if (userRepository.deleteById(id)) {
            return GenericResponseDto.builder().message("User deleted").details(Collections.singletonList("User " + id + " delete")).build();
        }
        return GenericResponseUtil.buildGenericResponse(HttpStatus.NOT_FOUND.getReasonPhrase(), String.format(Constants.USER_X_NOT_FOUND, id));
    }

    /**
     * Cambia el estado de habilitado de un usuario
     * @param userId Id de usuario
     * @param enable Estado de usuario
     * @return Resultado
     */
    @Override
    public GenericResponseDto enableUser(String userId, boolean enable) {
        Optional<UserDocument> user = userRepository.findById(userId);

        if (!user.isPresent()) {
            return GenericResponseUtil.buildGenericResponse(HttpStatus.NOT_FOUND.getReasonPhrase(), String.format("User id %s not found!", userId));
        }

        user.get().setEnabled(enable);

        userRepository.save(user.get());

        return GenericResponseUtil.buildGenericResponse(HttpStatus.OK.getReasonPhrase(), String.format("User %s fue %s correctamente", user.get().getEmail(), enable ? "habilitado" : "deshabilitado"));
    }
}
