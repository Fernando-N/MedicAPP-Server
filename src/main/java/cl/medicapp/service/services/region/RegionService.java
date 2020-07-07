package cl.medicapp.service.services.region;

import cl.medicapp.service.dto.GenericResponseDto;
import cl.medicapp.service.dto.RegionDto;

import java.util.List;

public interface RegionService {

    List<RegionDto> getAll();

    RegionDto getByName(String name);

    RegionDto save(RegionDto request);

    RegionDto update(String regionName, RegionDto newRegion);

    GenericResponseDto deleteByName(String name);

}
