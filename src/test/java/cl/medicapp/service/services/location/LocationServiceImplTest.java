package cl.medicapp.service.services.location;

import cl.medicapp.service.dto.LocationDto;
import cl.medicapp.service.dto.LocationResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LocationServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private LocationServiceImpl locationServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(locationServiceImpl, "apiKey", "key");
        ReflectionTestUtils.setField(locationServiceImpl, "apiUri", "http://test.com/");
    }

    @Test
    void testGetLongitudeAndLatitude() {
        LocationResponseDto.Location location = LocationResponseDto.Location.builder().latitude(0d).longitude(0d).label("address").build();
        LocationResponseDto locationResponseDto = LocationResponseDto.builder().data(Collections.singletonList(location)).build();

        when(restTemplate.getForObject(anyString(), any())).thenReturn(locationResponseDto);
        LocationDto result = locationServiceImpl.getLongitudeAndLatitude("address");
        assertEquals(LocationDto.builder().address("address").latitude(0d).longitude(0d).toString(), result.toString());
    }

    @Test
    void testGetLongitudeAndLatitude2() {
        LocationResponseDto.Location location = LocationResponseDto.Location.builder().latitude(0d).longitude(0d).label("address").build();

        when(restTemplate.getForObject(anyString(), any())).thenReturn(null);
        try {
            locationServiceImpl.getLongitudeAndLatitude("address");
            fail("Método ejecutado con exito");
        }catch (Exception e) {
            assertNotNull(e);
        }
    }

    @Test
    void testGetLongitudeAndLatitudeException() {
        LocationResponseDto.Location location = LocationResponseDto.Location.builder().latitude(0d).longitude(0d).label("address").build();

        when(restTemplate.getForObject(anyString(), any())).thenThrow(HttpClientErrorException.class);

        try {
            locationServiceImpl.getLongitudeAndLatitude("address");
            fail("Método ejecutado con exito");
        }catch (Exception e) {
            assertNotNull(e);
        }
    }

}
