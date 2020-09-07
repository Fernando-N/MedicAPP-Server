package cl.medicapp.service.services.chat;

import cl.medicapp.service.constants.Constants;
import cl.medicapp.service.document.MessageDocument;
import cl.medicapp.service.document.UserDocument;
import cl.medicapp.service.dto.MessageInboundDto;
import cl.medicapp.service.dto.MessageOutboundDto;
import cl.medicapp.service.dto.UserChatDto;
import cl.medicapp.service.repository.chat.ChatRepository;
import cl.medicapp.service.repository.user.UserDocumentRepository;
import cl.medicapp.service.util.GenericResponseUtil;
import cl.medicapp.service.util.MessageUtil;
import cl.medicapp.service.util.UserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    public List<MessageOutboundDto> getMessagesNotRead() {
        return new ArrayList<>();
//        return chatRepository.findAllByAlreadyReadFalse().toStream()
//                .map(messageDocument -> {
//                    messageDocument.setAlreadyRead(true);
//                    chatRepository.save(messageDocument).subscribe();
//                    return MessageUtil.toMessageDto(messageDocument);
//                }).filter(messageDto -> false)//DateUtil.differenceNowDate(messageDto.getDate()))
//                .collect(Collectors.toList());
    }

    /**
     * Obtiene los mensajes del usuario en sesión
     * @return Lista de mensajes
     */
    @Override
    public List<MessageOutboundDto> getMessagesToUserLoggedIn() {
        String emailSender = UserUtil.getEmailUserLogged();
        log.info("User: [{}], esta obteniendo su lista de mensajes ", emailSender);
        return getMessagesToUser(emailSender, true);
    }

    /**
     * Obtienes los mensajes de un usuario
     * @param userTarget target
     * @param findByEmail Flag si el target es un correo o id
     * @return Lista de mensajes
     */
    @Override
    public List<MessageOutboundDto> getMessagesToUser(String userTarget, boolean findByEmail) {

        Optional<UserDocument> user;

        if (findByEmail) {
            user = userDocumentRepository.findByEmailIgnoreCase(userTarget);
        }else {
            user = userDocumentRepository.findById(userTarget);
        }

        if (!user.isPresent()) {
            throw GenericResponseUtil.buildGenericException(HttpStatus.NOT_FOUND, String.format(Constants.USER_X_NOT_FOUND, userTarget));
        }

        List<MessageDocument> messages = Stream.concat(
                chatRepository.findByToOrderByDateDesc(user.get()).stream(),
                MessageUtil.convertMessagesFromMe(chatRepository.findByFromOrderByDateDesc(user.get())).stream()
        ).collect(Collectors.toList());

        MessageUtil.orderMessagesByDateDesc(messages);

        List<MessageDocument> filtered = MessageUtil.filterMessagesFromOneUser(messages);

        return filtered.stream().map(MessageUtil::toMessageOutboundDto).collect(Collectors.toList());
    }

    /**
     * Obtiene el historial de mensajes de un usuario con el usuario en sesion
     * @param userId Id de usuario
     * @return Lista de mensajes
     */
    @Override
    public List<MessageOutboundDto> getMessagesFromUserId(String userId) {
        String emailSender = UserUtil.getEmailUserLogged();
        log.info("User: [{}], esta obteniendo su lista de mensajes ", emailSender);
        Optional<UserDocument> userTo = userDocumentRepository.findByEmailIgnoreCase(emailSender);
        Optional<UserDocument> userFrom = userDocumentRepository.findById(userId);

        if (!userFrom.isPresent()) {
            throw GenericResponseUtil.buildGenericException(HttpStatus.NOT_FOUND, String.format(Constants.USER_X_NOT_FOUND, emailSender));
        }

        if (!userTo.isPresent()) {
            throw GenericResponseUtil.buildGenericException(HttpStatus.NOT_FOUND, String.format(Constants.USER_X_NOT_FOUND, userId));
        }

        List<MessageDocument> messages = Stream.concat(
                chatRepository.findFirst30ByToAndFromOrderByDateDesc(userTo.get(), userFrom.get()).stream(),
                chatRepository.findFirst30ByToAndFromOrderByDateDesc(userFrom.get(), userTo.get()).stream()
        ).collect(Collectors.toList());

        MessageUtil.orderMessagesByDateDesc(messages);

        return messages.stream().map(MessageUtil::toMessageOutboundDto).collect(Collectors.toList());
    }

    /**
     * Guarda y retorna un mensaje entrante
     * @param messageInboundDto Mensaje entrante
     * @param idSender Id remitente
     * @return Mensaje entrante convertido a MessageOutboundDto
     */
    @Override
    public MessageOutboundDto saveAndSendMessage(MessageInboundDto messageInboundDto, String idSender) {

        Optional<UserDocument> userFrom = userDocumentRepository.findById(idSender);
        Optional<UserDocument> userTo = userDocumentRepository.findById(messageInboundDto.getTo());

        if (!userFrom.isPresent()) {
            throw GenericResponseUtil.buildGenericException(HttpStatus.NOT_FOUND, String.format(Constants.USER_X_NOT_FOUND, idSender));
        }

        if (!userTo.isPresent()) {
            throw GenericResponseUtil.buildGenericException(HttpStatus.NOT_FOUND, String.format(Constants.USER_X_NOT_FOUND, messageInboundDto.getTo()));
        }

        messageInboundDto.setFrom(UserChatDto.builder()
                .id(userFrom.get().getId())
                .name(userFrom.get().getUserDetails().getFirstName() + " " + userFrom.get().getUserDetails().getLastName())
                .avatarURI(userFrom.get().getUserDetails().getProfileImageURI())
                .build());

        MessageDocument message = chatRepository.save(MessageUtil.buildDocument(messageInboundDto, userFrom.get(), userTo.get()));

        log.info("Message send: " + message.toString());

        return MessageUtil.toMessageOutboundDto(message);
    }

}
