package cl.medicapp.service.repository.nationality;

import cl.medicapp.service.document.NationalityDocument;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio de Nacionalidades
 */
@Repository
public interface NationalityRepository extends PagingAndSortingRepository<NationalityDocument, String> {

    /**
     * Buscar todas las nacionalidades
     * @return Lista de nacionalidades
     */
    List<NationalityDocument> findAll();

    /**
     * Buscar una nacionalidad por su nombre
     * @param name Nombre a buscar
     * @return Lista de nacionalidades
     */
    Optional<NationalityDocument> findByNameIgnoreCase(String name);

    /**
     * Elimina una nacionalidad por su nombre
     * @param name Nombre a eliminar
     * @return Resultado
     */
    int deleteByNameIgnoreCase(String name);

}
