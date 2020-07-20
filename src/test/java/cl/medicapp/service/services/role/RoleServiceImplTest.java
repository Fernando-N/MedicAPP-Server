package cl.medicapp.service.services.role;

import cl.medicapp.service.document.RoleDocument;
import cl.medicapp.service.dto.GenericResponseDto;
import cl.medicapp.service.dto.RoleDto;
import cl.medicapp.service.repository.role.RoleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

class RoleServiceImplTest {
    @Mock
    RoleRepository roleRepository;
    @InjectMocks
    RoleServiceImpl roleServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAll() {
        when(roleRepository.findAll()).thenReturn(Arrays.<RoleDocument>asList(new RoleDocument("id", "name")));

        List<RoleDto> result = roleServiceImpl.getAll();
        Assertions.assertEquals(Arrays.<RoleDto>asList(new RoleDto("id", "name")), result);
    }

    @Test
    void testGetByName() {
        when(roleRepository.findByNameIgnoreCaseEndsWith(anyString())).thenReturn(null);

        RoleDto result = roleServiceImpl.getByName("name");
        Assertions.assertEquals(new RoleDto("id", "name"), result);
    }

    @Test
    void testSave() {
        when(roleRepository.findByNameIgnoreCaseEndsWith(anyString())).thenReturn(null);

        RoleDto result = roleServiceImpl.save(new RoleDto("id", "name"));
        Assertions.assertEquals(new RoleDto("id", "name"), result);
    }

    @Test
    void testUpdate() {
        when(roleRepository.findByNameIgnoreCaseEndsWith(anyString())).thenReturn(null);

        RoleDto result = roleServiceImpl.update("roleName", new RoleDto("id", "name"));
        Assertions.assertEquals(new RoleDto("id", "name"), result);
    }

    @Test
    void testDeleteByName() {
        when(roleRepository.deleteByNameIgnoreCase(anyString())).thenReturn(0);

        GenericResponseDto result = roleServiceImpl.deleteByName("name");
        Assertions.assertEquals(new GenericResponseDto("message", Arrays.<String>asList("String")), result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme