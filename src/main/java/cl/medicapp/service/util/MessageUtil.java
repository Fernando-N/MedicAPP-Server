package cl.medicapp.service.util;

import cl.medicapp.service.dto.MessageDto;
import cl.medicapp.service.entity.MessageEntity;
import cl.medicapp.service.entity.UserEntity;
import org.dozer.DozerBeanMapper;

import java.util.Date;

/**
 * Clase util para MessageEntity y MessageDto
 */
public class MessageUtil {

    private static final DozerBeanMapper mapper = new DozerBeanMapper();

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
                .from(messageEntity.getFrom().getFirstName()+" "+messageEntity.getFrom().getLastName())
                .to(messageEntity.getTo().getFirstName()+" "+messageEntity.getTo().getLastName())
                .build();
    }

    public static MessageEntity toMessageEntity(MessageDto messageDto) {
        return mapper.map(messageDto, MessageEntity.class);
    }

    /**
     * Constructor privado para no permitir crear instancias de la clase
     */
    private MessageUtil() {

    }

}
