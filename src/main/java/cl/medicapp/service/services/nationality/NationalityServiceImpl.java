package cl.medicapp.service.services.nationality;

import cl.medicapp.service.annotation.Capitalize;
import cl.medicapp.service.annotation.FormatArgs;
import cl.medicapp.service.constants.Constants;
import cl.medicapp.service.document.NationalityDocument;
import cl.medicapp.service.dto.GenericResponseDto;
import cl.medicapp.service.dto.NationalityDto;
import cl.medicapp.service.repository.nationality.NationalityRepository;
import cl.medicapp.service.util.GenericResponseUtil;
import cl.medicapp.service.util.NationalityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
//TODO aplicar logica utilizando la clase DocumentsHolder para evitar carga a la bd
public class NationalityServiceImpl implements NationalityService {

    private final NationalityRepository nationalityRepository;

    @Override
    public List<NationalityDto> getAll() {
        List<NationalityDto> nationalityList = new ArrayList<>();
        nationalityRepository.findAll().forEach(nationality -> nationalityList.add(NationalityUtil.toNationalityDto(nationality)));
        return nationalityList;
    }

    @Override
    public NationalityDto getByName(String name) {
        return nationalityRepository.findByNameIgnoreCase(name).map(NationalityUtil::toNationalityDto)
                .orElseThrow(() -> GenericResponseUtil.buildGenericException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.getReasonPhrase(), String.format("Nationality %s NOT FOUND!", name)));
    }

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

    @Override
    @FormatArgs
    public GenericResponseDto deleteByName(@Capitalize String name) {
        if (nationalityRepository.deleteByNameIgnoreCase(name) > 0) {
            return GenericResponseUtil.buildGenericResponse(Constants.ROLE_DELETED, String.format(Constants.ROLE_X_DELETED, name));
        }
        return GenericResponseUtil.buildGenericResponse(HttpStatus.NOT_FOUND.getReasonPhrase(), String.format(Constants.ROLE_X_NOT_FOUND, name));
    }
}
