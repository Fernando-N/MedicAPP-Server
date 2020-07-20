package cl.medicapp.service.services.location;

import cl.medicapp.service.dto.LocationDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import static org.mockito.Mockito.*;

class LocationServiceImplTest {
    @Mock
    Logger log;
    @InjectMocks
    LocationServiceImpl locationServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetLongitudeAndLatitude() {
        LocationDto result = locationServiceImpl.getLongitudeAndLatitude("address");
        Assertions.assertEquals(new LocationDto(0d, 0d, "address"), result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme