package cl.medicapp.service.services.nationality;

import cl.medicapp.service.document.NationalityDocument;
import cl.medicapp.service.dto.GenericResponseDto;
import cl.medicapp.service.dto.NationalityDto;
import cl.medicapp.service.holder.DocumentsHolder;
import cl.medicapp.service.repository.nationality.NationalityRepository;
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

class NationalityServiceImplTest {

    @Mock
    private NationalityRepository nationalityRepository;
    @InjectMocks
    private NationalityServiceImpl nationalityServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        DocumentsHolder.getInstance().setNationalityDocumentList(Collections.singletonList(NationalityDocument.builder().name("name").build()));
    }

    @Test
    void testGetAll() {
        List<NationalityDto> result = nationalityServiceImpl.getAll();
        assertNotNull(result);
    }

    @Test
    void testGetByName() {
        NationalityDto result = nationalityServiceImpl.getByName("name");
        assertNotNull(result);
    }

    @Test
    void testSave() {
        when(nationalityRepository.findByNameIgnoreCase(anyString())).thenReturn(Optional.empty());
        NationalityDto result = nationalityServiceImpl.save(new NationalityDto("id", "name"));
        assertNotNull(result);
    }

    @Test
    void testUpdate() {
        NationalityDocument nationalityDocument = NationalityDocument.builder().name("name").build();
        when(nationalityRepository.findByNameIgnoreCase(anyString())).thenReturn(Optional.of(nationalityDocument));
        NationalityDto result = nationalityServiceImpl.update("name", new NationalityDto("id", "name"));
        assertNotNull(result);
    }

    @Test
    void testDeleteByName() {
        when(nationalityRepository.deleteByNameIgnoreCase(anyString())).thenReturn(0);
        GenericResponseDto result = nationalityServiceImpl.deleteByName("name");
        assertNotNull(result);
    }
}
