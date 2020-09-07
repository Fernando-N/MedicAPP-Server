package cl.medicapp.service.services.communes;

import cl.medicapp.service.annotation.Capitalize;
import cl.medicapp.service.annotation.FormatArgs;
import cl.medicapp.service.constants.Constants;
import cl.medicapp.service.document.CommuneDocument;
import cl.medicapp.service.document.RegionDocument;
import cl.medicapp.service.dto.CommuneDto;
import cl.medicapp.service.dto.GenericResponseDto;
import cl.medicapp.service.holder.DocumentsHolder;
import cl.medicapp.service.repository.commune.CommuneRepository;
import cl.medicapp.service.repository.region.RegionRepository;
import cl.medicapp.service.util.CommuneUtil;
import cl.medicapp.service.util.GenericResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementacion de servicio de comunas
 */
@Service
@RequiredArgsConstructor
public class CommuneServiceImpl implements CommuneService {

    /**
     * Bean de repositorio de comunas
     */
    private final CommuneRepository communeRepository;

    /**
     * Obtiene todas las comunas
     * @return Lista de comunas
     */
    @Override
    public List<CommuneDto> getAll() {
        return DocumentsHolder.getInstance().getCommuneDocumentList()
                .stream()
                .map(CommuneUtil::toCommuneDto)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene una comuna por nombre
     * @param name Nombre de comuna a buscar
     * @return Comuna encontrada
     */
    @Override
    public CommuneDto getByName(String name) {
        return DocumentsHolder.getInstance().getCommuneDocumentList()
                .stream()
                .filter(commune -> commune.getName().equalsIgnoreCase(name))
                .findFirst()
                .map(CommuneUtil::toCommuneDto)
                .orElseThrow(() -> GenericResponseUtil.buildGenericException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.getReasonPhrase(), String.format("Commune %s NOT FOUND!", name)));
    }

    /**
     * Obtiene comunas de una region
     * @param regionId id de region
     * @return Lista de comunas encontradas
     */
    @Override
    public List<CommuneDto> getCommunesByRegionId(String regionId) {
        RegionDocument regionFind = DocumentsHolder.getInstance().getRegionDocumentList()
                .stream()
                .filter(region -> region.getId().equalsIgnoreCase(regionId))
                .findFirst()
                .orElseThrow(GenericResponseUtil::getGenericException);

        return DocumentsHolder.getInstance().getCommuneDocumentList()
                .stream()
                .filter(commune -> commune.getRegion().equals(regionFind))
                .map(CommuneUtil::toCommuneDto)
                .collect(Collectors.toList());
    }

    /**
     * Guardar nueva comuna
     * @param request Objeto de comuna a guardar
     * @return Comuna guardada
     */
    @Override
    @FormatArgs
    public CommuneDto save(CommuneDto request) {
        Optional<CommuneDocument> communeOp = DocumentsHolder.getInstance().getCommuneDocumentList()
                .stream()
                .filter(commune -> commune.getName().equalsIgnoreCase(request.getName()))
                .findAny();

        Optional<RegionDocument> regionOp = DocumentsHolder.getInstance().getRegionDocumentList()
                .stream()
                .filter(region -> region.getId().equalsIgnoreCase(request.getRegion().getId()))
                .findAny();

        if (communeOp.isPresent() || !regionOp.isPresent()) {
            throw GenericResponseUtil.buildGenericException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(), String.format("Commune %s already exist!", request.getName()));
        }

        CommuneDocument save = communeRepository.save(CommuneUtil.toCommuneDocument(request, regionOp.get()));
        DocumentsHolder.getInstance().getCommuneDocumentList().add(save);

        return request;
    }

    /**
     * Modifica el nombre de una comuna
     * @param communeName Target
     * @param newCommune Nuevo nombre
     * @return Comuna modificada
     */
    @Override
    @FormatArgs
    public CommuneDto update(@Capitalize String communeName, CommuneDto newCommune) {
        Optional<CommuneDocument> communeOp = DocumentsHolder.getInstance().getCommuneDocumentList()
                .stream()
                .filter(commune -> commune.getName().equalsIgnoreCase(communeName))
                .findAny();

        if (!communeOp.isPresent()) {
            throw GenericResponseUtil.buildGenericException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(), String.format("Commune %s already exist!", newCommune.getName()));
        }

        communeOp.get().setName(newCommune.getName());
        communeRepository.save(communeOp.get());

        return newCommune;
    }

    /**
     * Eliminar comuna por su nombre
     * @param name Nombre de comuna
     * @return Resultado
     */
    @Override
    @FormatArgs
    public GenericResponseDto deleteByName(@Capitalize String name) {
        if (communeRepository.deleteByNameIgnoreCase(name) > 0) {
            return GenericResponseUtil.buildGenericResponse(Constants.ROLE_DELETED, String.format(Constants.ROLE_X_DELETED, name));
        }
        return GenericResponseUtil.buildGenericResponse(HttpStatus.NOT_FOUND.getReasonPhrase(), String.format(Constants.ROLE_X_NOT_FOUND, name));
    }
}
