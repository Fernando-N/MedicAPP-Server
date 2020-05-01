package cl.medicapp.service.services.chat;

import cl.medicapp.service.document.MessageDocument;
import cl.medicapp.service.document.UserDocument;
import cl.medicapp.service.dto.MessageDto;
import cl.medicapp.service.repository.ChatRepository;
import cl.medicapp.service.repository.UserRepository;
import cl.medicapp.service.util.MessageUtil;
import cl.medicapp.service.util.UserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    @Override
    public void insertAndSubscribe(MessageDto messageDto) {
        UserDocument from = userRepository.findByEmailIgnoreCase(UserUtil.getEmailUserLogged()).orElseThrow();
        UserDocument to = userRepository.findByEmailIgnoreCase(messageDto.getTo()).orElseThrow();
        chatRepository.insert(MessageUtil.buildDocument(messageDto, from, to)).subscribe();
    }

    @Override
    public Flux<MessageDto> openStreamToUser(String to) {
        UserDocument toUser = userRepository.findByEmailIgnoreCase(to).orElseThrow();
        Flux<MessageDocument> messageDocumentFlux = chatRepository.findWithTailableCursorByTo(toUser)
                .doOnNext(messageDocument -> {
                    messageDocument.setAlreadyRead(true);
                    chatRepository.save(messageDocument).subscribe();
                });
        return messageDocumentFlux.map(MessageUtil::toMessageDto);
    }

    @Override
    public List<MessageDto> getMessagesNotRead() {
        return chatRepository.findAllByAlreadyReadFalse().toStream()
                .map(messageDocument -> {
                    messageDocument.setAlreadyRead(true);
                    chatRepository.save(messageDocument).subscribe();
                    return MessageUtil.toMessageDto(messageDocument);
                }).collect(Collectors.toList());
    }

}
