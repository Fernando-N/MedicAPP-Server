package cl.medicapp.service.services.auth;

import cl.medicapp.service.document.CommuneDocument;
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
import cl.medicapp.service.repository.user.UserDocumentRepository;
import cl.medicapp.service.repository.userdetails.UserDetailsDocumentRepository;
import cl.medicapp.service.services.email.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

class AuthServiceImplTest {

    @Mock
    private UserDocumentRepository userDocumentRepository;

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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        DocumentsHolder.getInstance().setRoleDocumentList(Collections.singletonList(RoleDocument.builder().name("PARAMEDIC").build()));
    }

    @Test
    void testRegister() {
        when(userDocumentRepository.findByEmailIgnoreCase(anyString())).thenReturn(Optional.empty());
        when(communeRepository.findById(anyString())).thenReturn(Optional.of(CommuneDocument.builder().build()));

        UserDto result = authServiceImpl.register(new UserDto("id", "email", "password", "rut", "firstName", "lastName", new GregorianCalendar(2020, Calendar.JULY, 20, 22, 28).getTime(), new CommuneDto("id", "name", new RegionDto("id", "label")), new RegionDto("id", "label"), true, "address", "aboutMe", true, "profileImage", "titleImage", 0, "certificateNationalHealth", "carnetImage", new GregorianCalendar(2020, Calendar.JULY, 20, 22, 28).getTime(), Integer.valueOf(0), Boolean.TRUE, new StatsDto(0, 0, 0), Arrays.<RoleDto>asList(new RoleDto("id", "name"))));

        assertNotNull(result);
    }

    @Test
    void testRecoveryPassword() {
        when(userDocumentRepository.findByEmailIgnoreCase(anyString())).thenReturn(Optional.of(UserDocument.builder().build()));

        GenericResponseDto result = authServiceImpl.recoveryPassword("email");

        assertNotNull(result);
    }

    @Test
    void testResetPassword() {
        RegionDocument regionDocument = RegionDocument.builder().build();
        CommuneDocument communeDocument = CommuneDocument.builder().region(regionDocument).build();
        UserDetailsDocument userDetailsDocument = UserDetailsDocument.builder().commune(communeDocument).build();

        List<RoleDocument> roleDocumentList = Collections.singletonList(RoleDocument.builder().name("PARAMEDIC").build());

        when(userDocumentRepository.findByResetToken(anyString())).thenReturn(Optional.of(UserDocument.builder().roleEntities(roleDocumentList).userDetails(userDetailsDocument).build()));

        ResetPasswordRequestDto request = new ResetPasswordRequestDto("token", "password", "password");

        UserDto result = authServiceImpl.resetPassword(request);

        assertNotNull(result);
    }
}
