package cl.medicapp.service.services.chat;

import cl.medicapp.service.dto.MessageDto;
import reactor.core.publisher.Flux;

import java.util.List;

public interface ChatService {

    void sendMessage(MessageDto messageDto);

    Flux<MessageDto> getMessages(String to);

    List<MessageDto> getMessages2(String to);

    List<MessageDto> getMessagesNotRead();

}
