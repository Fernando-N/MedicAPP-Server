package cl.medicapp.service.services.communes;

import cl.medicapp.service.document.CommuneDocument;
import cl.medicapp.service.document.RegionDocument;
import cl.medicapp.service.dto.CommuneDto;
import cl.medicapp.service.dto.GenericResponseDto;
import cl.medicapp.service.dto.RegionDto;
import cl.medicapp.service.holder.DocumentsHolder;
import cl.medicapp.service.repository.commune.CommuneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

class CommuneServiceImplTest {

    @Mock
    private CommuneRepository communeRepository;

    @InjectMocks
    private CommuneServiceImpl communeServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        DocumentsHolder.getInstance().setCommuneDocumentList(Collections.singletonList(CommuneDocument.builder().region(RegionDocument.builder().id("ID").name("REGION").build()).name("COMUNA").build()));
        DocumentsHolder.getInstance().setRegionDocumentList(Collections.singletonList(RegionDocument.builder().id("ID").name("REGION").build()));
    }

    @Test
    void testGetAll() {
        List<CommuneDto> result = communeServiceImpl.getAll();
        assertNotNull(result);
    }

    @Test
    void testGetByName() {
        CommuneDto result = communeServiceImpl.getByName("COMUNA");
        assertNotNull(result);
    }

    @Test
    void testGetCommunesByRegionId() {
        List<CommuneDto> result = communeServiceImpl.getCommunesByRegionId("ID");
        assertNotNull(result);
    }

    @Test
    void testUpdate() {
        CommuneDto result = communeServiceImpl.update("COMUNA", new CommuneDto("ID", "COMUNA", new RegionDto("ID", "label")));
        assertNotNull(result);
    }

    @Test
    void testDeleteByName() {
        when(communeRepository.deleteByNameIgnoreCase(anyString())).thenReturn(0);

        GenericResponseDto result = communeServiceImpl.deleteByName("name");
        assertNotNull(result);
    }
}
