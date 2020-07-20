package cl.medicapp.service.services.user;

import cl.medicapp.service.document.CommuneDocument;
import cl.medicapp.service.document.FeedbackDocument;
import cl.medicapp.service.document.NationalityDocument;
import cl.medicapp.service.document.ParamedicDetailsDocument;
import cl.medicapp.service.document.RegionDocument;
import cl.medicapp.service.document.RoleDocument;
import cl.medicapp.service.document.UserDetailsDocument;
import cl.medicapp.service.document.UserDocument;
import cl.medicapp.service.dto.CommuneDto;
import cl.medicapp.service.dto.ContentDto;
import cl.medicapp.service.dto.GenericResponseDto;
import cl.medicapp.service.dto.MessageOutboundDto;
import cl.medicapp.service.dto.RegionDto;
import cl.medicapp.service.dto.RoleDto;
import cl.medicapp.service.dto.StatsDto;
import cl.medicapp.service.dto.UserDto;
import cl.medicapp.service.repository.feedback.FeedbackRepository;
import cl.medicapp.service.repository.user.UserRepository;
import cl.medicapp.service.services.chat.ChatService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static org.mockito.Mockito.*;

class UserServiceImplTest {
    @Mock
    UserRepository userRepository;
    @Mock
    FeedbackRepository feedbackRepository;
    @Mock
    ChatService chatService;
    @Mock
    BCryptPasswordEncoder passwordEncoder;
    @InjectMocks
    UserServiceImpl userServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAll() {
        when(userRepository.findAll()).thenReturn(Arrays.<UserDocument>asList(new UserDocument("id", "email", "password", Arrays.<RoleDocument>asList(null), null, null, Boolean.TRUE, Integer.valueOf(0), new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), "resetToken")));

        List<UserDto> result = userServiceImpl.getAll();
        Assertions.assertEquals(Arrays.<UserDto>asList(new UserDto("id", "email", "password", "rut", "firstName", "lastName", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), new CommuneDto("value", "label", new RegionDto("value", "label")), new RegionDto("value", "label"), true, "address", "aboutMe", true, "profileImage", "titleImage", 0, "certificateNationalHealth", "carnetImage", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), Integer.valueOf(0), Boolean.TRUE, new StatsDto(0, 0, 0), Arrays.<RoleDto>asList(new RoleDto("id", "name")))), result);
    }

    @Test
    void testGetAllDisabled() {
        when(userRepository.findAllByEnabledFalse()).thenReturn(Arrays.<UserDocument>asList(new UserDocument("id", "email", "password", Arrays.<RoleDocument>asList(null), null, null, Boolean.TRUE, Integer.valueOf(0), new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), "resetToken")));

        List<UserDto> result = userServiceImpl.getAllDisabled();
        Assertions.assertEquals(Arrays.<UserDto>asList(new UserDto("id", "email", "password", "rut", "firstName", "lastName", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), new CommuneDto("value", "label", new RegionDto("value", "label")), new RegionDto("value", "label"), true, "address", "aboutMe", true, "profileImage", "titleImage", 0, "certificateNationalHealth", "carnetImage", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), Integer.valueOf(0), Boolean.TRUE, new StatsDto(0, 0, 0), Arrays.<RoleDto>asList(new RoleDto("id", "name")))), result);
    }

    @Test
    void testGetOwnProfile() {
        when(userRepository.findByEmailIgnoreCase(anyString())).thenReturn(null);

        UserDto result = userServiceImpl.getOwnProfile();
        Assertions.assertEquals(new UserDto("id", "email", "password", "rut", "firstName", "lastName", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), new CommuneDto("value", "label", new RegionDto("value", "label")), new RegionDto("value", "label"), true, "address", "aboutMe", true, "profileImage", "titleImage", 0, "certificateNationalHealth", "carnetImage", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), Integer.valueOf(0), Boolean.TRUE, new StatsDto(0, 0, 0), Arrays.<RoleDto>asList(new RoleDto("id", "name"))), result);
    }

    @Test
    void testGetAllByRole() {
        when(userRepository.findById(anyString())).thenReturn(null);
        when(userRepository.findAllByRole(any())).thenReturn(Arrays.<UserDocument>asList(new UserDocument("id", "email", "password", Arrays.<RoleDocument>asList(null), null, null, Boolean.TRUE, Integer.valueOf(0), new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), "resetToken")));
        when(feedbackRepository.findAllByTo(any())).thenReturn(Arrays.<FeedbackDocument>asList(new FeedbackDocument("id", new UserDocument("id", "email", "password", Arrays.<RoleDocument>asList(null), null, null, Boolean.TRUE, Integer.valueOf(0), new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), "resetToken"), new UserDocument("id", "email", "password", Arrays.<RoleDocument>asList(null), null, null, Boolean.TRUE, Integer.valueOf(0), new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), "resetToken"), "comment", true, "date", 0)));
        when(chatService.getMessagesToUser(anyString(), anyBoolean())).thenReturn(Arrays.<MessageOutboundDto>asList(new MessageOutboundDto("id", "text", "date", null)));

        List<UserDto> result = userServiceImpl.getAllByRole("role");
        Assertions.assertEquals(Arrays.<UserDto>asList(new UserDto("id", "email", "password", "rut", "firstName", "lastName", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), new CommuneDto("value", "label", new RegionDto("value", "label")), new RegionDto("value", "label"), true, "address", "aboutMe", true, "profileImage", "titleImage", 0, "certificateNationalHealth", "carnetImage", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), Integer.valueOf(0), Boolean.TRUE, new StatsDto(0, 0, 0), Arrays.<RoleDto>asList(new RoleDto("id", "name")))), result);
    }

    @Test
    void testGetAllByRegionId() {
        when(userRepository.findAllByRoleAndRegion(any(), any())).thenReturn(Arrays.<UserDocument>asList(new UserDocument("id", "email", "password", Arrays.<RoleDocument>asList(null), null, null, Boolean.TRUE, Integer.valueOf(0), new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), "resetToken")));

        List<UserDto> result = userServiceImpl.getAllByRegionId("role", "regionId");
        Assertions.assertEquals(Arrays.<UserDto>asList(new UserDto("id", "email", "password", "rut", "firstName", "lastName", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), new CommuneDto("value", "label", new RegionDto("value", "label")), new RegionDto("value", "label"), true, "address", "aboutMe", true, "profileImage", "titleImage", 0, "certificateNationalHealth", "carnetImage", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), Integer.valueOf(0), Boolean.TRUE, new StatsDto(0, 0, 0), Arrays.<RoleDto>asList(new RoleDto("id", "name")))), result);
    }

    @Test
    void testGetAllByCommuneId() {
        when(userRepository.findAllByRoleAndCommune(any(), any())).thenReturn(Arrays.<UserDocument>asList(new UserDocument("id", "email", "password", Arrays.<RoleDocument>asList(null), null, null, Boolean.TRUE, Integer.valueOf(0), new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), "resetToken")));

        List<UserDto> result = userServiceImpl.getAllByCommuneId("role", "communeId");
        Assertions.assertEquals(Arrays.<UserDto>asList(new UserDto("id", "email", "password", "rut", "firstName", "lastName", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), new CommuneDto("value", "label", new RegionDto("value", "label")), new RegionDto("value", "label"), true, "address", "aboutMe", true, "profileImage", "titleImage", 0, "certificateNationalHealth", "carnetImage", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), Integer.valueOf(0), Boolean.TRUE, new StatsDto(0, 0, 0), Arrays.<RoleDto>asList(new RoleDto("id", "name")))), result);
    }

    @Test
    void testGetByEmail() {
        when(userRepository.findByEmailIgnoreCase(anyString())).thenReturn(null);

        UserDto result = userServiceImpl.getByEmail("email");
        Assertions.assertEquals(new UserDto("id", "email", "password", "rut", "firstName", "lastName", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), new CommuneDto("value", "label", new RegionDto("value", "label")), new RegionDto("value", "label"), true, "address", "aboutMe", true, "profileImage", "titleImage", 0, "certificateNationalHealth", "carnetImage", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), Integer.valueOf(0), Boolean.TRUE, new StatsDto(0, 0, 0), Arrays.<RoleDto>asList(new RoleDto("id", "name"))), result);
    }

    @Test
    void testGetByName() {
        when(userRepository.findByFirstNameAndLastName(anyString(), anyString())).thenReturn(null);

        List<UserDto> result = userServiceImpl.getByName("firstName", "lastName");
        Assertions.assertEquals(Arrays.<UserDto>asList(new UserDto("id", "email", "password", "rut", "firstName", "lastName", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), new CommuneDto("value", "label", new RegionDto("value", "label")), new RegionDto("value", "label"), true, "address", "aboutMe", true, "profileImage", "titleImage", 0, "certificateNationalHealth", "carnetImage", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), Integer.valueOf(0), Boolean.TRUE, new StatsDto(0, 0, 0), Arrays.<RoleDto>asList(new RoleDto("id", "name")))), result);
    }

    @Test
    void testGetById() {
        when(userRepository.findById(anyString())).thenReturn(null);

        UserDto result = userServiceImpl.getById("id");
        Assertions.assertEquals(new UserDto("id", "email", "password", "rut", "firstName", "lastName", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), new CommuneDto("value", "label", new RegionDto("value", "label")), new RegionDto("value", "label"), true, "address", "aboutMe", true, "profileImage", "titleImage", 0, "certificateNationalHealth", "carnetImage", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), Integer.valueOf(0), Boolean.TRUE, new StatsDto(0, 0, 0), Arrays.<RoleDto>asList(new RoleDto("id", "name"))), result);
    }

    @Test
    void testGetByRut() {
        UserDto result = userServiceImpl.getByRut("rut");
        Assertions.assertEquals(new UserDto("id", "email", "password", "rut", "firstName", "lastName", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), new CommuneDto("value", "label", new RegionDto("value", "label")), new RegionDto("value", "label"), true, "address", "aboutMe", true, "profileImage", "titleImage", 0, "certificateNationalHealth", "carnetImage", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), Integer.valueOf(0), Boolean.TRUE, new StatsDto(0, 0, 0), Arrays.<RoleDto>asList(new RoleDto("id", "name"))), result);
    }

    @Test
    void testGetUserImage() {
        when(userRepository.findById(anyString())).thenReturn(null);

        ContentDto result = userServiceImpl.getUserImage("id");
        Assertions.assertEquals(new ContentDto("contentType", "content"), result);
    }

    @Test
    void testGetStats() {
        when(userRepository.findById(anyString())).thenReturn(null);
        when(feedbackRepository.findAllByTo(any())).thenReturn(Arrays.<FeedbackDocument>asList(new FeedbackDocument("id", new UserDocument("id", "email", "password", Arrays.<RoleDocument>asList(null), null, null, Boolean.TRUE, Integer.valueOf(0), new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), "resetToken"), new UserDocument("id", "email", "password", Arrays.<RoleDocument>asList(null), null, null, Boolean.TRUE, Integer.valueOf(0), new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), "resetToken"), "comment", true, "date", 0)));
        when(chatService.getMessagesToUser(anyString(), anyBoolean())).thenReturn(Arrays.<MessageOutboundDto>asList(new MessageOutboundDto("id", "text", "date", null)));

        StatsDto result = userServiceImpl.getStats("userId");
        Assertions.assertEquals(new StatsDto(0, 0, 0), result);
    }

    @Test
    void testEdit() {
        when(userRepository.findById(anyString())).thenReturn(null);
        when(userRepository.save(any())).thenReturn(new UserDocument("id", "email", "password", Arrays.<RoleDocument>asList(null), new UserDetailsDocument("id", "rut", "firstName", "lastName", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), new CommuneDocument("id", "name", new RegionDocument("id", "name", "ordinal")), "aboutMe", new NationalityDocument("id", "name"), true, "address", "profileImageURI"), new ParamedicDetailsDocument("id", "titleImageURI", 0, "certificateNationalHealthURI", "carnetImageURI"), Boolean.TRUE, Integer.valueOf(0), new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), "resetToken"));

        UserDto result = userServiceImpl.edit("userId", new UserDto("id", "email", "password", "rut", "firstName", "lastName", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), new CommuneDto("value", "label", new RegionDto("value", "label")), new RegionDto("value", "label"), true, "address", "aboutMe", true, "profileImage", "titleImage", 0, "certificateNationalHealth", "carnetImage", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), Integer.valueOf(0), Boolean.TRUE, new StatsDto(0, 0, 0), Arrays.<RoleDto>asList(new RoleDto("id", "name"))));
        Assertions.assertEquals(new UserDto("id", "email", "password", "rut", "firstName", "lastName", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), new CommuneDto("value", "label", new RegionDto("value", "label")), new RegionDto("value", "label"), true, "address", "aboutMe", true, "profileImage", "titleImage", 0, "certificateNationalHealth", "carnetImage", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), Integer.valueOf(0), Boolean.TRUE, new StatsDto(0, 0, 0), Arrays.<RoleDto>asList(new RoleDto("id", "name"))), result);
    }

    @Test
    void testSave() {
        when(userRepository.save(any())).thenReturn(new UserDocument("id", "email", "password", Arrays.<RoleDocument>asList(null), new UserDetailsDocument("id", "rut", "firstName", "lastName", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), new CommuneDocument("id", "name", new RegionDocument("id", "name", "ordinal")), "aboutMe", new NationalityDocument("id", "name"), true, "address", "profileImageURI"), new ParamedicDetailsDocument("id", "titleImageURI", 0, "certificateNationalHealthURI", "carnetImageURI"), Boolean.TRUE, Integer.valueOf(0), new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), "resetToken"));

        UserDto result = userServiceImpl.save(new UserDto("id", "email", "password", "rut", "firstName", "lastName", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), new CommuneDto("value", "label", new RegionDto("value", "label")), new RegionDto("value", "label"), true, "address", "aboutMe", true, "profileImage", "titleImage", 0, "certificateNationalHealth", "carnetImage", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), Integer.valueOf(0), Boolean.TRUE, new StatsDto(0, 0, 0), Arrays.<RoleDto>asList(new RoleDto("id", "name"))));
        Assertions.assertEquals(new UserDto("id", "email", "password", "rut", "firstName", "lastName", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), new CommuneDto("value", "label", new RegionDto("value", "label")), new RegionDto("value", "label"), true, "address", "aboutMe", true, "profileImage", "titleImage", 0, "certificateNationalHealth", "carnetImage", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), Integer.valueOf(0), Boolean.TRUE, new StatsDto(0, 0, 0), Arrays.<RoleDto>asList(new RoleDto("id", "name"))), result);
    }

    @Test
    void testDeleteById() {
        when(userRepository.deleteById(anyString())).thenReturn(true);

        GenericResponseDto result = userServiceImpl.deleteById("id");
        Assertions.assertEquals(new GenericResponseDto("message", Arrays.<String>asList("String")), result);
    }

    @Test
    void testEnableUser() {
        when(userRepository.findById(anyString())).thenReturn(null);
        when(userRepository.save(any())).thenReturn(new UserDocument("id", "email", "password", Arrays.<RoleDocument>asList(null), new UserDetailsDocument("id", "rut", "firstName", "lastName", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), new CommuneDocument("id", "name", new RegionDocument("id", "name", "ordinal")), "aboutMe", new NationalityDocument("id", "name"), true, "address", "profileImageURI"), new ParamedicDetailsDocument("id", "titleImageURI", 0, "certificateNationalHealthURI", "carnetImageURI"), Boolean.TRUE, Integer.valueOf(0), new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), "resetToken"));

        GenericResponseDto result = userServiceImpl.enableUser("userId", true);
        Assertions.assertEquals(new GenericResponseDto("message", Arrays.<String>asList("String")), result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme