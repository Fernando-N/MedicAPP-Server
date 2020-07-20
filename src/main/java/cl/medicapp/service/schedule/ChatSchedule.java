package cl.medicapp.service.schedule;

import cl.medicapp.service.services.chat.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Clase schedule para enviar notificaciones push a usuarios cuando se envia un mensaje
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableScheduling
public class ChatSchedule {

    /**
     * Bean servicio de chat
     */
    private final ChatService chatService;

    /**
     * Enviar notificación push en mensajes, este metodo se ejecuta cada 10 segundos
     */
    @Scheduled(fixedRate = 10000)
    public void sendPushNotificationMessageNotRead() {
        chatService.getMessagesNotRead().forEach(
                message -> {
                    log.info("Sending push notification to: " + message.getUser().getId());
                    log.info(message.toString());
                });
    }

}
