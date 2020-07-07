package cl.medicapp.service.services.user;

import cl.medicapp.service.constants.Constants;
import cl.medicapp.service.document.UserDocument;
import cl.medicapp.service.dto.ContentDto;
import cl.medicapp.service.dto.GenericResponseDto;
import cl.medicapp.service.dto.UserDto;
import cl.medicapp.service.exception.GenericException;
import cl.medicapp.service.holder.DocumentsHolder;
import cl.medicapp.service.repository.user.UserRepository;
import cl.medicapp.service.util.GenericResponseUtil;
import cl.medicapp.service.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final HttpServletResponse httpServletResponse;

    @Override
    public List<UserDto> getAll() {
        return userRepository.findAll().stream().map(UserUtil::toUserDto).collect(Collectors.toList());
    }

    @Override
    public UserDto getOwnProfile() {
        String emailUser = UserUtil.getEmailUserLogged();
        return UserUtil.toUserDto(userRepository.findByEmailIgnoreCase(emailUser).orElseThrow(GenericResponseUtil::getGenericException));
    }

    @Override
    public List<UserDto> getAllByRole(String role) {
        return userRepository.findAllByRole(
                DocumentsHolder.getInstance().getRoleDocumentList()
                        .stream()
                        .filter(roleDocument -> roleDocument.getName().contains(role))
                        .findFirst()
                        .orElseThrow(
                                () ->
                                        GenericResponseUtil.buildGenericException(
                                                HttpStatus.NOT_FOUND,
                                                "Rol no encontrado",
                                                String.format("Rol %s no encontrado", role)
                                        )
                        )
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
    public UserDto save(UserDto userDto) {
        userRepository.save(UserUtil.toUserDocument(userDto));
        return userDto;
    }

    //TODO Agregar implementación para que borre de UserDetails igualmente.
    @Override
    public GenericResponseDto deleteByEmail(String email) {
        if (userRepository.deleteByEmail(email)) {
            return GenericResponseDto.builder().message("User deleted").details(Collections.singletonList("User " + email + " delete")).build();
        }
        return GenericResponseUtil.buildGenericResponse(HttpStatus.NOT_FOUND.getReasonPhrase(), String.format(Constants.USER_X_NOT_FOUND, email));
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
