package cl.medicapp.service.services.communes;

import cl.medicapp.service.dto.CommuneDto;
import cl.medicapp.service.dto.GenericResponseDto;

import java.util.List;

public interface CommuneService {

    List<CommuneDto> getAll();

    CommuneDto getByName(String name);

    List<CommuneDto> getCommunesByRegionId(String id);

    CommuneDto save(CommuneDto request);

    CommuneDto update(String communeName, CommuneDto newCommune);

    GenericResponseDto deleteByName(String name);

}
