package cl.medicapp.service.services.feedback;

import cl.medicapp.service.dto.FeedbackDto;
import cl.medicapp.service.dto.GenericResponseDto;

import java.util.List;

public interface FeedbackService {

    List<FeedbackDto> getAll();

    List<FeedbackDto> getAllByToUserId(String id);

    List<FeedbackDto> getAllByFromUserId(String id);

    FeedbackDto save(FeedbackDto feedbackDto);

    GenericResponseDto deleteById(String id);

}
