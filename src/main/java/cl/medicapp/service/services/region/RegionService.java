package cl.medicapp.service.services.region;

import cl.medicapp.service.dto.GenericResponseDto;
import cl.medicapp.service.dto.RegionDto;

import java.util.List;

/**
 * Interfaz de servicio de regiones
 */
public interface RegionService {

    /**
     * @see RegionServiceImpl#getAll()
     */
    List<RegionDto> getAll();

    /**
     * @see RegionServiceImpl#getByName(String)
     */
    RegionDto getByName(String name);

    /**
     * @see RegionServiceImpl#save(RegionDto)
     */
    RegionDto save(RegionDto request);

    /**
     * @see RegionServiceImpl#update(String, RegionDto)
     */
    RegionDto update(String regionName, RegionDto newRegion);

    /**
     * @see RegionServiceImpl#deleteByName(String)
     */
    GenericResponseDto deleteByName(String name);

}
