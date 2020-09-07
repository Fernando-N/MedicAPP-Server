package cl.medicapp.service.util;

import cl.medicapp.service.document.FeedbackDocument;
import cl.medicapp.service.document.UserDocument;
import cl.medicapp.service.dto.FeedbackDto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Clase utilitarias para feedbacks
 */
public class FeedbackUtil {

    /**
     * Convierte a FeedbackDto
     * @param feedbackDocument target
     * @return target convertido a FeedbackDto
     */
    public static FeedbackDto toFeedbackDto(FeedbackDocument feedbackDocument) {
        return FeedbackDto.builder()
                .id(feedbackDocument.getId())
                .from(feedbackDocument.isAnon() ? null : UserUtil.toUserDto(feedbackDocument.getFrom()))
                .to(UserUtil.toUserDto(feedbackDocument.getTo()))
                .anon(feedbackDocument.isAnon())
                .comment(feedbackDocument.getComment())
                .date(feedbackDocument.getDate())
                .rate(feedbackDocument.getRate())
                .build();
    }

    /**
     * Convierte a FeedbackDocument
     * @param feedbackDto target
     * @param from Autor
     * @param to Destinatario
     * @return target convertido a FeedbackDocument
     */
    public static FeedbackDocument toFeedbackDocument(FeedbackDto feedbackDto, UserDocument from, UserDocument to) {
        return FeedbackDocument.builder()
                .date(DateUtil.from(new Date()))
                .from(from)
                .to(to)
                .comment(feedbackDto.getComment())
                .rate(feedbackDto.getRate())
                .anon(feedbackDto.isAnon())
                .alreadyRead(false)
                .build();
    }

    /**
     * Calcula la puntuación de un usuario
     * @param feedbackDocumentList Lista de feedbacks
     * @return puntuación de usuario
     */
    public static int calculateRating(List<FeedbackDocument> feedbackDocumentList) {
        if (feedbackDocumentList.isEmpty()) {
            return 0;
        }

        int sum = feedbackDocumentList.stream().mapToInt(FeedbackDocument::getRate).sum();
        String div = String.valueOf(sum / feedbackDocumentList.size());

        if (div.contains(".")) {
            if (Integer.parseInt(div.split("\\.")[1]) >= 5) {
                return Integer.parseInt(div.split("\\.")[0] + 1);
            } else {
                return Integer.parseInt(div.split("\\.")[0]);
            }
        }

        return Integer.parseInt(div);
    }

    public static List<FeedbackDocument> filterFeedbackByUserId(String userId, List<FeedbackDocument> feedbackDocuments) {
        List<FeedbackDocument> listFiltered = new ArrayList<>();

        feedbackDocuments.forEach(feedback -> {
            if (feedback.getTo().getId().equalsIgnoreCase(userId)) {
                listFiltered.add(feedback);
            }
        });

        return listFiltered;
    }


    /**
     * Constructor privado para no permitir crear instancias de la clase
     */
    private FeedbackUtil() {

    }

}
