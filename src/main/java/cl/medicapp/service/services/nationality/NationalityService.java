package cl.medicapp.service.services.nationality;

import cl.medicapp.service.dto.GenericResponseDto;
import cl.medicapp.service.dto.NationalityDto;

import java.util.List;

/**
 * Interfaz de servicio de nacionalidades
 */
public interface NationalityService {

    /**
     * @see NationalityServiceImpl#getAll()
     */
    List<NationalityDto> getAll();

    /**
     * @see NationalityServiceImpl#getByName(String)
     */
    NationalityDto getByName(String name);

    /**
     * @see NationalityServiceImpl#save(NationalityDto)
     */
    NationalityDto save(NationalityDto request);

    /**
     * @see NationalityServiceImpl#update(String, NationalityDto)
     */
    NationalityDto update(String nationalityName, NationalityDto newNationality);

    /**
     * @see NationalityServiceImpl#deleteByName(String)
     */
    GenericResponseDto deleteByName(String name);

}
