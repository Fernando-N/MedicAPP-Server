package cl.medicapp.service.repository;

import cl.medicapp.service.document.MessageDocument;
import cl.medicapp.service.document.UserDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ChatRepository extends ReactiveMongoRepository<MessageDocument, String> {

    @Tailable
    Flux<MessageDocument> findWithTailableCursorByTo(UserDocument to);

    Flux<MessageDocument> findAllByAlreadyReadFalse();

}