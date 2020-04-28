package cl.medicapp.service.repository;

import cl.medicapp.service.entity.MessageEntity;
import cl.medicapp.service.entity.UserEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ChatRepository extends ReactiveMongoRepository<MessageEntity, String> {

    @Tailable
    Flux<MessageEntity> findWithTailableCursorByTo(UserEntity to);

    Flux<MessageEntity> findAllByAlreadyReadFalse();

}