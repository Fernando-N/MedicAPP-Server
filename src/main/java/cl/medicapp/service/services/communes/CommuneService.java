package cl.medicapp.service.services.communes;

import cl.medicapp.service.dto.CommuneDto;
import cl.medicapp.service.dto.GenericResponseDto;

import java.util.List;

/**
 * Interfaz de servicio de comuna
 */
public interface CommuneService {

    /**
     * @see CommuneService#getAll()
     */
    List<CommuneDto> getAll();

    /**
     * @see CommuneService#getByName(String)
     */
    CommuneDto getByName(String name);

    /**
     * @see CommuneService#getCommunesByRegionId(String)
     */
    List<CommuneDto> getCommunesByRegionId(String id);

    /**
     * @see CommuneService#save(CommuneDto)
     */
    CommuneDto save(CommuneDto request);

    /**
     * @see CommuneService#update(String, CommuneDto)
     */
    CommuneDto update(String communeName, CommuneDto newCommune);

    /**
     * @see CommuneService#deleteByName(String)
     */
    GenericResponseDto deleteByName(String name);

}
