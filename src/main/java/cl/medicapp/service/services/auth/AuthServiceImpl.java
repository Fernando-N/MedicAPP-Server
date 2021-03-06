package cl.medicapp.service.services.auth;

import cl.medicapp.service.annotation.FormatArgs;
import cl.medicapp.service.constants.Constants;
import cl.medicapp.service.document.CommuneDocument;
import cl.medicapp.service.document.ParamedicDetailsDocument;
import cl.medicapp.service.document.UserDetailsDocument;
import cl.medicapp.service.document.UserDocument;
import cl.medicapp.service.dto.EmailDto;
import cl.medicapp.service.dto.GenericResponseDto;
import cl.medicapp.service.dto.ResetPasswordRequestDto;
import cl.medicapp.service.dto.UserDto;
import cl.medicapp.service.repository.commune.CommuneRepository;
import cl.medicapp.service.repository.paramedicdetails.ParamedicDetailsDocumentRepository;
import cl.medicapp.service.repository.user.UserDocumentRepository;
import cl.medicapp.service.repository.userdetails.UserDetailsDocumentRepository;
import cl.medicapp.service.services.email.EmailService;
import cl.medicapp.service.util.DocumentsHolderUtil;
import cl.medicapp.service.util.GenericResponseUtil;
import cl.medicapp.service.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementacion de servicio de autenticación
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    /**
     * Bean de repositorio de usuarios
     */
    private final UserDocumentRepository userDocumentRepository;

    /**
     * Bean de repositorio de detalles de usuario
     */
    private final UserDetailsDocumentRepository userDetailsDocumentRepository;

    /**
     * Bean de repositorio de detalles de paramedico
     */
    private final ParamedicDetailsDocumentRepository paramedicDetailsDocumentRepository;

    /**
     * Bean de repositorio de comunas
     */
    private final CommuneRepository communeRepository;

    /**
     * Bean de servicio de email
     */
    private final EmailService emailService;

    /**
     * Bean de BcryptPasswordEncoder
     */
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * Implementación endpoint de registro
     * @param newUser Nuevo usuario
     * @return Usuario nuevo
     */
    @Override
    @FormatArgs
    //TODO Terminar esta implementación
    public UserDto register(UserDto newUser) {
        Optional<UserDocument> user = userDocumentRepository.findByEmailIgnoreCase(newUser.getEmail());

        if (user.isPresent()) {
            throw GenericResponseUtil.buildGenericException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(), String.format(Constants.EMAIL_X_ALREADY_REGISTER, newUser.getEmail()));
        }

        CommuneDocument commune = communeRepository.findById(newUser.getCommune().getId()).orElseThrow(GenericResponseUtil::getGenericException);
        UserDetailsDocument userDetailsDocument = userDetailsDocumentRepository.save(UserUtil.buildUserDetailsDocument(newUser, commune));
        ParamedicDetailsDocument paramedicDetailsDocument = null;

        if (newUser.isParamedic()) {
            paramedicDetailsDocument = paramedicDetailsDocumentRepository.save(UserUtil.buildParamedicDetailsDocument(newUser));
        }

        UserDocument userDocument = UserUtil.toUserDocument(newUser);
        userDocument.setUserDetails(userDetailsDocument);
        userDocument.setParamedicDetails(paramedicDetailsDocument);
        userDocument.setPassword(passwordEncoder.encode(newUser.getPassword()));
        userDocument.setRoleEntities(Collections.singletonList(
                newUser.isParamedic() ?
                        DocumentsHolderUtil.getRoleDocumentByName(Constants.PARAMEDIC)
                        :
                        DocumentsHolderUtil.getRoleDocumentByName(Constants.USER)
                )
        );
        userDocument.setEnabled(!newUser.isParamedic());

        userDocumentRepository.save(userDocument);

        return newUser;
    }

    /**
     * Implementación endpoint de recuperar contraseña
     * @param email Email a recuperar contraseña
     * @return Respuesta con mensaje indicando que si existe el correo recibira un mensaje
     */
    //TODO Mover esto a constantes y generar plantilla mail
    @Override
    public GenericResponseDto recoveryPassword(String email) {
        userDocumentRepository.findByEmailIgnoreCase(email).ifPresent(userDocument -> {
            userDocument.setResetToken(UUID.randomUUID().toString());
            userDocumentRepository.save(userDocument);
            String body = "token to recovery password: ".concat(userDocument.getResetToken());
            EmailDto recoveryMail = EmailDto.builder().email(userDocument.getEmail()).title("Password recovery").body(body).build();
            emailService.sendEmail(recoveryMail);
        });

        return GenericResponseDto.builder()
                .message("Email was sent successfully")
                .details(Collections.singletonList(String.format("if the email %s is registered you will receive an email to reset the password", email))).build();
    }

    /**
     * Restablece la constraseña de un usuario
     * @param request Request que contiene token y nueva contraseña
     * @return Usuario restablecido
     */
    @Override
    public UserDto resetPassword(ResetPasswordRequestDto request) {

        if (!request.getPassword().equals(request.getPasswordConfirmation())) {
            throw GenericResponseUtil.buildGenericException(HttpStatus.BAD_REQUEST, Constants.PASSWORD_MUST_MATCH, Constants.INPUT_PASSWORDS_NOT_MATCH);
        }

        if (request.getPassword().length() < 6 || request.getPassword().length() > 16) {
            throw GenericResponseUtil.buildGenericException(HttpStatus.BAD_REQUEST, Constants.PASSWORD_MUST_BE_BETWEEN, Constants.PASSWORD_MUST_BE_BETWEEN);
        }

        UserDocument user = userDocumentRepository.findByResetToken(request.getToken()).map(userDocument -> {
            userDocument.setResetToken(null);
            userDocument.setPassword(passwordEncoder.encode(request.getPassword()));
            userDocumentRepository.save(userDocument);
            return userDocument;
        }).orElseThrow(() -> GenericResponseUtil.buildGenericException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.getReasonPhrase(), String.format(Constants.TOKEN_NOT_FOUND, request.getToken())));

        return UserUtil.toUserDto(user);
    }

}
