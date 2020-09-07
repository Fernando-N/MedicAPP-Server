package cl.medicapp.service.repository.chat;

import cl.medicapp.service.document.MessageDocument;
import cl.medicapp.service.document.UserDocument;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio de chat
 */
@Repository
public interface ChatRepository extends PagingAndSortingRepository<MessageDocument, String> {

    /**
     * Obtiene mensajes de para un usuario con orden descendente segun fecha
     * @param to Usuario a buscar
     * @return Lista de mensajes
     */
    List<MessageDocument> findByToOrderByDateDesc(UserDocument to);

    /**
     * Obtiene mensajes de un usuario con orden descendente según fecha
     * @param from Usuario a buscar
     * @return Lista de mensajes
     */
    List<MessageDocument> findByFromOrderByDateDesc(UserDocument from);

    /**
     * Busca los ultimos 30 mensajes entre dos usuarios
     * @param to Usuario para
     * @param from Usuario desde
     * @return Lista de mensajes
     */
    List<MessageDocument> findFirst30ByToAndFromOrderByDateDesc(UserDocument to, UserDocument from);

    /**
     * Retorna lista de mensajes no leidos de todos los usuarios
     * @return Lista de mensajes
     */
    List<MessageDocument> findAllByAlreadyReadFalse();

}
