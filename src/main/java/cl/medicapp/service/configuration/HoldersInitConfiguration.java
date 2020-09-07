package cl.medicapp.service.configuration;

import cl.medicapp.service.holder.DocumentsHolder;
import cl.medicapp.service.repository.commune.CommuneRepository;
import cl.medicapp.service.repository.nationality.NationalityRepository;
import cl.medicapp.service.repository.region.RegionRepository;
import cl.medicapp.service.repository.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Clase de configuración de clase DocumentsHolder
 */
@RequiredArgsConstructor
@Configuration
public class HoldersInitConfiguration {

    /**
     * Bean repositorio de roles
     */
    private final RoleRepository roleRepository;

    /**
     * Bean repositorio de regiones
     */
    private final RegionRepository regionRepository;

    /**
     * Bean repositorio de comunas
     */
    private final CommuneRepository communeRepository;

    /**
     * Bean repositorio de nacionalidades
     */
    private final NationalityRepository nationalityRepository;

    /**
     * Método inicial que busca en los repositorios todos los datos y los guarda en la instancia estatica
     */
    @PostConstruct
    public void onInit() {
        DocumentsHolder.getInstance().setRoleDocumentList(roleRepository.findAll());
        DocumentsHolder.getInstance().setRegionDocumentList(regionRepository.findAll());
        DocumentsHolder.getInstance().setCommuneDocumentList(communeRepository.findAll());
        DocumentsHolder.getInstance().setNationalityDocumentList(nationalityRepository.findAll());
    }


}
