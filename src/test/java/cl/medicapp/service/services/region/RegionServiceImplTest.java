package cl.medicapp.service.services.region;

import cl.medicapp.service.document.RegionDocument;
import cl.medicapp.service.dto.GenericResponseDto;
import cl.medicapp.service.dto.RegionDto;
import cl.medicapp.service.holder.DocumentsHolder;
import cl.medicapp.service.repository.region.RegionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

class RegionServiceImplTest {

    @Mock
    private RegionRepository regionRepository;

    @InjectMocks
    private RegionServiceImpl regionServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        DocumentsHolder.getInstance().setRegionDocumentList(Collections.singletonList(RegionDocument.builder().name("name").build()));
    }

    @Test
    void testGetAll() {
        List<RegionDto> result = regionServiceImpl.getAll();
        assertNotNull(result);
    }

    @Test
    void testGetByName() {
        RegionDto result = regionServiceImpl.getByName("name");
        assertNotNull(result);
    }

    @Test
    void testSave() {
        when(regionRepository.findByNameIgnoreCase(anyString())).thenReturn(Optional.empty());

        RegionDto result = regionServiceImpl.save(new RegionDto("id", "label"));
        assertNotNull(result);
    }

    @Test
    void testUpdate() {
        RegionDocument regionDocument = RegionDocument.builder().name("regionName").build();
        when(regionRepository.findByNameIgnoreCase(anyString())).thenReturn(Optional.of(regionDocument));

        RegionDto result = regionServiceImpl.update("regionName", new RegionDto("id", "label"));
        assertNotNull(result);
    }

    @Test
    void testDeleteByName() {
        when(regionRepository.deleteByNameIgnoreCase(anyString())).thenReturn(0);

        GenericResponseDto result = regionServiceImpl.deleteByName("name");
        assertNotNull(result);
    }
}
