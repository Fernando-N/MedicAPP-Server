package cl.medicapp.service.repository.chat;

import cl.medicapp.service.document.MessageDocument;
import cl.medicapp.service.document.UserDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.List;

@Repository
public interface ChatRepository2 extends PagingAndSortingRepository<MessageDocument, String> {

    List<MessageDocument> findByTo(UserDocument to);

    List<MessageDocument> findAllByAlreadyReadFalse();

}
