package cl.medicapp.service.util;

import cl.medicapp.service.dto.MessageDto;
import cl.medicapp.service.entity.MessageEntity;
import cl.medicapp.service.entity.UserEntity;

import java.util.Date;

/**
 * Clase util para MessageEntity y MessageDto
 */
public class MessageUtil {

    public static MessageEntity build(MessageDto messageDto, UserEntity from, UserEntity to) {
        return MessageEntity.builder()
                .date(new Date())
                .from(from)
                .to(to)
                .message(messageDto.getMessage())
                .build();
    }

    public static MessageDto toMessageDto(MessageEntity messageEntity) {
        return MessageDto.builder()
                .date(messageEntity.getDate())
                .message(messageEntity.getMessage())
                .from(messageEntity.getFrom().getFirstName() + " " + messageEntity.getFrom().getLastName())
                .to(messageEntity.getTo().getFirstName() + " " + messageEntity.getTo().getLastName())
                .build();
    }

    /**
     * Constructor privado para no permitir crear instancias de la clase
     */
    private MessageUtil() {

    }

}
