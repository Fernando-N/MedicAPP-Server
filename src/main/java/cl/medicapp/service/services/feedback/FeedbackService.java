package cl.medicapp.service.services.feedback;

import cl.medicapp.service.dto.FeedbackDto;
import cl.medicapp.service.dto.GenericResponseDto;

import java.util.List;

/**
 * Interfaz de servicio de feedback
 */
public interface FeedbackService {

    /**
     * @see FeedbackServiceImpl#getAll()
     */
    List<FeedbackDto> getAll();

    /**
     * @see FeedbackServiceImpl#getAllByToUserId(String, int)
     */
    List<FeedbackDto> getAllByToUserId(String id, int page);

    /**
     * @see FeedbackServiceImpl#getAllByFromUserId(String)
     */
    List<FeedbackDto> getAllByFromUserId(String id);

    /**
     * @see FeedbackServiceImpl#save(FeedbackDto)
     */
    FeedbackDto save(FeedbackDto feedbackDto);

    /**
     * @see FeedbackServiceImpl#deleteById(String)
     */
    GenericResponseDto deleteById(String id);

}
