package cl.medicapp.service.util;

import cl.medicapp.service.document.MessageDocument;
import cl.medicapp.service.document.UserDocument;
import cl.medicapp.service.dto.MessageInboundDto;
import cl.medicapp.service.dto.MessageOutboundDto;
import cl.medicapp.service.dto.UserChatDto;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Clase util para mensajes
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

    public static MessageOutboundDto toMessageOutboundDto(MessageDocument messageDocument) {
        return MessageOutboundDto.builder()
                .id(messageDocument.getId())
                .date(messageDocument.getDate())
                .text(messageDocument.getMessage())
                .user(UserChatDto.builder()
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

    public static List<MessageDocument> filterMessagesFromOneUser(List<MessageDocument> messages) {
        List<String> userFrom = new ArrayList<>();
        List<MessageDocument> listFiltered = new ArrayList<>();
        messages.forEach(message -> {
            if ((!userFrom.contains(message.getFrom().getEmail()) || !userFrom.contains(message.getTo().getEmail()))) {
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
