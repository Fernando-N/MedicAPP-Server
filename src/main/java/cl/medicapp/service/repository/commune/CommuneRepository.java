package cl.medicapp.service.repository.commune;

import cl.medicapp.service.document.CommuneDocument;
import cl.medicapp.service.document.RegionDocument;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio de comunas
 */
@Repository
public interface CommuneRepository extends PagingAndSortingRepository<CommuneDocument, String> {

    /**
     * Buscar todas las comunas
     * @return Lista de comunas
     */
    List<CommuneDocument> findAll();

    /**
     * Busca todas las comunas de una region
     * @param region Region a buscar
     * @return Lista de comunas
     */
    List<CommuneDocument> findAllByRegion(RegionDocument region);

    /**
     * Busca una comuna por nombre
     * @param name Nombre a buscar
     * @return Lista de comunas
     */
    Optional<CommuneDocument> findByNameIgnoreCase(String name);

    /**
     * Elimina una comuna por su nombre
     * @param name Nombre de comuna a eliminar
     * @return Resultado
     */
    int deleteByNameIgnoreCase(String name);

}
