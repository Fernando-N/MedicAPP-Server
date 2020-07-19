package cl.medicapp.service.repository.userdetails;

import cl.medicapp.service.document.CommuneDocument;
import cl.medicapp.service.document.UserDetailsDocument;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio de Usuarios
 */
@Repository
public interface UserDetailsDocumentRepository extends PagingAndSortingRepository<UserDetailsDocument, String> {

    List<UserDetailsDocument> findAllByCommuneIn(List<CommuneDocument> communes);

    List<UserDetailsDocument> findByFirstNameAndLastName(String firstName, String lastName);

}
