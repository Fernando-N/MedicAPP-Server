package cl.medicapp.service.repository.region;

import cl.medicapp.service.document.RegionDocument;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio de regiones
 */
@Repository
public interface RegionRepository extends PagingAndSortingRepository<RegionDocument, String> {

    /**
     * Buscar todas las regiones
     * @return Lista de regiones
     */
    List<RegionDocument> findAll();

    /**
     * Buscar region por nombre
     * @param name Nombre a buscar
     * @return Region encontrada
     */
    Optional<RegionDocument> findByNameIgnoreCase(String name);

    /**
     * Eliminar una region por su nombre
     * @param name Nombre de region a eliminar
     * @return Resultado
     */
    int deleteByNameIgnoreCase(String name);

}
