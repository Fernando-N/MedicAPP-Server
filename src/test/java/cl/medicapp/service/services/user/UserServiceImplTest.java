package cl.medicapp.service.services.user;

import cl.medicapp.service.document.CommuneDocument;
import cl.medicapp.service.document.FeedbackDocument;
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
import cl.medicapp.service.holder.DocumentsHolder;
import cl.medicapp.service.repository.commune.CommuneRepository;
import cl.medicapp.service.repository.feedback.FeedbackRepository;
import cl.medicapp.service.repository.region.RegionRepository;
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
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyBoolean;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CommuneRepository communeRepository;

    @Mock
    private RegionRepository regionRepository;

    @Mock
    private FeedbackRepository feedbackRepository;

    @Mock
    private ChatService chatService;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        DocumentsHolder.getInstance().setRoleDocumentList(Collections.singletonList(RoleDocument.builder().name("PARAMEDIC").build()));
        DocumentsHolder.getInstance().setCommuneDocumentList(Collections.singletonList(CommuneDocument.builder().id("ID").region(RegionDocument.builder().id("ID").name("REGION").build()).name("COMUNA").build()));
        DocumentsHolder.getInstance().setRegionDocumentList(Collections.singletonList(RegionDocument.builder().id("ID").name("REGION").build()));
    }

    @Test
    void testGetAll() {
        RoleDocument roleDocument = RoleDocument.builder().name("ADMIN").build();
        RegionDocument regionDocument = RegionDocument.builder().build();
        CommuneDocument communeDocument = CommuneDocument.builder().region(regionDocument).build();
        UserDetailsDocument userDetailsDocument = UserDetailsDocument.builder().commune(communeDocument).build();
        UserDocument userDocument = UserDocument.builder().roleEntities(Collections.singletonList(roleDocument)).userDetails(userDetailsDocument).build();
        when(userRepository.findAll()).thenReturn(Collections.singletonList(userDocument));

        List<UserDto> result = userServiceImpl.getAll();
        assertNotNull(result);
    }

    @Test
    void testGetAllDisabled() {
        List<UserDto> result = userServiceImpl.getAllDisabled();
        assertNotNull(result);
    }

    @Test
    void testGetAllByRole() {
        RoleDocument roleDocument = RoleDocument.builder().name("PARAMEDIC").build();
        RegionDocument regionDocument = RegionDocument.builder().build();
        CommuneDocument communeDocument = CommuneDocument.builder().region(regionDocument).build();
        UserDetailsDocument userDetailsDocument = UserDetailsDocument.builder().commune(communeDocument).build();
        UserDocument userDocument = UserDocument.builder().roleEntities(Collections.singletonList(roleDocument)).userDetails(userDetailsDocument).build();

        when(userRepository.findById(anyString())).thenReturn(Optional.of(userDocument));
        when(chatService.getMessagesToUser(anyString(), anyBoolean())).thenReturn(Collections.singletonList(new MessageOutboundDto("id", "text", "date", null)));

        List<UserDto> result = userServiceImpl.getAllByRole("PARAMEDIC");
        assertNotNull(result);
    }

    @Test
    void testGetAllByRoleAndRegionId() {
        RoleDocument roleDocument = RoleDocument.builder().name("ADMIN").build();
        RegionDocument regionDocument = RegionDocument.builder().build();
        CommuneDocument communeDocument = CommuneDocument.builder().region(regionDocument).build();
        UserDetailsDocument userDetailsDocument = UserDetailsDocument.builder().commune(communeDocument).build();
        UserDocument userDocument = UserDocument.builder().roleEntities(Collections.singletonList(roleDocument)).userDetails(userDetailsDocument).build();
        when(userRepository.findAllByRoleAndRegion(any(), any())).thenReturn(Collections.singletonList(userDocument));

        List<UserDto> result = userServiceImpl.getAllByRoleAndRegionId("PARAMEDIC", "ID");
        assertNotNull(result);
    }

    @Test
    void testGetAllByRoleAndCommuneId() {
        RoleDocument roleDocument = RoleDocument.builder().name("PARAMEDIC").build();
        RegionDocument regionDocument = RegionDocument.builder().id("ID").build();
        CommuneDocument communeDocument = CommuneDocument.builder().id("ID").region(regionDocument).build();
        UserDetailsDocument userDetailsDocument = UserDetailsDocument.builder().commune(communeDocument).build();
        UserDocument userDocument = UserDocument.builder().roleEntities(Collections.singletonList(roleDocument)).userDetails(userDetailsDocument).build();

        when(userRepository.findAllByRoleAndCommune(any(), any())).thenReturn(Collections.singletonList(userDocument));

        List<UserDto> result = userServiceImpl.getAllByRoleAndCommuneId("PARAMEDIC", "ID");
        assertNotNull(result);
    }

    @Test
    void testGetByEmail() {
        RoleDocument roleDocument = RoleDocument.builder().name("PARAMEDIC").build();
        RegionDocument regionDocument = RegionDocument.builder().build();
        CommuneDocument communeDocument = CommuneDocument.builder().region(regionDocument).build();
        UserDetailsDocument userDetailsDocument = UserDetailsDocument.builder().commune(communeDocument).build();
        UserDocument userDocument = UserDocument.builder().roleEntities(Collections.singletonList(roleDocument)).userDetails(userDetailsDocument).build();

        when(userRepository.findByEmailIgnoreCase(anyString())).thenReturn(Optional.of(userDocument));

        UserDto result = userServiceImpl.getByEmail("email");
        assertNotNull(result);
    }

    @Test
    void testGetByName() {

        List<UserDto> result = userServiceImpl.getByName("firstName", "lastName");
        assertNotNull(result);
    }

    @Test
    void testGetById() {
        RoleDocument roleDocument = RoleDocument.builder().name("ADMIN").build();
        RegionDocument regionDocument = RegionDocument.builder().build();
        CommuneDocument communeDocument = CommuneDocument.builder().region(regionDocument).build();
        UserDetailsDocument userDetailsDocument = UserDetailsDocument.builder().commune(communeDocument).build();
        UserDocument userDocument = UserDocument.builder().roleEntities(Collections.singletonList(roleDocument)).userDetails(userDetailsDocument).build();
        when(userRepository.findById(anyString())).thenReturn(Optional.of(userDocument));

        UserDto result = userServiceImpl.getById("id");
        assertNotNull(result);
    }

    @Test
    void testGetByRut() {
        RoleDocument roleDocument = RoleDocument.builder().name("ADMIN").build();
        RegionDocument regionDocument = RegionDocument.builder().build();
        CommuneDocument communeDocument = CommuneDocument.builder().region(regionDocument).build();
        UserDetailsDocument userDetailsDocument = UserDetailsDocument.builder().commune(communeDocument).build();
        UserDocument userDocument = UserDocument.builder().roleEntities(Collections.singletonList(roleDocument)).userDetails(userDetailsDocument).build();
        when(userRepository.findByRut(anyString())).thenReturn(Optional.of(userDocument));

        UserDto result = userServiceImpl.getByRut("rut");
        assertNotNull(result);
    }

    @Test
    void testGetUserImage() {
        RoleDocument roleDocument = RoleDocument.builder().name("ADMIN").build();
        RegionDocument regionDocument = RegionDocument.builder().build();
        CommuneDocument communeDocument = CommuneDocument.builder().region(regionDocument).build();
        UserDetailsDocument userDetailsDocument = UserDetailsDocument.builder().commune(communeDocument).build();
        UserDocument userDocument = UserDocument.builder().roleEntities(Collections.singletonList(roleDocument)).userDetails(userDetailsDocument).build();
        when(userRepository.findById(anyString())).thenReturn(Optional.of(userDocument));

        ContentDto result = userServiceImpl.getUserImage("id");
        assertNotNull(result);
    }

    @Test
    void testGetStats() {
        RoleDocument roleDocument = RoleDocument.builder().name("PARAMEDIC").build();
        RegionDocument regionDocument = RegionDocument.builder().build();
        CommuneDocument communeDocument = CommuneDocument.builder().region(regionDocument).build();
        UserDetailsDocument userDetailsDocument = UserDetailsDocument.builder().commune(communeDocument).build();
        UserDocument userDocument = UserDocument.builder().roleEntities(Collections.singletonList(roleDocument)).userDetails(userDetailsDocument).build();
        when(userRepository.findById(anyString())).thenReturn(Optional.of(userDocument));
        when(chatService.getMessagesToUser(anyString(), anyBoolean())).thenReturn(Collections.singletonList(new MessageOutboundDto("id", "text", "date", null)));

        StatsDto result = userServiceImpl.getStats("userId");
        assertNotNull(result);
    }

    @Test
    void testEdit() {
        RoleDocument roleDocument = RoleDocument.builder().name("PARAMEDICC").build();
        RegionDocument regionDocument = RegionDocument.builder().build();
        CommuneDocument communeDocument = CommuneDocument.builder().region(regionDocument).build();
        UserDetailsDocument userDetailsDocument = UserDetailsDocument.builder().commune(communeDocument).build();
        UserDocument userDocument = UserDocument.builder().roleEntities(Collections.singletonList(roleDocument)).userDetails(userDetailsDocument).build();
        when(userRepository.findById(anyString())).thenReturn(Optional.of(userDocument));
        when(communeRepository.findById(anyString())).thenReturn(Optional.of(communeDocument));
        when(regionRepository.findById(anyString())).thenReturn(Optional.of(regionDocument));
        when(userRepository.save(any())).thenReturn(userDocument);
        UserDto result = userServiceImpl.edit("userId", new UserDto("id", "email", "password", "rut", "firstName", "lastName", new GregorianCalendar(2020, Calendar.JULY, 21, 0, 29).getTime(), new CommuneDto("id", "name", new RegionDto("id", "label")), new RegionDto("id", "label"), true, "address", "aboutMe", false, "profileImage", "titleImage", 0, "certificateNationalHealth", "carnetImage", new GregorianCalendar(2020, Calendar.JULY, 21, 0, 29).getTime(), Integer.valueOf(0), Boolean.TRUE, new StatsDto(0, 0, 0), Arrays.<RoleDto>asList(new RoleDto("id", "name"))));
        assertNotNull(result);
    }

    @Test
    void testSave() {

        UserDto result = userServiceImpl.save(new UserDto("id", "email", "password", "rut", "firstName", "lastName", new GregorianCalendar(2020, Calendar.JULY, 21, 0, 29).getTime(), new CommuneDto("id", "name", new RegionDto("id", "label")), new RegionDto("id", "label"), true, "address", "aboutMe", true, "profileImage", "titleImage", 0, "certificateNationalHealth", "carnetImage", new GregorianCalendar(2020, Calendar.JULY, 21, 0, 29).getTime(), Integer.valueOf(0), Boolean.TRUE, new StatsDto(0, 0, 0), Arrays.<RoleDto>asList(new RoleDto("id", "name"))));
        assertNotNull(result);
    }

    @Test
    void testDeleteById() {
        when(userRepository.deleteById(anyString())).thenReturn(true);

        GenericResponseDto result = userServiceImpl.deleteById("id");
        assertNotNull(result);
    }

    @Test
    void testEnableUser() {
        RoleDocument roleDocument = RoleDocument.builder().name("ADMIN").build();
        RegionDocument regionDocument = RegionDocument.builder().build();
        CommuneDocument communeDocument = CommuneDocument.builder().region(regionDocument).build();
        UserDetailsDocument userDetailsDocument = UserDetailsDocument.builder().commune(communeDocument).build();
        UserDocument userDocument = UserDocument.builder().roleEntities(Collections.singletonList(roleDocument)).userDetails(userDetailsDocument).build();
        when(userRepository.findById(anyString())).thenReturn(Optional.of(userDocument));

        GenericResponseDto result = userServiceImpl.enableUser("userId", true);
        assertNotNull(result);
    }
}
