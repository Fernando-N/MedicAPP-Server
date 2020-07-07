package cl.medicapp.service.services.nationality;

import cl.medicapp.service.dto.GenericResponseDto;
import cl.medicapp.service.dto.NationalityDto;

import java.util.List;

public interface NationalityService {

    List<NationalityDto> getAll();

    NationalityDto getByName(String name);

    NationalityDto save(NationalityDto request);

    NationalityDto update(String nationalityName, NationalityDto newNationality);

    GenericResponseDto deleteByName(String name);

}
