package cl.medicapp.service.repository.stats;

import cl.medicapp.service.document.StatsDocument;
import cl.medicapp.service.document.UserDocument;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio de Estadisticas
 */
@Repository
public interface StatsRepository extends PagingAndSortingRepository<StatsDocument, String> {

    /**
     * Buscar todos las estadisticas
     * @return Lista de estadisticas
     */
    List<StatsDocument> findAll();

    /**
     * Busca las estadisticas de un usuario
     * @param user target
     * @return Estadisticas de usuario
     */
    Optional<StatsDocument> findByUser(UserDocument user);

}
