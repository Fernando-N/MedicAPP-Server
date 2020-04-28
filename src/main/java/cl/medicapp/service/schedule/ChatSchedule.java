package cl.medicapp.service.schedule;

import cl.medicapp.service.services.chat.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableScheduling
public class ChatSchedule {

    private final ChatService chatService;

    @Scheduled(fixedRate = 10000)
    public void sendPushNotificationMessageNotRead() {
        chatService.getMessagesNotRead().forEach(
                message -> {
                    log.info("Sending push notification to: " + message.getTo());
                    log.info(message.toString());
                });
    }

}
