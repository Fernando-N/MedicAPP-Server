package cl.medicapp.service.services.chat;

import cl.medicapp.service.dto.MessageDto;
import cl.medicapp.service.entity.MessageEntity;
import cl.medicapp.service.entity.UserEntity;
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
        UserEntity from = userRepository.findByEmailIgnoreCase(UserUtil.getEmailUserLogged()).orElseThrow();
        UserEntity to = userRepository.findByEmailIgnoreCase(messageDto.getTo()).orElseThrow();
        chatRepository.insert(MessageUtil.build(messageDto, from, to)).subscribe();
    }

    @Override
    public Flux<MessageDto> openStreamToUser(String to) {
        UserEntity toEntity = userRepository.findByEmailIgnoreCase(to).orElseThrow();
        Flux<MessageEntity> messageEntityFlux = chatRepository.findWithTailableCursorByTo(toEntity)
                .doOnNext(messageEntity -> {
                    messageEntity.setAlreadyRead(true);
                    chatRepository.save(messageEntity).subscribe();
                });
        return messageEntityFlux.map(MessageUtil::toMessageDto);
    }

    @Override
    public List<MessageDto> getMessagesNotRead() {
        return chatRepository.findAllByAlreadyReadFalse().toStream()
                .map(messageEntity -> {
                    messageEntity.setAlreadyRead(true);
                    chatRepository.save(messageEntity).subscribe();
                    return MessageUtil.toMessageDto(messageEntity);
                }).collect(Collectors.toList());
    }

}
