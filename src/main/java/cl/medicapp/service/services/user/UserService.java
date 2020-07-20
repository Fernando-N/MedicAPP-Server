package cl.medicapp.service.services.user;

import cl.medicapp.service.dto.ContentDto;
import cl.medicapp.service.dto.GenericResponseDto;
import cl.medicapp.service.dto.StatsDto;
import cl.medicapp.service.dto.UserDto;

import java.util.List;

/**
 * Interfaz de servicio de usuario
 */
public interface UserService {

    /**
     * @see UserServiceImpl#getAll()
     */
    List<UserDto> getAll();

    /**
     * @see UserServiceImpl#getAllDisabled()
     */
    List<UserDto> getAllDisabled();

    /**
     * @see UserServiceImpl#getOwnProfile()
     */
    UserDto getOwnProfile();

    /**
     * @see UserServiceImpl#getAllByRole(String)
     */
    List<UserDto> getAllByRole(String role);

    /**
     * @see UserServiceImpl#getAllByRoleAndRegionId(String, String)
     */
    List<UserDto> getAllByRoleAndRegionId(String role, String regionId);

    /**
     * @see UserServiceImpl#getAllByRoleAndCommuneId(String, String)
     */
    List<UserDto> getAllByRoleAndCommuneId(String role, String communeId);

    /**
     * @see UserServiceImpl#getByEmail(String)
     */
    UserDto getByEmail(String email);

    /**
     * @see UserServiceImpl#getByName(String, String)
     */
    List<UserDto> getByName(String name, String lastName);

    /**
     * @see UserServiceImpl#getById(String)
     */
    UserDto getById(String id);

    /**
     * @see UserServiceImpl#getByRut(String)
     */
    UserDto getByRut(String rut);

    /**
     * @see UserServiceImpl#getUserImage(String)
     */
    ContentDto getUserImage(String id);

    /**
     * @see UserServiceImpl#getStats(String)
     */
    StatsDto getStats(String userId);

    /**
     * @see UserServiceImpl#edit(String, UserDto)
     */
    UserDto edit(String userId, UserDto userDto);

    /**
     * @see UserServiceImpl#save(UserDto)
     */
    UserDto save(UserDto userDto);

    /**
     * @see UserServiceImpl#deleteById(String)
     */
    GenericResponseDto deleteById(String id);

    /**
     * @see UserServiceImpl#enableUser(String, boolean)
     */
    GenericResponseDto enableUser(String userId, boolean enable);

}
