package cl.medicapp.service.services.auth;

import cl.medicapp.service.document.CommuneDocument;
import cl.medicapp.service.document.ParamedicDetailsDocument;
import cl.medicapp.service.document.RegionDocument;
import cl.medicapp.service.document.RoleDocument;
import cl.medicapp.service.document.UserDetailsDocument;
import cl.medicapp.service.document.UserDocument;
import cl.medicapp.service.dto.CommuneDto;
import cl.medicapp.service.dto.GenericResponseDto;
import cl.medicapp.service.dto.RegionDto;
import cl.medicapp.service.dto.ResetPasswordRequestDto;
import cl.medicapp.service.dto.RoleDto;
import cl.medicapp.service.dto.StatsDto;
import cl.medicapp.service.dto.UserDto;
import cl.medicapp.service.holder.DocumentsHolder;
import cl.medicapp.service.repository.commune.CommuneRepository;
import cl.medicapp.service.repository.paramedicdetails.ParamedicDetailsDocumentRepository;
import cl.medicapp.service.repository.role.RoleRepository;
import cl.medicapp.service.repository.user.UserDocumentRepository;
import cl.medicapp.service.repository.userdetails.UserDetailsDocumentRepository;
import cl.medicapp.service.services.email.EmailService;
import cl.medicapp.service.util.UserUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyString;

/**
 * Clase de test unitario para AuthService.
 */
class AuthServiceImplTest {

    @Mock
    private UserDocumentRepository userDocumentRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserDetailsDocumentRepository userDetailsDocumentRepository;

    @Mock
    private ParamedicDetailsDocumentRepository paramedicDetailsDocumentRepository;

    @Mock
    private CommuneRepository communeRepository;

    @Mock
    private EmailService emailService;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthServiceImpl authServiceImpl;

    /**
     * Método Set-up de DocumentsHolder para realizar flujos
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        DocumentsHolder.getInstance().setCommuneDocumentList(Collections.singletonList(CommuneDocument.builder().name("Comuna test").id("id Comuna test").region(RegionDocument.builder().name("Region test").ordinal("I").id("id Region test").build()).build()));
        DocumentsHolder.getInstance().setRoleDocumentList(Collections.singletonList(RoleDocument.builder().id("id Role").name("PARAMEDIC").build()));
    }

    /**
     * Método test registro usuario
     */
    @Test
    void testRegister() {
        Optional<UserDocument> userDocumentOptional = Optional.empty();
        Optional<CommuneDocument> communeDocumentOptional = Optional.of(CommuneDocument.builder().build());

        when(userDocumentRepository.findByEmailIgnoreCase(anyString())).thenReturn(userDocumentOptional);
        when(communeRepository.findById(Mockito.anyString())).thenReturn(communeDocumentOptional);

        UserDto result = authServiceImpl.register(new UserDto("id", "email", "password", "rut", "firstName", "lastName", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 23).getTime(), new CommuneDto("value", "label", new RegionDto("value", "label")), new RegionDto("value", "label"), true, "address", "aboutMe", true, "profileImage", "titleImage", 0, "certificateNationalHealth", "carnetImage", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 23).getTime(), Integer.valueOf(0), Boolean.TRUE, new StatsDto(0, 0, 0), Arrays.<RoleDto>asList(new RoleDto("id", "name"))));
        Assertions.assertEquals(new UserDto("id", "email", "password", "rut", "firstName", "lastName", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 23).getTime(), new CommuneDto("value", "label", new RegionDto("value", "label")), new RegionDto("value", "label"), true, "address", "aboutMe", true, "profileImage", "titleImage", 0, "certificateNationalHealth", "carnetImage", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 23).getTime(), Integer.valueOf(0), Boolean.TRUE, new StatsDto(0, 0, 0), Arrays.<RoleDto>asList(new RoleDto("id", "name"))), result);
    }

    /**
     * Método test envio correo recuperación de contraseña
     */
    @Test
    void testRecoveryPassword() {
        Optional<UserDocument> userDocumentOptional = Optional.of(UserDocument.builder().build());

        when(userDocumentRepository.findByEmailIgnoreCase(anyString())).thenReturn(userDocumentOptional);

        GenericResponseDto result = authServiceImpl.recoveryPassword("email");

        GenericResponseDto expected = GenericResponseDto.builder()
                .message("Email was sent successfully")
                .details(Collections.singletonList("if the email email is registered you will receive an email to reset the password"))
                .build();

        Assertions.assertEquals(expected.getMessage(), result.getMessage());
        Assertions.assertEquals(expected.getDetails(), result.getDetails());
    }

    /**
     * Método test restablecer contraseña
     */
    @Test
    void testResetPassword() {
        RegionDocument regionDocument = RegionDocument.builder().build();
        CommuneDocument communeDocument = CommuneDocument.builder().region(regionDocument).build();
        Optional<UserDocument> userDocumentOptional = Optional.of(UserDocument.builder()
                .userDetails(UserDetailsDocument.builder().commune(communeDocument).build())
                .paramedicDetails(ParamedicDetailsDocument.builder().build())
                .roleEntities(Collections.singletonList(RoleDocument.builder().name("ROLE_PARAMEDIC").build()))
                .build());

        when(userDocumentRepository.findByResetToken(anyString())).thenReturn(userDocumentOptional);

        ResetPasswordRequestDto req = ResetPasswordRequestDto.builder()
                .token("TOKEN")
                .password("PASSWORD")
                .passwordConfirmation("PASSWORD")
                .build();

        UserDto result = authServiceImpl.resetPassword(req);
        Assertions.assertEquals(UserUtil.toUserDto(userDocumentOptional.get()), result);
    }
}

