package cl.medicapp.service.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Clase de configuración encargada de cargar properties
 */
@Slf4j
public class SecurityPropertiesLoaderFactory implements PropertySourceFactory {

    /**
     * Método encargado de cargar properties a source
     * @param s --
     * @param encodedResource Archivo a cargar
     * @return PropertySource con properties cargados
     * @throws IOException En caso de que el yml este con mal formato
     */
    @Override
    public PropertySource<?> createPropertySource(String s, EncodedResource encodedResource) throws IOException {
        log.info("************************************************************");
        log.info("Properties loader");
        log.info("Loading file: " + encodedResource.getResource().getFilename() + " init");
        long init = System.currentTimeMillis();
        List<PropertySource<?>> sources = new YamlPropertySourceLoader().load(encodedResource.getResource().getFilename(), encodedResource.getResource());
        if (StringUtils.isEmpty(sources)) return null;
        logProperties(sources);
        log.info("File: " + encodedResource.getResource().getFilename() + " loaded in {}ms", (System.currentTimeMillis() - init));
        log.info("************************************************************");
        return sources.get(0);
    }

    /**
     * Encargado de loggear los properties cargados
     * @param propertySources Lista de properties a loggear
     */
    private void logProperties(List<PropertySource<?>> propertySources) {
        propertySources.forEach(propertySource -> ((Map<?, ?>) propertySource.getSource()).forEach(this::validateAndLogProperty));
    }

    /**
     * Ejecuta log.info filtrando si contiene "auth."
     * @param key Llave
     * @param value Valor
     */
    private void validateAndLogProperty(Object key, Object value) {
        if (key instanceof String && ((String) key).contains("auth.")) {
            log.info(key + ": ************");
        } else {
            log.info(key + ": " + value.toString().replace("\n", ""));
        }
    }

}
