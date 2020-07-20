package cl.medicapp.service.services.nationality;

import cl.medicapp.service.annotation.Capitalize;
import cl.medicapp.service.annotation.FormatArgs;
import cl.medicapp.service.constants.Constants;
import cl.medicapp.service.document.NationalityDocument;
import cl.medicapp.service.dto.GenericResponseDto;
import cl.medicapp.service.dto.NationalityDto;
import cl.medicapp.service.holder.DocumentsHolder;
import cl.medicapp.service.repository.nationality.NationalityRepository;
import cl.medicapp.service.util.GenericResponseUtil;
import cl.medicapp.service.util.NationalityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementacion de servicio de nacionalidad
 */
@Service
@RequiredArgsConstructor
public class NationalityServiceImpl implements NationalityService {

    /**
     * Bean de repositorio de nacionalidad
     */
    private final NationalityRepository nationalityRepository;

    /**
     * Obtiene todas las nacionalidades
     * @return Lista de nacionalidades
     */
    @Override
    public List<NationalityDto> getAll() {
        return DocumentsHolder.getInstance().getNationalityDocumentList()
                .stream()
                .map(NationalityUtil::toNationalityDto)
                .collect(Collectors.toList());
    }

    /**
     * Obtener una nacionalidad por su nombre
     * @param name Nombre
     * @return Nacionalidad encontrada
     */
    @Override
    public NationalityDto getByName(String name) {
        return DocumentsHolder.getInstance().getNationalityDocumentList()
                .stream()
                .filter(nationality -> nationality.getName().equalsIgnoreCase(name))
                .findFirst()
                .map(NationalityUtil::toNationalityDto)
                .orElseThrow(() -> GenericResponseUtil.buildGenericException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.getReasonPhrase(), String.format("Nationality %s NOT FOUND!", name)));
    }

    /**
     * Guardar una nacionalidad
     * @param request Objeto a guardar
     * @return Objeto guardado
     */
    @Override
    @FormatArgs
    public NationalityDto save(NationalityDto request) {
        Optional<NationalityDocument> nationality = nationalityRepository.findByNameIgnoreCase(request.getName());

        if (nationality.isPresent()) {
            throw GenericResponseUtil.buildGenericException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(), String.format("Nationality %s already exist!", request.getName()));
        }

        nationalityRepository.save(NationalityUtil.toNationalityDocument(request));
        return request;
    }

    /**
     * Actualiza una nacionalidad
     * @param regionName Nombre de nacionalidad
     * @param newRegion Objeto con cambios
     * @return Objeto actualizado
     */
    @Override
    @FormatArgs
    public NationalityDto update(@Capitalize String regionName, NationalityDto newRegion) {
        Optional<NationalityDocument> nationality = nationalityRepository.findByNameIgnoreCase(regionName);

        if (!nationality.isPresent()) {
            throw GenericResponseUtil.buildGenericException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.getReasonPhrase(), String.format(Constants.ROLE_X_NOT_FOUND, regionName));
        }

        nationality.get().setName(newRegion.getName());
        nationalityRepository.save(nationality.get());

        return newRegion;
    }

    /**
     * Elimina una nacionalidad por su nombre
     * @param name Nombre de nacionalidad
     * @return Lista de nacionalidades
     */
    @Override
    @FormatArgs
    public GenericResponseDto deleteByName(@Capitalize String name) {
        if (nationalityRepository.deleteByNameIgnoreCase(name) > 0) {
            return GenericResponseUtil.buildGenericResponse(Constants.ROLE_DELETED, String.format(Constants.ROLE_X_DELETED, name));
        }
        return GenericResponseUtil.buildGenericResponse(HttpStatus.NOT_FOUND.getReasonPhrase(), String.format(Constants.ROLE_X_NOT_FOUND, name));
    }
}
