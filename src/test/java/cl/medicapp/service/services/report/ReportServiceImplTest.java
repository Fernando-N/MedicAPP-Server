package cl.medicapp.service.services.report;

import cl.medicapp.service.document.ReportDocument;
import cl.medicapp.service.dto.CommuneDto;
import cl.medicapp.service.dto.GenericResponseDto;
import cl.medicapp.service.dto.RegionDto;
import cl.medicapp.service.dto.ReportDto;
import cl.medicapp.service.dto.RoleDto;
import cl.medicapp.service.dto.StatsDto;
import cl.medicapp.service.dto.UserDto;
import cl.medicapp.service.repository.report.ReportRepository;
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

class ReportServiceImplTest {
    @Mock
    ReportRepository reportRepository;
    @Mock
    UserRepository userRepository;
    @InjectMocks
    ReportServiceImpl reportServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAll() {
        when(reportRepository.findAll()).thenReturn(Arrays.<ReportDocument>asList(new ReportDocument("id", null, null, "message", "date", true)));

        List<ReportDto> result = reportServiceImpl.getAll();
        Assertions.assertEquals(Arrays.<ReportDto>asList(new ReportDto("id", new UserDto("id", "email", "password", "rut", "firstName", "lastName", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), new CommuneDto("value", "label", new RegionDto("value", "label")), new RegionDto("value", "label"), true, "address", "aboutMe", true, "profileImage", "titleImage", 0, "certificateNationalHealth", "carnetImage", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), Integer.valueOf(0), Boolean.TRUE, new StatsDto(0, 0, 0), Arrays.<RoleDto>asList(new RoleDto("id", "name"))), new UserDto("id", "email", "password", "rut", "firstName", "lastName", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), new CommuneDto("value", "label", new RegionDto("value", "label")), new RegionDto("value", "label"), true, "address", "aboutMe", true, "profileImage", "titleImage", 0, "certificateNationalHealth", "carnetImage", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), Integer.valueOf(0), Boolean.TRUE, new StatsDto(0, 0, 0), Arrays.<RoleDto>asList(new RoleDto("id", "name"))), "message", "date", true)), result);
    }

    @Test
    void testGetByFromUserId() {
        when(reportRepository.findByFrom(any())).thenReturn(Arrays.<ReportDocument>asList(new ReportDocument("id", null, null, "message", "date", true)));
        when(userRepository.findById(anyString())).thenReturn(null);

        List<ReportDto> result = reportServiceImpl.getByFromUserId("id");
        Assertions.assertEquals(Arrays.<ReportDto>asList(new ReportDto("id", new UserDto("id", "email", "password", "rut", "firstName", "lastName", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), new CommuneDto("value", "label", new RegionDto("value", "label")), new RegionDto("value", "label"), true, "address", "aboutMe", true, "profileImage", "titleImage", 0, "certificateNationalHealth", "carnetImage", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), Integer.valueOf(0), Boolean.TRUE, new StatsDto(0, 0, 0), Arrays.<RoleDto>asList(new RoleDto("id", "name"))), new UserDto("id", "email", "password", "rut", "firstName", "lastName", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), new CommuneDto("value", "label", new RegionDto("value", "label")), new RegionDto("value", "label"), true, "address", "aboutMe", true, "profileImage", "titleImage", 0, "certificateNationalHealth", "carnetImage", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), Integer.valueOf(0), Boolean.TRUE, new StatsDto(0, 0, 0), Arrays.<RoleDto>asList(new RoleDto("id", "name"))), "message", "date", true)), result);
    }

    @Test
    void testGetByToUserId() {
        when(reportRepository.findByTo(any())).thenReturn(Arrays.<ReportDocument>asList(new ReportDocument("id", null, null, "message", "date", true)));
        when(userRepository.findById(anyString())).thenReturn(null);

        List<ReportDto> result = reportServiceImpl.getByToUserId("id");
        Assertions.assertEquals(Arrays.<ReportDto>asList(new ReportDto("id", new UserDto("id", "email", "password", "rut", "firstName", "lastName", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), new CommuneDto("value", "label", new RegionDto("value", "label")), new RegionDto("value", "label"), true, "address", "aboutMe", true, "profileImage", "titleImage", 0, "certificateNationalHealth", "carnetImage", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), Integer.valueOf(0), Boolean.TRUE, new StatsDto(0, 0, 0), Arrays.<RoleDto>asList(new RoleDto("id", "name"))), new UserDto("id", "email", "password", "rut", "firstName", "lastName", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), new CommuneDto("value", "label", new RegionDto("value", "label")), new RegionDto("value", "label"), true, "address", "aboutMe", true, "profileImage", "titleImage", 0, "certificateNationalHealth", "carnetImage", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), Integer.valueOf(0), Boolean.TRUE, new StatsDto(0, 0, 0), Arrays.<RoleDto>asList(new RoleDto("id", "name"))), "message", "date", true)), result);
    }

    @Test
    void testSave() {
        when(userRepository.findById(anyString())).thenReturn(null);

        ReportDto result = reportServiceImpl.save(new ReportDto("id", new UserDto("id", "email", "password", "rut", "firstName", "lastName", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), new CommuneDto("value", "label", new RegionDto("value", "label")), new RegionDto("value", "label"), true, "address", "aboutMe", true, "profileImage", "titleImage", 0, "certificateNationalHealth", "carnetImage", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), Integer.valueOf(0), Boolean.TRUE, new StatsDto(0, 0, 0), Arrays.<RoleDto>asList(new RoleDto("id", "name"))), new UserDto("id", "email", "password", "rut", "firstName", "lastName", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), new CommuneDto("value", "label", new RegionDto("value", "label")), new RegionDto("value", "label"), true, "address", "aboutMe", true, "profileImage", "titleImage", 0, "certificateNationalHealth", "carnetImage", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), Integer.valueOf(0), Boolean.TRUE, new StatsDto(0, 0, 0), Arrays.<RoleDto>asList(new RoleDto("id", "name"))), "message", "date", true));
        Assertions.assertEquals(new ReportDto("id", new UserDto("id", "email", "password", "rut", "firstName", "lastName", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), new CommuneDto("value", "label", new RegionDto("value", "label")), new RegionDto("value", "label"), true, "address", "aboutMe", true, "profileImage", "titleImage", 0, "certificateNationalHealth", "carnetImage", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), Integer.valueOf(0), Boolean.TRUE, new StatsDto(0, 0, 0), Arrays.<RoleDto>asList(new RoleDto("id", "name"))), new UserDto("id", "email", "password", "rut", "firstName", "lastName", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), new CommuneDto("value", "label", new RegionDto("value", "label")), new RegionDto("value", "label"), true, "address", "aboutMe", true, "profileImage", "titleImage", 0, "certificateNationalHealth", "carnetImage", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), Integer.valueOf(0), Boolean.TRUE, new StatsDto(0, 0, 0), Arrays.<RoleDto>asList(new RoleDto("id", "name"))), "message", "date", true), result);
    }

    @Test
    void testUpdate() {
        ReportDto result = reportServiceImpl.update("idReport", new ReportDto("id", new UserDto("id", "email", "password", "rut", "firstName", "lastName", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), new CommuneDto("value", "label", new RegionDto("value", "label")), new RegionDto("value", "label"), true, "address", "aboutMe", true, "profileImage", "titleImage", 0, "certificateNationalHealth", "carnetImage", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), Integer.valueOf(0), Boolean.TRUE, new StatsDto(0, 0, 0), Arrays.<RoleDto>asList(new RoleDto("id", "name"))), new UserDto("id", "email", "password", "rut", "firstName", "lastName", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), new CommuneDto("value", "label", new RegionDto("value", "label")), new RegionDto("value", "label"), true, "address", "aboutMe", true, "profileImage", "titleImage", 0, "certificateNationalHealth", "carnetImage", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), Integer.valueOf(0), Boolean.TRUE, new StatsDto(0, 0, 0), Arrays.<RoleDto>asList(new RoleDto("id", "name"))), "message", "date", true));
        Assertions.assertEquals(new ReportDto("id", new UserDto("id", "email", "password", "rut", "firstName", "lastName", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), new CommuneDto("value", "label", new RegionDto("value", "label")), new RegionDto("value", "label"), true, "address", "aboutMe", true, "profileImage", "titleImage", 0, "certificateNationalHealth", "carnetImage", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), Integer.valueOf(0), Boolean.TRUE, new StatsDto(0, 0, 0), Arrays.<RoleDto>asList(new RoleDto("id", "name"))), new UserDto("id", "email", "password", "rut", "firstName", "lastName", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), new CommuneDto("value", "label", new RegionDto("value", "label")), new RegionDto("value", "label"), true, "address", "aboutMe", true, "profileImage", "titleImage", 0, "certificateNationalHealth", "carnetImage", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 25).getTime(), Integer.valueOf(0), Boolean.TRUE, new StatsDto(0, 0, 0), Arrays.<RoleDto>asList(new RoleDto("id", "name"))), "message", "date", true), result);
    }

    @Test
    void testDeleteById() {
        GenericResponseDto result = reportServiceImpl.deleteById("name");
        Assertions.assertEquals(new GenericResponseDto("message", Arrays.<String>asList("String")), result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme