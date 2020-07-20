package cl.medicapp.service.repository.userdetails;

import cl.medicapp.service.document.CommuneDocument;
import cl.medicapp.service.document.UserDetailsDocument;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio de detalles de usuario
 */
@Repository
public interface UserDetailsDocumentRepository extends PagingAndSortingRepository<UserDetailsDocument, String> {

    /**
     * Buscar todos los usuarios de una lista de comunas
     * @param communes Lista de comunas a buscar
     * @return Lista de detalles de usuario
     */
    List<UserDetailsDocument> findAllByCommuneIn(List<CommuneDocument> communes);

    /**
     * Busca todos los usuarios por nombre y apellido
     * @param firstName Nombre
     * @param lastName Apellido
     * @return Lista de usuarios encontrados
     */
    List<UserDetailsDocument> findByFirstNameAndLastName(String firstName, String lastName);

}
