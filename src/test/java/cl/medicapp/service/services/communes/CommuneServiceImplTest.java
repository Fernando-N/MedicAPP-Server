package cl.medicapp.service.services.communes;

import cl.medicapp.service.document.CommuneDocument;
import cl.medicapp.service.dto.CommuneDto;
import cl.medicapp.service.dto.GenericResponseDto;
import cl.medicapp.service.dto.RegionDto;
import cl.medicapp.service.repository.commune.CommuneRepository;
import cl.medicapp.service.repository.region.RegionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

class CommuneServiceImplTest {
    @Mock
    CommuneRepository communeRepository;
    @Mock
    RegionRepository regionRepository;
    @InjectMocks
    CommuneServiceImpl communeServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAll() {
        List<CommuneDto> result = communeServiceImpl.getAll();
        Assertions.assertEquals(Arrays.<CommuneDto>asList(new CommuneDto("value", "label", new RegionDto("value", "label"))), result);
    }

    @Test
    void testGetByName() {
        when(communeRepository.findByNameIgnoreCase(anyString())).thenReturn(null);

        CommuneDto result = communeServiceImpl.getByName("name");
        Assertions.assertEquals(new CommuneDto("value", "label", new RegionDto("value", "label")), result);
    }

    @Test
    void testGetCommunesByRegionId() {
        when(communeRepository.findAllByRegion(any())).thenReturn(Arrays.<CommuneDocument>asList(new CommuneDocument("id", "name", null)));

        List<CommuneDto> result = communeServiceImpl.getCommunesByRegionId("id");
        Assertions.assertEquals(Arrays.<CommuneDto>asList(new CommuneDto("value", "label", new RegionDto("value", "label"))), result);
    }

    @Test
    void testSave() {
        when(communeRepository.findByNameIgnoreCase(anyString())).thenReturn(null);

        CommuneDto result = communeServiceImpl.save(new CommuneDto("value", "label", new RegionDto("value", "label")));
        Assertions.assertEquals(new CommuneDto("value", "label", new RegionDto("value", "label")), result);
    }

    @Test
    void testUpdate() {
        when(communeRepository.findByNameIgnoreCase(anyString())).thenReturn(null);

        CommuneDto result = communeServiceImpl.update("communeName", new CommuneDto("value", "label", new RegionDto("value", "label")));
        Assertions.assertEquals(new CommuneDto("value", "label", new RegionDto("value", "label")), result);
    }

    @Test
    void testDeleteByName() {
        when(communeRepository.deleteByNameIgnoreCase(anyString())).thenReturn(0);

        GenericResponseDto result = communeServiceImpl.deleteByName("name");
        Assertions.assertEquals(new GenericResponseDto("message", Arrays.<String>asList("String")), result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme