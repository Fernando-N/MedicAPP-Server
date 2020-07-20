package cl.medicapp.service.services.nationality;

import cl.medicapp.service.document.NationalityDocument;
import cl.medicapp.service.dto.GenericResponseDto;
import cl.medicapp.service.dto.NationalityDto;
import cl.medicapp.service.repository.nationality.NationalityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

class NationalityServiceImplTest {
    @Mock
    NationalityRepository nationalityRepository;
    @InjectMocks
    NationalityServiceImpl nationalityServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAll() {
        when(nationalityRepository.findAll()).thenReturn(Arrays.<NationalityDocument>asList(new NationalityDocument("id", "name")));

        List<NationalityDto> result = nationalityServiceImpl.getAll();
        Assertions.assertEquals(Arrays.<NationalityDto>asList(new NationalityDto("value", "label")), result);
    }

    @Test
    void testGetByName() {
        when(nationalityRepository.findByNameIgnoreCase(anyString())).thenReturn(null);

        NationalityDto result = nationalityServiceImpl.getByName("name");
        Assertions.assertEquals(new NationalityDto("value", "label"), result);
    }

    @Test
    void testSave() {
        when(nationalityRepository.findByNameIgnoreCase(anyString())).thenReturn(null);

        NationalityDto result = nationalityServiceImpl.save(new NationalityDto("value", "label"));
        Assertions.assertEquals(new NationalityDto("value", "label"), result);
    }

    @Test
    void testUpdate() {
        when(nationalityRepository.findByNameIgnoreCase(anyString())).thenReturn(null);

        NationalityDto result = nationalityServiceImpl.update("regionName", new NationalityDto("value", "label"));
        Assertions.assertEquals(new NationalityDto("value", "label"), result);
    }

    @Test
    void testDeleteByName() {
        when(nationalityRepository.deleteByNameIgnoreCase(anyString())).thenReturn(0);

        GenericResponseDto result = nationalityServiceImpl.deleteByName("name");
        Assertions.assertEquals(new GenericResponseDto("message", Arrays.<String>asList("String")), result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme