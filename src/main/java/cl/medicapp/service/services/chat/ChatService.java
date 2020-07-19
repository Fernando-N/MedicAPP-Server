package cl.medicapp.service.services.chat;

import cl.medicapp.service.dto.MessageDto;
import cl.medicapp.service.dto.MessageInboundDto;
import cl.medicapp.service.dto.MessageOutboundDto;

import java.util.List;

public interface ChatService {

    List<MessageDto> getMessagesNotRead();

    List<MessageOutboundDto> getMessagesToUserLoggedIn();

    List<MessageOutboundDto> getMessagesFromUserId(String userId);

    MessageOutboundDto saveAndSendMessage(MessageInboundDto messageInboundDto, String idSender);

}
