package cl.medicapp.service.services.auth;

import cl.medicapp.service.dto.GenericResponseDto;
import cl.medicapp.service.dto.ResetPasswordRequestDto;
import cl.medicapp.service.dto.UserDto;

/**
 * Interfaz de servicio de autenticacion
 */
public interface AuthService {

    /**
     * @see AuthServiceImpl#register(UserDto)
     */
    UserDto register(UserDto usuario);

    /**
     * @see AuthServiceImpl#resetPassword(ResetPasswordRequestDto)
     */
    UserDto resetPassword(ResetPasswordRequestDto request);

    /**
     * @see AuthServiceImpl#recoveryPassword(String)
     */
    GenericResponseDto recoveryPassword(String email);

}
