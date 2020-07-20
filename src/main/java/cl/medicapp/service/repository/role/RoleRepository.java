package cl.medicapp.service.repository.role;

import cl.medicapp.service.document.RoleDocument;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio de Roles
 */
@Repository
public interface RoleRepository extends PagingAndSortingRepository<RoleDocument, String> {

    /**
     * Buscar todos los roles
     * @return Lista de roles
     */
    List<RoleDocument> findAll();

    /**
     * Busca un rol por su nombre
     * @param role Nombre a buscar
     * @return Lista de roles
     */
    Optional<RoleDocument> findByNameIgnoreCaseEndsWith(String role);

    /**
     * Elimina un rol por su nombre
     * @param name Nombre a eliminar
     * @return Resultado
     */
    int deleteByNameIgnoreCase(String name);

}
