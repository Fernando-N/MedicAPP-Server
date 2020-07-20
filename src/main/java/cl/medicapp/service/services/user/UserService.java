package cl.medicapp.service.services.user;

import cl.medicapp.service.dto.ContentDto;
import cl.medicapp.service.dto.GenericResponseDto;
import cl.medicapp.service.dto.StatsDto;
import cl.medicapp.service.dto.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> getAll();

    List<UserDto> getAllDisabled();

    UserDto getOwnProfile();

    List<UserDto> getAllByRole(String role);

    List<UserDto> getAllByRegionId(String role, String regionId);

    List<UserDto> getAllByCommuneId(String role, String communeId);

    UserDto getByEmail(String email);

    List<UserDto> getByName(String name, String lastName);

    UserDto getById(String id);

    UserDto getByRut(String rut);

    ContentDto getUserImage(String id);

    StatsDto getStats(String userId);

    UserDto edit(String userId, UserDto userDto);

    UserDto save(UserDto userDto);

    GenericResponseDto deleteById(String id);

    GenericResponseDto enableUser(String userId, boolean enable);

}
