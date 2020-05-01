package cl.medicapp.service.services.auth;

import cl.medicapp.service.dto.GenericResponseDto;
import cl.medicapp.service.dto.ResetPasswordRequestDto;
import cl.medicapp.service.dto.UserDto;

public interface AuthService {

    UserDto register(UserDto usuario);

    UserDto resetPassword(ResetPasswordRequestDto request);

    GenericResponseDto recoveryPassword(String email);

}
