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

//TODO aplicar logica utilizando la clase DocumentsHolder para evitar carga a la bd
@Service
@RequiredArgsConstructor
public class CommuneServiceImpl implements CommuneService {

    private final CommuneRepository communeRepository;
    private final RegionRepository regionRepository;

    @Override
    public List<CommuneDto> getAll() {
        return DocumentsHolder.getInstance().getCommuneDocumentList()
                .stream()
                .map(CommuneUtil::toCommuneDto)
                .collect(Collectors.toList());
    }

    @Override
    public CommuneDto getByName(String name) {
        return communeRepository.findByNameIgnoreCase(name).map(CommuneUtil::toCommuneDto)
                .orElseThrow(() -> GenericResponseUtil.buildGenericException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.getReasonPhrase(), String.format("Commune %s NOT FOUND!", name)));
    }

    @Override
    public List<CommuneDto> getCommunesByRegionId(String id) {
        RegionDocument regionFind = regionRepository.findById(id).orElseThrow(GenericResponseUtil::getGenericException);
        return communeRepository.findAllByRegion(regionFind).stream().map(CommuneUtil::toCommuneDto).collect(Collectors.toList());
    }

    @Override
    @FormatArgs
    public CommuneDto save(CommuneDto request) {
        Optional<CommuneDocument> commune = communeRepository.findByNameIgnoreCase(request.getLabel());

        if (commune.isPresent()) {
            throw GenericResponseUtil.buildGenericException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(), String.format("Commune %s already exist!", request.getLabel()));
        }

        communeRepository.save(CommuneUtil.toCommuneDocument(request));

        return request;
    }

    @Override
    @FormatArgs
    public CommuneDto update(@Capitalize String communeName, CommuneDto newCommune) {
        Optional<CommuneDocument> commune = communeRepository.findByNameIgnoreCase(communeName);

        if (!commune.isPresent()) {
            throw GenericResponseUtil.buildGenericException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.getReasonPhrase(), String.format(Constants.ROLE_X_NOT_FOUND, communeName));
        }

        commune.get().setName(newCommune.getLabel());
        communeRepository.save(commune.get());

        return newCommune;
    }

    @Override
    @FormatArgs
    public GenericResponseDto deleteByName(@Capitalize String name) {
        if (communeRepository.deleteByNameIgnoreCase(name) > 0) {
            return GenericResponseUtil.buildGenericResponse(Constants.ROLE_DELETED, String.format(Constants.ROLE_X_DELETED, name));
        }
        return GenericResponseUtil.buildGenericResponse(HttpStatus.NOT_FOUND.getReasonPhrase(), String.format(Constants.ROLE_X_NOT_FOUND, name));
    }
}
