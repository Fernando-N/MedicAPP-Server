package cl.medicapp.service.services.feedback;

import cl.medicapp.service.document.FeedbackDocument;
import cl.medicapp.service.dto.CommuneDto;
import cl.medicapp.service.dto.FeedbackDto;
import cl.medicapp.service.dto.GenericResponseDto;
import cl.medicapp.service.dto.RegionDto;
import cl.medicapp.service.dto.RoleDto;
import cl.medicapp.service.dto.StatsDto;
import cl.medicapp.service.dto.UserDto;
import cl.medicapp.service.repository.feedback.FeedbackRepository;
import cl.medicapp.service.repository.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static org.mockito.Mockito.*;

class FeedbackServiceImplTest {
    @Mock
    FeedbackRepository feedbackRepository;
    @Mock
    UserRepository userRepository;
    @InjectMocks
    FeedbackServiceImpl feedbackServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAll() {
        List<FeedbackDto> result = feedbackServiceImpl.getAll();
        Assertions.assertEquals(Arrays.<FeedbackDto>asList(new FeedbackDto("id", new UserDto("id", "email", "password", "rut", "firstName", "lastName", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 24).getTime(), new CommuneDto("value", "label", new RegionDto("value", "label")), new RegionDto("value", "label"), true, "address", "aboutMe", true, "profileImage", "titleImage", 0, "certificateNationalHealth", "carnetImage", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 24).getTime(), Integer.valueOf(0), Boolean.TRUE, new StatsDto(0, 0, 0), Arrays.<RoleDto>asList(new RoleDto("id", "name"))), new UserDto("id", "email", "password", "rut", "firstName", "lastName", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 24).getTime(), new CommuneDto("value", "label", new RegionDto("value", "label")), new RegionDto("value", "label"), true, "address", "aboutMe", true, "profileImage", "titleImage", 0, "certificateNationalHealth", "carnetImage", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 24).getTime(), Integer.valueOf(0), Boolean.TRUE, new StatsDto(0, 0, 0), Arrays.<RoleDto>asList(new RoleDto("id", "name"))), "comment", true, "date", 0)), result);
    }

    @Test
    void testGetAllByToUserId() {
        when(feedbackRepository.findAllByTo(any())).thenReturn(Arrays.<FeedbackDocument>asList(new FeedbackDocument("id", null, null, "comment", true, "date", 0)));
        when(userRepository.findById(anyString())).thenReturn(null);

        List<FeedbackDto> result = feedbackServiceImpl.getAllByToUserId("id");
        Assertions.assertEquals(Arrays.<FeedbackDto>asList(new FeedbackDto("id", new UserDto("id", "email", "password", "rut", "firstName", "lastName", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 24).getTime(), new CommuneDto("value", "label", new RegionDto("value", "label")), new RegionDto("value", "label"), true, "address", "aboutMe", true, "profileImage", "titleImage", 0, "certificateNationalHealth", "carnetImage", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 24).getTime(), Integer.valueOf(0), Boolean.TRUE, new StatsDto(0, 0, 0), Arrays.<RoleDto>asList(new RoleDto("id", "name"))), new UserDto("id", "email", "password", "rut", "firstName", "lastName", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 24).getTime(), new CommuneDto("value", "label", new RegionDto("value", "label")), new RegionDto("value", "label"), true, "address", "aboutMe", true, "profileImage", "titleImage", 0, "certificateNationalHealth", "carnetImage", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 24).getTime(), Integer.valueOf(0), Boolean.TRUE, new StatsDto(0, 0, 0), Arrays.<RoleDto>asList(new RoleDto("id", "name"))), "comment", true, "date", 0)), result);
    }

    @Test
    void testGetAllByFromUserId() {
        when(feedbackRepository.findAllByFrom(any())).thenReturn(Arrays.<FeedbackDocument>asList(new FeedbackDocument("id", null, null, "comment", true, "date", 0)));
        when(userRepository.findById(anyString())).thenReturn(null);

        List<FeedbackDto> result = feedbackServiceImpl.getAllByFromUserId("id");
        Assertions.assertEquals(Arrays.<FeedbackDto>asList(new FeedbackDto("id", new UserDto("id", "email", "password", "rut", "firstName", "lastName", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 24).getTime(), new CommuneDto("value", "label", new RegionDto("value", "label")), new RegionDto("value", "label"), true, "address", "aboutMe", true, "profileImage", "titleImage", 0, "certificateNationalHealth", "carnetImage", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 24).getTime(), Integer.valueOf(0), Boolean.TRUE, new StatsDto(0, 0, 0), Arrays.<RoleDto>asList(new RoleDto("id", "name"))), new UserDto("id", "email", "password", "rut", "firstName", "lastName", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 24).getTime(), new CommuneDto("value", "label", new RegionDto("value", "label")), new RegionDto("value", "label"), true, "address", "aboutMe", true, "profileImage", "titleImage", 0, "certificateNationalHealth", "carnetImage", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 24).getTime(), Integer.valueOf(0), Boolean.TRUE, new StatsDto(0, 0, 0), Arrays.<RoleDto>asList(new RoleDto("id", "name"))), "comment", true, "date", 0)), result);
    }

    @Test
    void testSave() {
        FeedbackDto result = feedbackServiceImpl.save(new FeedbackDto("id", new UserDto("id", "email", "password", "rut", "firstName", "lastName", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 24).getTime(), new CommuneDto("value", "label", new RegionDto("value", "label")), new RegionDto("value", "label"), true, "address", "aboutMe", true, "profileImage", "titleImage", 0, "certificateNationalHealth", "carnetImage", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 24).getTime(), Integer.valueOf(0), Boolean.TRUE, new StatsDto(0, 0, 0), Arrays.<RoleDto>asList(new RoleDto("id", "name"))), new UserDto("id", "email", "password", "rut", "firstName", "lastName", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 24).getTime(), new CommuneDto("value", "label", new RegionDto("value", "label")), new RegionDto("value", "label"), true, "address", "aboutMe", true, "profileImage", "titleImage", 0, "certificateNationalHealth", "carnetImage", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 24).getTime(), Integer.valueOf(0), Boolean.TRUE, new StatsDto(0, 0, 0), Arrays.<RoleDto>asList(new RoleDto("id", "name"))), "comment", true, "date", 0));
        Assertions.assertEquals(new FeedbackDto("id", new UserDto("id", "email", "password", "rut", "firstName", "lastName", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 24).getTime(), new CommuneDto("value", "label", new RegionDto("value", "label")), new RegionDto("value", "label"), true, "address", "aboutMe", true, "profileImage", "titleImage", 0, "certificateNationalHealth", "carnetImage", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 24).getTime(), Integer.valueOf(0), Boolean.TRUE, new StatsDto(0, 0, 0), Arrays.<RoleDto>asList(new RoleDto("id", "name"))), new UserDto("id", "email", "password", "rut", "firstName", "lastName", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 24).getTime(), new CommuneDto("value", "label", new RegionDto("value", "label")), new RegionDto("value", "label"), true, "address", "aboutMe", true, "profileImage", "titleImage", 0, "certificateNationalHealth", "carnetImage", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 24).getTime(), Integer.valueOf(0), Boolean.TRUE, new StatsDto(0, 0, 0), Arrays.<RoleDto>asList(new RoleDto("id", "name"))), "comment", true, "date", 0), result);
    }

    @Test
    void testDeleteById() {
        GenericResponseDto result = feedbackServiceImpl.deleteById("name");
        Assertions.assertEquals(new GenericResponseDto("message", Arrays.<String>asList("String")), result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme