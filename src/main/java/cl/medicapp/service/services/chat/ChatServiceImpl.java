package cl.medicapp.service.services.chat;

import cl.medicapp.service.document.MessageDocument;
import cl.medicapp.service.document.UserDocument;
import cl.medicapp.service.dto.MessageDto;
import cl.medicapp.service.repository.chat.ChatRepository;
import cl.medicapp.service.repository.chat.ChatRepository2;
import cl.medicapp.service.repository.user.UserDocumentRepository;
import cl.medicapp.service.util.DateUtil;
import cl.medicapp.service.util.GenericResponseUtil;
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
    private final ChatRepository2 chatRepository2;
    private final UserDocumentRepository userDocumentRepository;

    @Override
    public void sendMessage(MessageDto messageDto) {
        UserDocument from = userDocumentRepository.findByEmailIgnoreCase(UserUtil.getEmailUserLogged()).orElseThrow(GenericResponseUtil::getGenericException);
        //TODO Agregar excepción correcta
        UserDocument to = userDocumentRepository.findByEmailIgnoreCase(messageDto.getTo()).orElseThrow(GenericResponseUtil::getGenericException);
        chatRepository.insert(MessageUtil.buildDocument(messageDto, from, to)).subscribe();
    }

    @Override
    //TODO Agregar logica para que sea 1 a 1
    public Flux<MessageDto> getMessages(String from) {
        UserDocument fromUser = userDocumentRepository.findByEmailIgnoreCase(from).orElseThrow(GenericResponseUtil::getGenericException);
        Flux<MessageDocument> messageDocumentFlux = chatRepository.findWithTailableCursorByTo(fromUser)
                .doOnNext(messageDocument -> {
                    messageDocument.setAlreadyRead(true);
                    chatRepository.save(messageDocument).subscribe();
                });
        return messageDocumentFlux.map(MessageUtil::toMessageDto);
    }

    @Override
    public List<MessageDto> getMessages2(String from) {
        UserDocument fromUser = userDocumentRepository.findByEmailIgnoreCase(from).orElseThrow(GenericResponseUtil::getGenericException);
        List<MessageDto> messageDocuments = chatRepository2.findByTo(fromUser).stream().map(MessageUtil::toMessageDto)
                .collect(Collectors.toList());

        return messageDocuments;
    }

    @Override
    public List<MessageDto> getMessagesNotRead() {
        return chatRepository.findAllByAlreadyReadFalse().toStream()
                .map(messageDocument -> {
                    messageDocument.setAlreadyRead(true);
                    chatRepository.save(messageDocument).subscribe();
                    return MessageUtil.toMessageDto(messageDocument);
                }).filter(messageDto -> DateUtil.differenceNowDate(messageDto.getDate()))
                .collect(Collectors.toList());
    }

}
