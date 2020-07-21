package cl.medicapp.service.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Clase de configuracion de cliente RestTemplate
 */
@Slf4j
@Configuration
public class RestTemplateConfiguration {

    /**
     * Bean de restTemplate
     * @return
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
