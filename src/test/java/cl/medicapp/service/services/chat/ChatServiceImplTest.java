package cl.medicapp.service.services.chat;

import cl.medicapp.service.document.MessageDocument;
import cl.medicapp.service.document.UserDetailsDocument;
import cl.medicapp.service.document.UserDocument;
import cl.medicapp.service.dto.MessageInboundDto;
import cl.medicapp.service.dto.MessageOutboundDto;
import cl.medicapp.service.dto.UserChatDto;
import cl.medicapp.service.repository.chat.ChatRepository;
import cl.medicapp.service.repository.user.UserDocumentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

class ChatServiceImplTest {

    @Mock
    private ChatRepository chatRepository;

    @Mock
    private UserDocumentRepository userDocumentRepository;

    @InjectMocks
    private ChatServiceImpl chatServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetMessagesNotRead() {
        List<MessageOutboundDto> result = chatServiceImpl.getMessagesNotRead();

        assertNotNull(result);
    }

    @Test
    void testGetMessagesToUser() {
        when(userDocumentRepository.findByEmailIgnoreCase(anyString())).thenReturn(Optional.of(UserDocument.builder().build()));
        List<MessageOutboundDto> result = chatServiceImpl.getMessagesToUser("userTarget", true);

        assertNotNull(result);
    }

    @Test
    void testSaveAndSendMessage() {
        UserDetailsDocument userDetailsDocument = UserDetailsDocument.builder().firstName("Test").lastName("Test").build();

        when(userDocumentRepository.findById(anyString())).thenReturn(Optional.of(UserDocument.builder().userDetails(userDetailsDocument).build()));
        when(chatRepository.save(any())).thenReturn(MessageDocument.builder().from(UserDocument.builder().userDetails(userDetailsDocument).build()).build());

        MessageOutboundDto result = chatServiceImpl.saveAndSendMessage(new MessageInboundDto("text", new UserChatDto("id", "name", "avatarURI"), "to"), "idSender");

        assertNotNull(result);
    }
}
