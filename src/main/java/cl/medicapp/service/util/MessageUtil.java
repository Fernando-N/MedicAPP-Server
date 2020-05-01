package cl.medicapp.service.util;

import cl.medicapp.service.document.MessageDocument;
import cl.medicapp.service.document.UserDocument;
import cl.medicapp.service.dto.MessageDto;

import java.util.Date;

/**
 * Clase util para MessageDocument y MessageDto
 */
public class MessageUtil {

    public static MessageDocument buildDocument(MessageDto messageDto, UserDocument from, UserDocument to) {
        return MessageDocument.builder()
                .date(new Date())
                .from(from)
                .to(to)
                .message(messageDto.getMessage())
                .build();
    }

    public static MessageDto toMessageDto(MessageDocument messageDocument) {
        return MessageDto.builder()
                .date(messageDocument.getDate())
                .message(messageDocument.getMessage())
                .from(messageDocument.getFrom().getFirstName() + " " + messageDocument.getFrom().getLastName())
                .to(messageDocument.getTo().getFirstName() + " " + messageDocument.getTo().getLastName())
                .build();
    }

    /**
     * Constructor privado para no permitir crear instancias de la clase
     */
    private MessageUtil() {

    }

}
