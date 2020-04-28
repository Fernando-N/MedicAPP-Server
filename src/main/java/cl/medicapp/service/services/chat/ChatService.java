package cl.medicapp.service.services.chat;

import cl.medicapp.service.dto.MessageDto;
import reactor.core.publisher.Flux;

import java.util.List;

public interface ChatService {

    void insertAndSubscribe(MessageDto messageDto);

    Flux<MessageDto> openStreamToUser(String to);

    List<MessageDto> getMessagesNotRead();

}
