package cl.medicapp.service.services.report;

import cl.medicapp.service.document.CommuneDocument;
import cl.medicapp.service.document.RegionDocument;
import cl.medicapp.service.document.ReportDocument;
import cl.medicapp.service.document.RoleDocument;
import cl.medicapp.service.document.UserDetailsDocument;
import cl.medicapp.service.document.UserDocument;
import cl.medicapp.service.dto.GenericResponseDto;
import cl.medicapp.service.dto.ReportDto;
import cl.medicapp.service.repository.report.ReportRepository;
import cl.medicapp.service.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

class ReportServiceImplTest {

    @Mock
    private ReportRepository reportRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ReportServiceImpl reportServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAll() {
        RegionDocument regionDocument = RegionDocument.builder().build();
        CommuneDocument communeDocument = CommuneDocument.builder().region(regionDocument).build();
        UserDetailsDocument userDetailsDocument = UserDetailsDocument.builder().commune(communeDocument).build();
        List<RoleDocument> roleDocuments = Collections.singletonList(RoleDocument.builder().name("ADMIN").build());
        UserDocument userDocument = UserDocument.builder().userDetails(userDetailsDocument).roleEntities(roleDocuments).build();
        ReportDocument reportDocument = ReportDocument.builder().to(userDocument).from(userDocument).build();
        when(reportRepository.findAll()).thenReturn(Collections.singletonList(reportDocument));

        List<ReportDto> result = reportServiceImpl.getAll();
        assertNotNull(result);
    }

    @Test
    void testGetByFromUserId() {
        RegionDocument regionDocument = RegionDocument.builder().build();
        CommuneDocument communeDocument = CommuneDocument.builder().region(regionDocument).build();
        UserDetailsDocument userDetailsDocument = UserDetailsDocument.builder().commune(communeDocument).build();
        List<RoleDocument> roleDocuments = Collections.singletonList(RoleDocument.builder().name("ADMIN").build());
        UserDocument userDocument = UserDocument.builder().userDetails(userDetailsDocument).roleEntities(roleDocuments).build();
        ReportDocument reportDocument = ReportDocument.builder().to(userDocument).from(userDocument).build();
        when(reportRepository.findByTo(any())).thenReturn(Collections.singletonList(reportDocument));
        when(reportRepository.findByFrom(any())).thenReturn(Collections.singletonList(reportDocument));
        when(userRepository.findById(anyString())).thenReturn(Optional.of(userDocument));

        List<ReportDto> result = reportServiceImpl.getByFromUserId("id");
        assertNotNull(result);
    }

    @Test
    void testGetByToUserId() {
        RegionDocument regionDocument = RegionDocument.builder().build();
        CommuneDocument communeDocument = CommuneDocument.builder().region(regionDocument).build();
        UserDetailsDocument userDetailsDocument = UserDetailsDocument.builder().commune(communeDocument).build();
        List<RoleDocument> roleDocuments = Collections.singletonList(RoleDocument.builder().name("ADMIN").build());
        UserDocument userDocument = UserDocument.builder().userDetails(userDetailsDocument).roleEntities(roleDocuments).build();
        ReportDocument reportDocument = ReportDocument.builder().to(userDocument).from(userDocument).build();
        when(reportRepository.findByTo(any())).thenReturn(Collections.singletonList(reportDocument));
        when(userRepository.findById(anyString())).thenReturn(Optional.of(UserDocument.builder().build()));

        List<ReportDto> result = reportServiceImpl.getByToUserId("id");
        assertNotNull(result);
    }

    //TODO TEST
//    @Test
//    void testUpdate() {
//        ReportDto result = reportServiceImpl.update("idReport", new ReportDto("id", "toUserId", new UserDto("id", "email", "password", "rut", "firstName", "lastName", new GregorianCalendar(2020, Calendar.JULY, 21, 0, 0).getTime(), new CommuneDto("id", "name", new RegionDto("id", "label")), new RegionDto("id", "label"), true, "address", "aboutMe", true, "profileImage", "titleImage", 0, "certificateNationalHealth", "carnetImage", new GregorianCalendar(2020, Calendar.JULY, 21, 0, 0).getTime(), Integer.valueOf(0), Boolean.TRUE, new StatsDto(0, 0, 0), Arrays.<RoleDto>asList(new RoleDto("id", "name"))), new UserDto("id", "email", "password", "rut", "firstName", "lastName", new GregorianCalendar(2020, Calendar.JULY, 21, 0, 0).getTime(), new CommuneDto("id", "name", new RegionDto("id", "label")), new RegionDto("id", "label"), true, "address", "aboutMe", true, "profileImage", "titleImage", 0, "certificateNationalHealth", "carnetImage", new GregorianCalendar(2020, Calendar.JULY, 21, 0, 0).getTime(), Integer.valueOf(0), Boolean.TRUE, new StatsDto(0, 0, 0), Arrays.<RoleDto>asList(new RoleDto("id", "name"))), "message", "date", true));
//        assertNotNull(result);
//    }

    @Test
    void testResolveReportId() {
        ReportDocument reportDocument = ReportDocument.builder().build();
        when(reportRepository.findById(anyString())).thenReturn(Optional.of(reportDocument));
        GenericResponseDto result = reportServiceImpl.resolveReportId("idReport");
        assertNotNull(result);
    }

    @Test
    void testDeleteById() {
        GenericResponseDto result = reportServiceImpl.deleteById("idReporte");
        assertNotNull(result);
    }
}
