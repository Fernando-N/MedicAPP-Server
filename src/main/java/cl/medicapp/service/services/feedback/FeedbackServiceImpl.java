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
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;

    @Override
    public List<FeedbackDto> getAll() {
        List<FeedbackDto> feedbackDtos = new ArrayList<>();
        feedbackRepository.findAll().forEach(feedback -> feedbackDtos.add(FeedbackUtil.toFeedbackDto(feedback)));
        return feedbackDtos;
    }

    @Override
    public List<FeedbackDto> getAllByToUserId(String id) {
        Optional<UserDocument> userDocumentOptional = userRepository.findById(id);

        if (!userDocumentOptional.isPresent()) {
            throw GenericResponseUtil.buildGenericException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.getReasonPhrase(), String.format(Constants.ROLE_X_NOT_FOUND, id));
        }

        return feedbackRepository.findAllByTo(userDocumentOptional.get()).stream().map(FeedbackUtil::toFeedbackDto).collect(Collectors.toList());
    }

    @Override
    public List<FeedbackDto> getAllByFromUserId(String id) {
        Optional<UserDocument> userDocumentOptional = userRepository.findById(id);

        if (!userDocumentOptional.isPresent()) {
            throw GenericResponseUtil.buildGenericException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.getReasonPhrase(), String.format(Constants.ROLE_X_NOT_FOUND, id));
        }

        return feedbackRepository.findAllByFrom(userDocumentOptional.get()).stream().map(FeedbackUtil::toFeedbackDto).collect(Collectors.toList());
    }

    @Override
    @FormatArgs
    public FeedbackDto save(FeedbackDto feedbackDto) {
        //todo Implementar esto
        return null;
    }

    @Override
    @FormatArgs
    public GenericResponseDto deleteById(@UpperCase String name) {
        feedbackRepository.deleteById(name);

        return GenericResponseUtil.buildGenericResponse(HttpStatus.NOT_FOUND.getReasonPhrase(), String.format(Constants.ROLE_X_NOT_FOUND, name));
    }
}
