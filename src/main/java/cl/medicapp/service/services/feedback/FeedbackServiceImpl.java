package cl.medicapp.service.services.feedback;

import cl.medicapp.service.annotation.FormatArgs;
import cl.medicapp.service.annotation.UpperCase;
import cl.medicapp.service.constants.Constants;
import cl.medicapp.service.document.UserDocument;
import cl.medicapp.service.dto.FeedbackDto;
import cl.medicapp.service.dto.GenericResponseDto;
import cl.medicapp.service.repository.feedback.FeedbackRepository;
import cl.medicapp.service.repository.user.UserRepository;
import cl.medicapp.service.util.FeedbackUtil;
import cl.medicapp.service.util.GenericResponseUtil;
import cl.medicapp.service.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementacion servicio de feedbacks
 */
@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    /**
     * Bean de repositorio de feedbacks
     */
    private final FeedbackRepository feedbackRepository;

    /**
     * Bean de repositorio de usuarios
     */
    private final UserRepository userRepository;

    /**
     * Obtiene todos los feedbacks
     * @return Lista de feedbacks
     */
    @Override
    public List<FeedbackDto> getAll() {
        List<FeedbackDto> feedbackDtos = new ArrayList<>();
        feedbackRepository.findAll().forEach(feedback -> feedbackDtos.add(FeedbackUtil.toFeedbackDto(feedback)));
        return feedbackDtos;
    }

    /**
     * Obtener todos los feedbacks dirigidos a un usuario
     * @param idUsuario idUsuario
     * @return Lista de feedbacks
     */
    @Override
    public List<FeedbackDto> getAllByToUserId(String idUsuario) {
        Optional<UserDocument> userDocumentOptional = userRepository.findById(idUsuario);

        if (!userDocumentOptional.isPresent()) {
            throw GenericResponseUtil.buildGenericException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.getReasonPhrase(), String.format(Constants.ROLE_X_NOT_FOUND, idUsuario));
        }

        return feedbackRepository.findAllByTo(userDocumentOptional.get()).stream().map(FeedbackUtil::toFeedbackDto).collect(Collectors.toList());
    }

    /**
     * Obtiene todos los feedbacks creados por un usuario
     * @param idUsuario Id usuario
     * @return Lista de feedbacks
     */
    @Override
    public List<FeedbackDto> getAllByFromUserId(String idUsuario) {
        Optional<UserDocument> userDocumentOptional = userRepository.findById(idUsuario);

        if (!userDocumentOptional.isPresent()) {
            throw GenericResponseUtil.buildGenericException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.getReasonPhrase(), String.format(Constants.ROLE_X_NOT_FOUND, idUsuario));
        }

        return feedbackRepository.findAllByFrom(userDocumentOptional.get()).stream().map(FeedbackUtil::toFeedbackDto).collect(Collectors.toList());
    }

    /**
     * Guarda un feedback
     * @param request Objeto con datos de feedback
     * @return Feedback guardado
     */
    @Override
    @FormatArgs
    public FeedbackDto save(FeedbackDto request) {
        Optional<UserDocument> fromUserOptional = userRepository.findByEmailIgnoreCase(UserUtil.getEmailUserLogged());
        Optional<UserDocument> toUserOptional = userRepository.findById(request.getToUserId());

        if (!fromUserOptional.isPresent() || !toUserOptional.isPresent()) {
            throw GenericResponseUtil.buildGenericException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(), String.format(Constants.USER_X_NOT_FOUND, request.getTo().getId()));
        }

        feedbackRepository.save(FeedbackUtil.toFeedbackDocument(request, fromUserOptional.get(), toUserOptional.get()));

        return request;
    }

    /**
     * Eliminar feedback por id
     * @param idFeedback Id feedback
     * @return Respuesta
     */
    @Override
    public GenericResponseDto deleteById(String idFeedback) {
        feedbackRepository.deleteById(idFeedback);
        return GenericResponseUtil.buildGenericResponse(HttpStatus.NOT_FOUND.getReasonPhrase(), String.format(Constants.ROLE_X_NOT_FOUND, idFeedback));
    }
}
