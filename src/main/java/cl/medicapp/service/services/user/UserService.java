package cl.medicapp.service.services.user;

import cl.medicapp.service.dto.GenericResponseDto;
import cl.medicapp.service.dto.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> getAll();

    UserDto getByEmail(String email);

    List<UserDto> getByName(String name, String lastName);

    UserDto save(UserDto userDto);

    GenericResponseDto deleteByEmail(String email);

}
