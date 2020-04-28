package cl.medicapp.service.services.user;

import cl.medicapp.service.dto.GenericResponseDto;
import cl.medicapp.service.dto.UserDto;
import cl.medicapp.service.exception.GenericException;
import cl.medicapp.service.repository.UserRepository;
import cl.medicapp.service.util.UserUtil;
import lombok.RequiredArgsConstructor;
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
        userRepository.findAll().forEach(userEntity -> userDtoList.add(UserUtil.toUserDto(userEntity)));
        return userDtoList;
    }

    @Override
    public UserDto getByEmail(String email) {
        return userRepository.findByEmail(email).map(UserUtil::toUserDto)
                .orElseThrow(() -> new GenericException("User not found", Collections.singletonList("User " + email + " not found")));
    }

    @Override
    public List<UserDto> getByName(String firstName, String lastName) {
        List<UserDto> userDtoList = new ArrayList<>();
        userRepository.findByFirstNameAndLastName(firstName, lastName).ifPresentOrElse(
                usersList ->
                        usersList.forEach(userEntity -> userDtoList.add(UserUtil.toUserDto(userEntity))),
                () -> {
                    throw new GenericException("User not found", Collections.singletonList("User " + firstName + " " + lastName + " not found"));
                });
        return userDtoList;
    }

    @Override
    public UserDto save(UserDto userDto) {
        userRepository.save(UserUtil.toUserEntity(userDto));
        return userDto;
    }

    @Override
    public GenericResponseDto deleteByEmail(String email) {
        int rowsDelete = userRepository.deleteByEmail(email);
        if (rowsDelete>1) {
            return GenericResponseDto.builder().message("User deleted").details(Collections.singletonList("User "+email+" delete")).build();
        }
        return GenericResponseDto.builder().message("User not found").details(Collections.singletonList("User "+email+" not found")).build();
    }
}
