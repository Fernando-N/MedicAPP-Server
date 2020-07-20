package cl.medicapp.service.services.chat;

import cl.medicapp.service.document.MessageDocument;
import cl.medicapp.service.document.RoleDocument;
import cl.medicapp.service.document.UserDocument;
import cl.medicapp.service.dto.MessageInboundDto;
import cl.medicapp.service.dto.MessageOutboundDto;
import cl.medicapp.service.repository.chat.ChatRepository;
import cl.medicapp.service.repository.user.UserDocumentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

/**
 * Clase de test unitario para ChatService.
 */
class ChatServiceImplTest {

    @Mock
    private ChatRepository chatRepository;

    @Mock
    private UserDocumentRepository userDocumentRepository;

    @InjectMocks
    private ChatServiceImpl chatServiceImpl;

    /**
     * Método Set-up de DocumentsHolder para realizar flujos
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetMessagesToUserLoggedIn() {
        when(chatRepository.findByToOrderByDateDesc(any())).thenReturn(Collections.singletonList(new MessageDocument("id", new UserDocument("id", "email", "password", Arrays.<RoleDocument>asList(null), null, null, Boolean.TRUE, Integer.valueOf(0), new GregorianCalendar(2020, Calendar.JULY, 20, 12, 24).getTime(), new GregorianCalendar(2020, Calendar.JULY, 20, 12, 24).getTime(), "resetToken"), new UserDocument("id", "email", "password", Arrays.<RoleDocument>asList(null), null, null, Boolean.TRUE, Integer.valueOf(0), new GregorianCalendar(2020, Calendar.JULY, 20, 12, 24).getTime(), new GregorianCalendar(2020, Calendar.JULY, 20, 12, 24).getTime(), "resetToken"), "message", "date", true)));
        when(chatRepository.findByFromOrderByDateDesc(any())).thenReturn(Collections.singletonList(new MessageDocument("id", new UserDocument("id", "email", "password", Arrays.<RoleDocument>asList(null), null, null, Boolean.TRUE, Integer.valueOf(0), new GregorianCalendar(2020, Calendar.JULY, 20, 12, 24).getTime(), new GregorianCalendar(2020, Calendar.JULY, 20, 12, 24).getTime(), "resetToken"), new UserDocument("id", "email", "password", Arrays.<RoleDocument>asList(null), null, null, Boolean.TRUE, Integer.valueOf(0), new GregorianCalendar(2020, Calendar.JULY, 20, 12, 24).getTime(), new GregorianCalendar(2020, Calendar.JULY, 20, 12, 24).getTime(), "resetToken"), "message", "date", true)));
        when(userDocumentRepository.findByEmailIgnoreCase(anyString())).thenReturn(null);

        List<MessageOutboundDto> result = chatServiceImpl.getMessagesToUserLoggedIn();
        Assertions.assertEquals(Collections.singletonList(new MessageOutboundDto("id", "text", "date", new MessageOutboundDto.UserChat("id", "name", "avatarURI"))), result);
    }

    @Test
    void testGetMessagesToUser() {
        when(chatRepository.findByToOrderByDateDesc(any())).thenReturn(Arrays.<MessageDocument>asList(new MessageDocument("id", new UserDocument("id", "email", "password", Arrays.<RoleDocument>asList(null), null, null, Boolean.TRUE, Integer.valueOf(0), new GregorianCalendar(2020, Calendar.JULY, 20, 12, 24).getTime(), new GregorianCalendar(2020, Calendar.JULY, 20, 12, 24).getTime(), "resetToken"), new UserDocument("id", "email", "password", Arrays.<RoleDocument>asList(null), null, null, Boolean.TRUE, Integer.valueOf(0), new GregorianCalendar(2020, Calendar.JULY, 20, 12, 24).getTime(), new GregorianCalendar(2020, Calendar.JULY, 20, 12, 24).getTime(), "resetToken"), "message", "date", true)));
        when(chatRepository.findByFromOrderByDateDesc(any())).thenReturn(Arrays.<MessageDocument>asList(new MessageDocument("id", new UserDocument("id", "email", "password", Arrays.<RoleDocument>asList(null), null, null, Boolean.TRUE, Integer.valueOf(0), new GregorianCalendar(2020, Calendar.JULY, 20, 12, 24).getTime(), new GregorianCalendar(2020, Calendar.JULY, 20, 12, 24).getTime(), "resetToken"), new UserDocument("id", "email", "password", Arrays.<RoleDocument>asList(null), null, null, Boolean.TRUE, Integer.valueOf(0), new GregorianCalendar(2020, Calendar.JULY, 20, 12, 24).getTime(), new GregorianCalendar(2020, Calendar.JULY, 20, 12, 24).getTime(), "resetToken"), "message", "date", true)));
        when(userDocumentRepository.findByEmailIgnoreCase(anyString())).thenReturn(null);

        List<MessageOutboundDto> result = chatServiceImpl.getMessagesToUser("userTarget", true);
        Assertions.assertEquals(Arrays.<MessageOutboundDto>asList(new MessageOutboundDto("id", "text", "date", new MessageOutboundDto.UserChat("id", "name", "avatarURI"))), result);
    }

    @Test
    void testGetMessagesFromUserId() {
        when(chatRepository.findFirst30ByToAndFromOrderByDateDesc(any(), any())).thenReturn(Arrays.<MessageDocument>asList(new MessageDocument("id", new UserDocument("id", "email", "password", Arrays.<RoleDocument>asList(null), null, null, Boolean.TRUE, Integer.valueOf(0), new GregorianCalendar(2020, Calendar.JULY, 20, 12, 24).getTime(), new GregorianCalendar(2020, Calendar.JULY, 20, 12, 24).getTime(), "resetToken"), new UserDocument("id", "email", "password", Arrays.<RoleDocument>asList(null), null, null, Boolean.TRUE, Integer.valueOf(0), new GregorianCalendar(2020, Calendar.JULY, 20, 12, 24).getTime(), new GregorianCalendar(2020, Calendar.JULY, 20, 12, 24).getTime(), "resetToken"), "message", "date", true)));
        when(userDocumentRepository.findByEmailIgnoreCase(anyString())).thenReturn(null);

        List<MessageOutboundDto> result = chatServiceImpl.getMessagesFromUserId("userId");
        Assertions.assertEquals(Arrays.<MessageOutboundDto>asList(new MessageOutboundDto("id", "text", "date", new MessageOutboundDto.UserChat("id", "name", "avatarURI"))), result);
    }

    @Test
    void testSaveAndSendMessage() {
        MessageOutboundDto result = chatServiceImpl.saveAndSendMessage(new MessageInboundDto("id", "text", new GregorianCalendar(2020, Calendar.JULY, 20, 12, 24).getTime(), new MessageInboundDto.UserChat("id", "name", "avatarURI"), "to"), "idSender");
        Assertions.assertEquals(new MessageOutboundDto("id", "text", "date", new MessageOutboundDto.UserChat("id", "name", "avatarURI")), result);
    }
}
