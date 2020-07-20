package cl.medicapp.service.util;

import cl.medicapp.service.document.FeedbackDocument;
import cl.medicapp.service.document.UserDocument;
import cl.medicapp.service.dto.FeedbackDto;

import java.util.Date;
import java.util.List;

/**
 * Clase util para Feedbacks
 */
public class FeedbackUtil {

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

    public static FeedbackDocument toReportDocument(FeedbackDto feedbackDto, UserDocument from, UserDocument to) {
        return FeedbackDocument.builder()
                .date(DateUtil.from(new Date()))
                .from(from)
                .to(to)
                .comment(feedbackDto.getComment())
                .rate(feedbackDto.getRate())
                .anon(feedbackDto.isAnon())
                .build();
    }

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

    /**
     * Constructor privado para no permitir crear instancias de la clase
     */
    private FeedbackUtil() {

    }

}
