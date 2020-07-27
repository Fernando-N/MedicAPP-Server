package cl.medicapp.service.repository.feedback;

import cl.medicapp.service.document.FeedbackDocument;
import cl.medicapp.service.document.UserDocument;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio de feedbacks
 */
@Repository
public interface FeedbackRepository extends PagingAndSortingRepository<FeedbackDocument, String> {

    /**
     * Buscar todas creadas por un usuario
     * @param user Usuario a buscar
     * @return Lista de feedbacks encontrados
     */
    List<FeedbackDocument> findAllByFrom(UserDocument user);

    /**
     * Buscar todas para un usuario
     * @param userTo Usuario a buscar
     * @return Lista de feedbacks encontrados
     */
    List<FeedbackDocument> findAllByToOrderByDateDesc(UserDocument userTo);

    /**
     * Buscar todas para un usuario
     * @param userTo Usuario a buscar
     * @param pageable Objeto de paginación
     * @return Lista de feedbacks encontrados
     */
    List<FeedbackDocument> findAllByToOrderByDateDesc(UserDocument userTo, Pageable pageable);

    /**
     * Buscar feedbacks entre un usuario y otro
     * @param from Usuario creador
     * @param to Usuario destino
     * @return Lista de feedbacks encontrados
     */
    Optional<FeedbackDocument> findByFromAndTo(UserDocument from, UserDocument to);

    List<FeedbackDocument> findAllByAlreadyReadFalse();

}
