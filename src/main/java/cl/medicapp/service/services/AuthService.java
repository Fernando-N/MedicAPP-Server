package cl.medicapp.service.services;

import cl.medicapp.service.dto.UserDto;

public interface AuthService {

    UserDto register(UserDto usuario);

    UserDto resetPassword(String token, String password);

    void recoveryPassword(String email);

}
