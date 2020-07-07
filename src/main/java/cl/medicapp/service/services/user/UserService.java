package cl.medicapp.service.services.user;

import cl.medicapp.service.dto.ContentDto;
import cl.medicapp.service.dto.GenericResponseDto;
import cl.medicapp.service.dto.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> getAll();

    UserDto getOwnProfile();

    List<UserDto>  getAllByRole(String role);

    UserDto getByEmail(String email);

    List<UserDto> getByName(String name, String lastName);

    UserDto getById(String id);

    UserDto getByRut(String rut);

    ContentDto getUserImage(String id);

    UserDto save(UserDto userDto);

    GenericResponseDto deleteByEmail(String email);

    GenericResponseDto enableUser(String userId, boolean enable);

}
