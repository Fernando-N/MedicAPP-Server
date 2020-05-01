package cl.medicapp.service.dto;

import cl.medicapp.service.constants.Constants;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class ResetPasswordRequestDto {

    @NotBlank(message = Constants.TOKEN_CANT_BE_EMPTY)
    private String token;

    @NotBlank(message = Constants.PASSWORD_CANT_BE_EMPTY)
    private String password;

    @NotBlank(message = Constants.PASSWORD2_CANT_BE_EMPTY)
    private String passwordConfirmation;

}
