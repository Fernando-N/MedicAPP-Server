package cl.medicapp.service.services.user;

import cl.medicapp.service.constants.Constants;
import cl.medicapp.service.dto.GenericResponseDto;
import cl.medicapp.service.dto.UserDto;
import cl.medicapp.service.repository.UserRepository;
import cl.medicapp.service.util.GenericResponseUtil;
import cl.medicapp.service.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<UserDto> getAll() {
        List<UserDto> userDtoList = new ArrayList<>();
        userRepository.findAll().forEach(userDocument -> userDtoList.add(UserUtil.toUserDto(userDocument)));
        return userDtoList;
    }

    @Override
    public UserDto getByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email).map(UserUtil::toUserDto)
                .orElseThrow(() -> GenericResponseUtil.buildGenericException(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), String.format(Constants.USER_X_NOT_FOUND, email)));
    }

    @Override
    public List<UserDto> getByName(String firstName, String lastName) {
        List<UserDto> userDtoList = new ArrayList<>();
        userRepository.findByFirstNameAndLastName(firstName, lastName).ifPresentOrElse(
                usersList ->
                        usersList.forEach(userDocument -> userDtoList.add(UserUtil.toUserDto(userDocument))),
                () -> {
                    throw GenericResponseUtil.buildGenericException(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), String.format(Constants.USER_X_X_NOT_FOUND, firstName, lastName));
                });
        return userDtoList;
    }

    @Override
    public UserDto save(UserDto userDto) {
        userRepository.save(UserUtil.toUserDocument(userDto));
        return userDto;
    }

    @Override
    public GenericResponseDto deleteByEmail(String email) {
        int rowsDelete = userRepository.deleteByEmail(email);
        if (rowsDelete > 1) {
            return GenericResponseDto.builder().message("User deleted").details(Collections.singletonList("User " + email + " delete")).build();
        }
        return GenericResponseUtil.buildGenericResponse(HttpStatus.NOT_FOUND.getReasonPhrase(), String.format(Constants.USER_X_NOT_FOUND, email));
    }
}
