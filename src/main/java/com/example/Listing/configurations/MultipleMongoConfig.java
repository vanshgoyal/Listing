package com.example.Listing.configurations;

import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

@Configuration
public class MultipleMongoConfig {
    @Primary
    @Bean(name = "propertyProperties")
    @ConfigurationProperties(prefix = "spring.data.mongodb.property")
    public MongoProperties getPropertyProps() throws Exception {
        return new MongoProperties();
    }

    @Bean(name = "paramsProperties")
    @ConfigurationProperties(prefix = "spring.data.mongodb.params")
    public MongoProperties getParamProps() throws Exception {
        return new MongoProperties();
    }

    @Primary
    @Bean(name = "propertyMongoTemplate")
    public MongoTemplate propertyMongoTemplate() throws Exception {
        return new MongoTemplate(propertyMongoDatabaseFactory(getPropertyProps()));
    }

    @Bean(name ="paramsMongoTemplate")
    public MongoTemplate paramMongoTemplate() throws Exception {
        return new MongoTemplate(paramsMongoDatabaseFactory(getParamProps()));
    }

    @Primary
    @Bean
    public MongoDatabaseFactory propertyMongoDatabaseFactory(MongoProperties mongo) throws Exception {
        return new SimpleMongoClientDatabaseFactory(
                mongo.getUri()
        );
    }

    @Bean
    public MongoDatabaseFactory paramsMongoDatabaseFactory(MongoProperties mongo) throws Exception {
        return new SimpleMongoClientDatabaseFactory(
                mongo.getUri()
        );
    }

}
