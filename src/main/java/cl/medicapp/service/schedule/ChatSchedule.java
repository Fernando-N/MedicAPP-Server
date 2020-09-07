package cl.medicapp.service.schedule;

import cl.medicapp.service.document.FeedbackDocument;
import cl.medicapp.service.document.StatsDocument;
import cl.medicapp.service.document.UserDocument;
import cl.medicapp.service.repository.feedback.FeedbackRepository;
import cl.medicapp.service.repository.stats.StatsRepository;
import cl.medicapp.service.services.chat.ChatService;
import cl.medicapp.service.util.FeedbackUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.List;

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

    private final FeedbackRepository feedbackRepository;

    private final StatsRepository statsRepository;

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

    /**
     * Metodo que calcula valoraciones de usuarios cada x cantidad de tiempo, para evitar una respuesta lenta al devolver usuarios.
     */
    @Scheduled(fixedRate = 10000)
    public void calculateStatsUsers() {
        List<FeedbackDocument> feedbackDocuments = feedbackRepository.findAllByAlreadyReadFalse();
        List<UserDocument> usersReady = new ArrayList<>();

        List<StatsDocument> statsDocuments = new ArrayList<>();

        feedbackDocuments.forEach(feedback -> {

            if (usersReady.contains(feedback.getTo())) return;

            usersReady.add(feedback.getTo());

            List<FeedbackDocument> listActualUser = FeedbackUtil.filterFeedbackByUserId(feedback.getTo().getId(), feedbackDocuments);
            List<FeedbackDocument> listOldUser = feedbackRepository.findAllByToOrderByDateDesc(feedback.getTo());

            listActualUser.addAll(listOldUser);

            int contacts = chatService.getMessagesToUser(feedback.getTo().getId(), false).size();
            int valuations = listActualUser.size();
            int rating = FeedbackUtil.calculateRating(listActualUser);

            statsDocuments.add(StatsDocument.builder()
                            .contacts(contacts)
                            .rating(rating)
                            .valuations(valuations)
                            .user(feedback.getTo())
                            .build());
        });

        statsRepository.saveAll(statsDocuments);

    }

}
