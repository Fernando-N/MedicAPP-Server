package cl.medicapp.service.configuration;

import cl.medicapp.service.holder.DocumentsHolder;
import cl.medicapp.service.repository.commune.CommuneRepository;
import cl.medicapp.service.repository.nationality.NationalityRepository;
import cl.medicapp.service.repository.region.RegionRepository;
import cl.medicapp.service.repository.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
@Configuration
public class HoldersInitConfiguration {

    private final RoleRepository roleRepository;
    private final RegionRepository regionRepository;
    private final CommuneRepository communeRepository;
    private final NationalityRepository nationalityRepository;

    @PostConstruct
    public void onInit() {
        DocumentsHolder.getInstance().setRoleDocumentList(roleRepository.findAll());
        DocumentsHolder.getInstance().setRegionDocumentList(regionRepository.findAll());
        DocumentsHolder.getInstance().setCommuneDocumentList(communeRepository.findAll());
        DocumentsHolder.getInstance().setNationalityDocumentList(nationalityRepository.findAll());
    }


}
