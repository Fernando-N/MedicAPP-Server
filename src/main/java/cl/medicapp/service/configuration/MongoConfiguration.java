package cl.medicapp.service.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.index.IndexOperations;
import org.springframework.data.mongodb.core.index.MongoPersistentEntityIndexResolver;
import org.springframework.data.mongodb.core.mapping.BasicMongoPersistentEntity;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

/**
 * Clase de configuración de MongoDb
 */
@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableMongoAuditing
public class MongoConfiguration {

    /**
     * Bean MongoTemplate
     */
    private final MongoTemplate mongoTemplate;

    /**
     * Bean MongoConverter
     */
    private final MongoConverter mongoConverter;

    /**
     * Metodo que se encarga de iniciar los indices de mongo
     */
    @EventListener(ApplicationReadyEvent.class)
    public void initIndicesAfterStartup() {
        log.info("************************************************************");
        log.info("MongoDB init indices start");

        long init = System.currentTimeMillis();
        MappingContext<?, ?> mappingContext = mongoConverter.getMappingContext();

        if (mappingContext instanceof MongoMappingContext) {
            MongoMappingContext mongoMappingContext = (MongoMappingContext) mappingContext;
            for (BasicMongoPersistentEntity<?> persistentEntity : mongoMappingContext.getPersistentEntities()) {
                Class<?> clazz = persistentEntity.getType();
                if (clazz.isAnnotationPresent(Document.class)) {
                    MongoPersistentEntityIndexResolver resolver = new MongoPersistentEntityIndexResolver(mongoMappingContext);

                    IndexOperations indexOps = mongoTemplate.indexOps(clazz);
                    resolver.resolveIndexFor(clazz).forEach(indexOps::ensureIndex);
                }
            }
        }
        log.info("MongoDB init indices finish in {}ms", (System.currentTimeMillis() - init));
        log.info("************************************************************");

    }

}
