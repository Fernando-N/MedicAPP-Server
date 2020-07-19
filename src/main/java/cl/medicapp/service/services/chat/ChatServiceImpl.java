package cl.medicapp.service.services.chat;

import cl.medicapp.service.document.MessageDocument;
import cl.medicapp.service.document.UserDocument;
import cl.medicapp.service.dto.MessageDto;
import cl.medicapp.service.dto.MessageInboundDto;
import cl.medicapp.service.dto.MessageOutboundDto;
import cl.medicapp.service.repository.chat.ChatRepository;
import cl.medicapp.service.repository.user.UserDocumentRepository;
import cl.medicapp.service.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final UserDocumentRepository userDocumentRepository;

    //TODO Implementar esto
    @Override
    public List<MessageDto> getMessagesNotRead() {
        return new ArrayList<>();
//        return chatRepository.findAllByAlreadyReadFalse().toStream()
//                .map(messageDocument -> {
//                    messageDocument.setAlreadyRead(true);
//                    chatRepository.save(messageDocument).subscribe();
//                    return MessageUtil.toMessageDto(messageDocument);
//                }).filter(messageDto -> false)//DateUtil.differenceNowDate(messageDto.getDate()))
//                .collect(Collectors.toList());
    }

    @Override
    public List<MessageOutboundDto> getMessagesToUserLoggedIn() {
        String emailSender = UserUtil.getEmailUserLogged();
        log.info("User: [{}], esta obteniendo su lista de mensajes ", emailSender);
        UserDocument user = userDocumentRepository.findByEmailIgnoreCase(emailSender).get();
        List<MessageDocument> messages = Stream.concat(
                chatRepository.findByToOrderByDateDesc(user).stream(),
                MessageUtil.convertMessagesFromMe(chatRepository.findByFromOrderByDateDesc(user)).stream()
        ).collect(Collectors.toList());

        MessageUtil.orderMessagesByDateDesc(messages);

        List<MessageDocument> filtered = MessageUtil.filterMessagesFromOneUser(messages, emailSender);

        return filtered.stream().map(MessageUtil::toMessageOutboundDto).collect(Collectors.toList());
    }

    @Override
    public List<MessageOutboundDto> getMessagesFromUserId(String userId) {
        String emailSender = UserUtil.getEmailUserLogged();
        log.info("User: [{}], esta obteniendo su lista de mensajes ", emailSender);
        UserDocument to = userDocumentRepository.findByEmailIgnoreCase(emailSender).get();
        UserDocument from = userDocumentRepository.findById(userId).get();
        List<MessageDocument> messages = Stream.concat(
                chatRepository.findFirst30ByToAndFromOrderByDateDesc(to, from).stream(),
                chatRepository.findFirst30ByToAndFromOrderByDateDesc(from, to).stream()
        ).collect(Collectors.toList());

        MessageUtil.orderMessagesByDateDesc(messages);

        return messages.stream().map(MessageUtil::toMessageOutboundDto).collect(Collectors.toList());
    }

    @Override
    public MessageOutboundDto saveAndSendMessage(MessageInboundDto messageInboundDto, String idSender) {

        Optional<UserDocument> userFrom = userDocumentRepository.findById(idSender);
        Optional<UserDocument> userTo = userDocumentRepository.findById(messageInboundDto.getTo());

        messageInboundDto.setFrom(MessageInboundDto.UserChat.builder()
                .id(userFrom.get().getId())
                .name(userFrom.get().getUserDetails().getFirstName() + " " + userFrom.get().getUserDetails().getLastName())
                .avatarURI(userFrom.get().getUserDetails().getProfileImageURI())
                .build());

        MessageDocument message = chatRepository.save(MessageUtil.buildDocument(messageInboundDto, userFrom.get(), userTo.get()));

        log.info("Message send: " + message.toString());

        return MessageUtil.toMessageOutboundDto(message);
    }

}
