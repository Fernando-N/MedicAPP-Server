package cl.medicapp.service.services.chat;

import cl.medicapp.service.dto.MessageInboundDto;
import cl.medicapp.service.dto.MessageOutboundDto;

import java.util.List;

/**
 * Interfaz de servicio de chat
 */
public interface ChatService {

    /**
     * @see ChatServiceImpl#getMessagesNotRead()
     */
    List<MessageOutboundDto> getMessagesNotRead();

    /**
     * @see ChatServiceImpl#getMessagesToUserLoggedIn()
     */
    List<MessageOutboundDto> getMessagesToUserLoggedIn();

    /**
     * @see ChatServiceImpl#getMessagesToUser(String, boolean)
     */
    List<MessageOutboundDto> getMessagesToUser(String userId, boolean findByEmail);

    /**
     * @see ChatServiceImpl#getMessagesFromUserId(String)
     */
    List<MessageOutboundDto> getMessagesFromUserId(String userId);

    /**
     * @see ChatServiceImpl#saveAndSendMessage(MessageInboundDto, String)
     */
    MessageOutboundDto saveAndSendMessage(MessageInboundDto messageInboundDto, String idSender);

}
