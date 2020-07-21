package cl.medicapp.service.services.region;

import cl.medicapp.service.annotation.Capitalize;
import cl.medicapp.service.annotation.FormatArgs;
import cl.medicapp.service.constants.Constants;
import cl.medicapp.service.document.RegionDocument;
import cl.medicapp.service.dto.GenericResponseDto;
import cl.medicapp.service.dto.RegionDto;
import cl.medicapp.service.holder.DocumentsHolder;
import cl.medicapp.service.repository.region.RegionRepository;
import cl.medicapp.service.util.GenericResponseUtil;
import cl.medicapp.service.util.RegionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementacion de servicio de regiones
 */
@Service
@RequiredArgsConstructor
public class RegionServiceImpl implements RegionService {

    /**
     * Bean de repositorio de regiones
     */
    private final RegionRepository regionRepository;

    /**
     * Obtener todas las regiones
     * @return Lista de regiones
     */
    @Override
    public List<RegionDto> getAll() {
        return DocumentsHolder.getInstance().getRegionDocumentList()
                .stream()
                .map(RegionUtil::toRegionDto)
                .collect(Collectors.toList());
    }

    /**
     * Obtener una region por su nombre
     * @param name Nombre de region
     * @return Region encontrada
     */
    @Override
    public RegionDto getByName(String name) {
        return DocumentsHolder.getInstance().getRegionDocumentList()
                .stream()
                .filter(region -> region.getName().equalsIgnoreCase(name))
                .findFirst()
                .map(RegionUtil::toRegionDto)
                .orElseThrow(() -> GenericResponseUtil.buildGenericException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.getReasonPhrase(), String.format("Region %s NOT FOUND!", name)));
    }

    /**
     * Guardar region
     * @param request objeto con datos de region
     * @return Region guardada
     */
    @Override
    @FormatArgs
    public RegionDto save(RegionDto request) {
    Optional<RegionDocument> region = regionRepository.findByNameIgnoreCase(request.getLabel());

    if (region.isPresent()){
        throw GenericResponseUtil.buildGenericException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(), String.format("Role %s already exist!", request.getLabel()));
    }

    regionRepository.save(RegionUtil.toRegionDocument(request));

    return request;
    }

    /**
     * Actualizar una region
     * @param regionName Nombre de region
     * @param newRegion Region con cambios
     * @return Region actualizada
     */
    @Override
    @FormatArgs
    public RegionDto update(@Capitalize String regionName, RegionDto newRegion) {
        Optional<RegionDocument> region = regionRepository.findByNameIgnoreCase(regionName);

        if (!region.isPresent()) {
            throw GenericResponseUtil.buildGenericException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.getReasonPhrase(), String.format(Constants.ROLE_X_NOT_FOUND, regionName));
        }

        region.get().setName(newRegion.getLabel());
        regionRepository.save(region.get());

        return newRegion;
    }

    /**
     * Eliminar por nombre
     * @param name Nombre de region
     * @return Resultado
     */
    @Override
    @FormatArgs
    public GenericResponseDto deleteByName(@Capitalize String name) {
        if (regionRepository.deleteByNameIgnoreCase(name) > 0) {
            return GenericResponseUtil.buildGenericResponse(Constants.ROLE_DELETED, String.format(Constants.ROLE_X_DELETED, name));
        }
        return GenericResponseUtil.buildGenericResponse(HttpStatus.NOT_FOUND.getReasonPhrase(), String.format(Constants.ROLE_X_NOT_FOUND, name));
    }
}
