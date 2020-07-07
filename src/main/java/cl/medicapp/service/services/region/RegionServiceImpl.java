package cl.medicapp.service.services.region;

import cl.medicapp.service.annotation.Capitalize;
import cl.medicapp.service.annotation.FormatArgs;
import cl.medicapp.service.constants.Constants;
import cl.medicapp.service.document.RegionDocument;
import cl.medicapp.service.dto.GenericResponseDto;
import cl.medicapp.service.dto.RegionDto;
import cl.medicapp.service.repository.region.RegionRepository;
import cl.medicapp.service.util.GenericResponseUtil;
import cl.medicapp.service.util.RegionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
//TODO aplicar logica utilizando la clase DocumentsHolder para evitar carga a la bd
public class RegionServiceImpl implements RegionService {

    private final RegionRepository regionRepository;

    @Override
    public List<RegionDto> getAll() {
        List<RegionDto> regionList = new ArrayList<>();
        regionRepository.findAll().forEach(region -> regionList.add(RegionUtil.toRegionDto(region)));
        return regionList;
    }

    @Override
    public RegionDto getByName(String name) {
        return regionRepository.findByNameIgnoreCase(name).map(RegionUtil::toRegionDto)
                .orElseThrow(() -> GenericResponseUtil.buildGenericException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.getReasonPhrase(), String.format("Region %s NOT FOUND!", name)));
    }

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

    @Override
    @FormatArgs
    public GenericResponseDto deleteByName(@Capitalize String name) {
        if (regionRepository.deleteByNameIgnoreCase(name) > 0) {
            return GenericResponseUtil.buildGenericResponse(Constants.ROLE_DELETED, String.format(Constants.ROLE_X_DELETED, name));
        }
        return GenericResponseUtil.buildGenericResponse(HttpStatus.NOT_FOUND.getReasonPhrase(), String.format(Constants.ROLE_X_NOT_FOUND, name));
    }
}
