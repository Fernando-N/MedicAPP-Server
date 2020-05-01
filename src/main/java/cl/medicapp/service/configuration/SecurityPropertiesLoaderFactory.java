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

@Slf4j
public class SecurityPropertiesLoaderFactory implements PropertySourceFactory {

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

    private void logProperties(List<PropertySource<?>> propertySources) {
        propertySources.forEach(propertySource -> ((Map<String, Object>) propertySource.getSource()).forEach(this::validateAndLogProperty));
    }

    private void validateAndLogProperty(String key, Object value) {
        if (key.contains("auth.")) {
            log.info(key + ": ************");
        } else {
            log.info(key + ": " + value.toString().replace("\n", ""));
        }
    }

}
