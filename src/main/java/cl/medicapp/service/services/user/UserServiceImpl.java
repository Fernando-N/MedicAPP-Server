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

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CommuneRepository communeRepository;
    private final RegionRepository regionRepository;
    private final FeedbackRepository feedbackRepository;
    private final ChatService chatService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public List<UserDto> getAll() {
        return userRepository.findAll().stream().map(UserUtil::toUserDto).collect(Collectors.toList());
    }

    @Override
    public List<UserDto> getAllDisabled() {
        return userRepository.findAllByEnabledFalse().stream().map(UserUtil::toUserDto).collect(Collectors.toList());
    }

    @Override
    public UserDto getOwnProfile() {
        String emailUser = UserUtil.getEmailUserLogged();
        return UserUtil.toUserDto(userRepository.findByEmailIgnoreCase(emailUser).orElseThrow(GenericResponseUtil::getGenericException));
    }

    @Override
    public List<UserDto> getAllByRole(String role) {
        List<UserDto> userDtos = userRepository.findAllByRole(DocumentsHolderUtil.getRoleDocumentByName(role))
                .stream()
                .map(UserUtil::toUserDto)
                .collect(Collectors.toList());

        userDtos.forEach(user -> user.setStats(getStats(user.getId())));

        return userDtos;
    }

    @Override
    public List<UserDto> getAllByRegionId(String role, String regionId) {
        return userRepository.findAllByRoleAndRegion(
                DocumentsHolderUtil.getRoleDocumentByName(role),
                DocumentsHolderUtil.getRegionDocumentById(regionId)
        )
                .stream()
                .map(UserUtil::toUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> getAllByCommuneId(String role, String communeId) {
        return userRepository.findAllByRoleAndCommune(
                DocumentsHolderUtil.getRoleDocumentByName(role),
                DocumentsHolderUtil.getCommuneDocumentById(communeId)
        )
                .stream()
                .map(UserUtil::toUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email).map(UserUtil::toUserDto)
                .orElseThrow(() -> GenericResponseUtil.buildGenericException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.getReasonPhrase(), String.format(Constants.USER_X_NOT_FOUND, email)));
    }

    @Override
    public List<UserDto> getByName(String firstName, String lastName) {
        List<UserDto> userDtoList = new ArrayList<>();


        Optional<List<UserDocument>> user = userRepository.findByFirstNameAndLastName(firstName, lastName);

        if (!user.isPresent()) {
            throw GenericResponseUtil.buildGenericException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.getReasonPhrase(), String.format(Constants.USER_X_X_NOT_FOUND, firstName, lastName));
        }

        user.get().forEach(userDocument -> userDtoList.add(UserUtil.toUserDto(userDocument)));

        return userDtoList;
    }

    @Override
    public UserDto getById(String id) {
        return userRepository.findById(id).map(UserUtil::toUserDto)
                .orElseThrow(() -> GenericResponseUtil.buildGenericException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.getReasonPhrase(), String.format(Constants.USER_X_NOT_FOUND, id)));
    }

    @Override
    //TODO Implementar esto
    public UserDto getByRut(String rut) {
        return null;
    }

    @Override
    public ContentDto getUserImage(String id) {
        Optional<UserDocument> user = userRepository.findById(id);

        if (!user.isPresent()) {
            throw GenericResponseUtil.buildGenericException(HttpStatus.INTERNAL_SERVER_ERROR, "");
        }

        return ContentDto.builder().contentType("image").content(user.get().getUserDetails().getProfileImageURI()).build();
    }

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

    @Override
    public UserDto save(UserDto userDto) {
        userRepository.save(UserUtil.toUserDocument(userDto));
        return userDto;
    }

    //TODO Agregar implementación para que borre de UserDetails igualmente.
    @Override
    public GenericResponseDto deleteById(String id) {
        if (userRepository.deleteById(id)) {
            return GenericResponseDto.builder().message("User deleted").details(Collections.singletonList("User " + id + " delete")).build();
        }
        return GenericResponseUtil.buildGenericResponse(HttpStatus.NOT_FOUND.getReasonPhrase(), String.format(Constants.USER_X_NOT_FOUND, id));
    }

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
