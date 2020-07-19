package cl.medicapp.service.util;

import cl.medicapp.service.document.MessageDocument;
import cl.medicapp.service.document.UserDocument;
import cl.medicapp.service.dto.MessageDto;
import cl.medicapp.service.dto.MessageInboundDto;
import cl.medicapp.service.dto.MessageOutboundDto;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Clase util para MessageDocument y MessageDto
 */
@Slf4j
public class MessageUtil {

    public static MessageDocument buildDocument(MessageInboundDto messageInboundDto, UserDocument from, UserDocument to) {
        return MessageDocument.builder()
                .date(DateUtil.from(new Date()))
                .from(from)
                .to(to)
                .message(messageInboundDto.getText())
                .alreadyRead(false)
                .build();
    }

    public static MessageDto toMessageDto(MessageDocument messageDocument) {
        return MessageDto.builder()
                .id(messageDocument.getId())
                .date(DateUtil.from(messageDocument.getDate()))
                .message(messageDocument.getMessage())
                .from(MessageDto.UserChat.builder()
                        .id(messageDocument.getFrom().getId())
                        .name(messageDocument.getFrom().getUserDetails().getFirstName() + " " + messageDocument.getFrom().getUserDetails().getLastName())
                        .avatar(messageDocument.getFrom().getUserDetails().getProfileImageURI())
                        .build())
                .to(messageDocument.getTo().getUserDetails().getFirstName() + " " + messageDocument.getTo().getUserDetails().getLastName())
                .build();
    }

    public static MessageOutboundDto toMessageOutboundDto(MessageDocument messageDocument) {
        return MessageOutboundDto.builder()
                .id(messageDocument.getId())
                .date(messageDocument.getDate())
                .text(messageDocument.getMessage())
                .user(MessageOutboundDto.UserChat.builder()
                        .id(messageDocument.getFrom().getId())
                        .avatarURI(messageDocument.getFrom().getUserDetails().getProfileImageURI())
                        .name(messageDocument.getFrom().getUserDetails().getFirstName() + " " + messageDocument.getFrom().getUserDetails().getLastName())
                        .build())
                .build();
    }

    public static List<MessageDocument> convertMessagesFromMe(List<MessageDocument> messages) {
        messages.forEach(message -> {
            UserDocument tmp = message.getFrom();
            message.setFrom(message.getTo());
            message.setTo(tmp);
            message.setMessage("Yo: " + message.getMessage());
        });
        return messages;
    }

    public static void orderMessagesByDateDesc(List<MessageDocument> messages) {
        messages.sort(Comparator.comparing(MessageDocument::getDate));
        Collections.reverse(messages);
    }

    public static List<MessageDocument> filterMessagesFromOneUser(List<MessageDocument> messages, String emailSender) {
        List<String> userFrom = new ArrayList<>();
        List<MessageDocument> listFiltered = new ArrayList<>();
        messages.forEach(message -> {
            log.info(message.getDate() + " | " +message.getMessage());
            if ((!userFrom.contains(message.getFrom().getEmail()) || !userFrom.contains(message.getTo().getEmail()))) {
                log.info(message.getDate() + " | " +message.getMessage() + " | GUARDADO");
                userFrom.add(message.getFrom().getEmail());
                userFrom.add(message.getTo().getEmail());
                listFiltered.add(message);
            }
        });
        return listFiltered;
    }

    /**
     * Constructor privado para no permitir crear instancias de la clase
     */
    private MessageUtil() {

    }

}
