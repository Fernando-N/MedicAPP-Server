package cl.medicapp.service.services.region;

import cl.medicapp.service.document.RegionDocument;
import cl.medicapp.service.dto.GenericResponseDto;
import cl.medicapp.service.dto.RegionDto;
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

class RegionServiceImplTest {
    @Mock
    RegionRepository regionRepository;
    @InjectMocks
    RegionServiceImpl regionServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAll() {
        when(regionRepository.findAll()).thenReturn(Arrays.<RegionDocument>asList(new RegionDocument("id", "name", "ordinal")));

        List<RegionDto> result = regionServiceImpl.getAll();
        Assertions.assertEquals(Arrays.<RegionDto>asList(new RegionDto("value", "label")), result);
    }

    @Test
    void testGetByName() {
        when(regionRepository.findByNameIgnoreCase(anyString())).thenReturn(null);

        RegionDto result = regionServiceImpl.getByName("name");
        Assertions.assertEquals(new RegionDto("value", "label"), result);
    }

    @Test
    void testSave() {
        when(regionRepository.findByNameIgnoreCase(anyString())).thenReturn(null);

        RegionDto result = regionServiceImpl.save(new RegionDto("value", "label"));
        Assertions.assertEquals(new RegionDto("value", "label"), result);
    }

    @Test
    void testUpdate() {
        when(regionRepository.findByNameIgnoreCase(anyString())).thenReturn(null);

        RegionDto result = regionServiceImpl.update("regionName", new RegionDto("value", "label"));
        Assertions.assertEquals(new RegionDto("value", "label"), result);
    }

    @Test
    void testDeleteByName() {
        when(regionRepository.deleteByNameIgnoreCase(anyString())).thenReturn(0);

        GenericResponseDto result = regionServiceImpl.deleteByName("name");
        Assertions.assertEquals(new GenericResponseDto("message", Arrays.<String>asList("String")), result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme