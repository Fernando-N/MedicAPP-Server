package cl.medicapp.service.services.role;

import cl.medicapp.service.document.RoleDocument;
import cl.medicapp.service.dto.GenericResponseDto;
import cl.medicapp.service.dto.RoleDto;
import cl.medicapp.service.holder.DocumentsHolder;
import cl.medicapp.service.repository.role.RoleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        DocumentsHolder.getInstance().setRoleDocumentList(Collections.singletonList(RoleDocument.builder().name("PARAMEDIC").build()));
    }

    @Test
    void testGetAll() {
        List<RoleDto> result = roleServiceImpl.getAll();
        assertNotNull(result);
    }

    @Test
    void testGetByName() {
        RoleDto result = roleServiceImpl.getByName("PARAMEDIC");
        assertNotNull(result);
    }

    @Test
    void testSave() {
        when(roleRepository.findByNameIgnoreCaseEndsWith(anyString())).thenReturn(Optional.empty());

        RoleDto result = roleServiceImpl.save(new RoleDto("id", "name"));
        assertNotNull(result);
    }

    @Test
    void testUpdate() {
        RoleDocument roleDocument = RoleDocument.builder().build();
        when(roleRepository.findByNameIgnoreCaseEndsWith(anyString())).thenReturn(Optional.of(roleDocument));

        RoleDto result = roleServiceImpl.update("roleName", new RoleDto("id", "name"));
        assertNotNull(result);
    }

    @Test
    void testDeleteByName() {
        when(roleRepository.deleteByNameIgnoreCase(anyString())).thenReturn(1);

        GenericResponseDto result = roleServiceImpl.deleteByName("ROLE_TEST");
        assertNotNull(result);
    }
}
