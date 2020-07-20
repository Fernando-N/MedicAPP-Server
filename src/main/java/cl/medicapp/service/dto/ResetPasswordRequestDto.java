package cl.medicapp.service.dto;

import cl.medicapp.service.constants.Constants;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * Objeto de transferencia para request de restablecer contraseña
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResetPasswordRequestDto {

    /**
     * Token
     */
    @NotBlank(message = Constants.TOKEN_CANT_BE_EMPTY)
    private String token;

    /**
     * Contraseña
     */
    @NotBlank(message = Constants.PASSWORD_CANT_BE_EMPTY)
    private String password;

    /**
     * Confirmación de contraseña
     */
    @NotBlank(message = Constants.PASSWORD2_CANT_BE_EMPTY)
    private String passwordConfirmation;

}
