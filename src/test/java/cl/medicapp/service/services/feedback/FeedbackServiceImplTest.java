package cl.medicapp.service.services.feedback;

import cl.medicapp.service.document.CommuneDocument;
import cl.medicapp.service.document.FeedbackDocument;
import cl.medicapp.service.document.RegionDocument;
import cl.medicapp.service.document.RoleDocument;
import cl.medicapp.service.document.UserDetailsDocument;
import cl.medicapp.service.document.UserDocument;
import cl.medicapp.service.dto.FeedbackDto;
import cl.medicapp.service.dto.GenericResponseDto;
import cl.medicapp.service.holder.DocumentsHolder;
import cl.medicapp.service.repository.feedback.FeedbackRepository;
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

class FeedbackServiceImplTest {

    @Mock
    private FeedbackRepository feedbackRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private FeedbackServiceImpl feedbackServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        DocumentsHolder.getInstance().setRoleDocumentList(Collections.singletonList(RoleDocument.builder().name("PARAMEDIC").build()));
    }

    @Test
    void testGetAll() {
        List<FeedbackDto> result = feedbackServiceImpl.getAll();
        assertNotNull(result);
    }

    @Test
    void testGetAllByToUserId() {
        CommuneDocument communeDocument = CommuneDocument.builder().region(RegionDocument.builder().build()).build();
        UserDetailsDocument userDetailsDocument = UserDetailsDocument.builder().commune(communeDocument).build();
        FeedbackDocument feedbackDocument = FeedbackDocument.builder().anon(true).to(UserDocument.builder().roleEntities(Collections.singletonList(RoleDocument.builder().name("ADMIN").build())).userDetails(userDetailsDocument).build()).build();
        when(feedbackRepository.findAllByToOrderByDateDesc(any())).thenReturn(Collections.singletonList(feedbackDocument));
        when(userRepository.findById(anyString())).thenReturn(Optional.of(UserDocument.builder().roleEntities(Collections.singletonList(RoleDocument.builder().name("ROLE_PARAMEDIC").build())).build()));

        List<FeedbackDto> result = feedbackServiceImpl.getAllByToUserId("idUsuario", 1);
        assertNotNull(result);
    }

    @Test
    void testGetAllByFromUserId() {
        CommuneDocument communeDocument = CommuneDocument.builder().region(RegionDocument.builder().build()).build();
        UserDetailsDocument userDetailsDocument = UserDetailsDocument.builder().commune(communeDocument).build();
        FeedbackDocument feedbackDocument = FeedbackDocument.builder().anon(true).to(UserDocument.builder().roleEntities(Collections.singletonList(RoleDocument.builder().name("ADMIN").build())).userDetails(userDetailsDocument).build()).build();
        when(feedbackRepository.findAllByToOrderByDateDesc(any())).thenReturn(Collections.singletonList(feedbackDocument));
        when(userRepository.findById(anyString())).thenReturn(Optional.of(UserDocument.builder().build()));

        List<FeedbackDto> result = feedbackServiceImpl.getAllByFromUserId("idUsuario");

        assertNotNull(result);
    }

    @Test
    void testDeleteById() {
        GenericResponseDto result = feedbackServiceImpl.deleteById("idFeedback");

        assertNotNull(result);
    }
}
