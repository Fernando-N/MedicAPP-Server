package cl.medicapp.service.repository.feedback;

import cl.medicapp.service.document.FeedbackDocument;
import cl.medicapp.service.document.UserDocument;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio de Roles
 */
@Repository
public interface FeedbackRepository extends PagingAndSortingRepository<FeedbackDocument, String> {

    Optional<List<FeedbackDocument>> findByFrom(UserDocument user);

    Optional<List<FeedbackDocument>> findByTo(UserDocument userTo);

    Optional<FeedbackDocument> findByFromAndTo(UserDocument from, UserDocument to);

}