package cl.medicapp.service.repository.chat;

import cl.medicapp.service.document.MessageDocument;
import cl.medicapp.service.document.UserDocument;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends PagingAndSortingRepository<MessageDocument, String> {

    List<MessageDocument> findByToOrderByDateDesc(UserDocument to);

    List<MessageDocument> findByFromOrderByDateDesc(UserDocument from);

    List<MessageDocument> findFirst30ByToAndFromOrderByDateDesc(UserDocument to, UserDocument from);

    List<MessageDocument> findAllByAlreadyReadFalse();

}
