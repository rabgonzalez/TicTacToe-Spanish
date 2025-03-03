package es.iespuertodelacruz.rag.tresenraya.shared.config;


import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
public class DatabaseConfig {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseConfig.class);
    
    @Bean
    @Profile("dev")
    DataSource dataSource() {
        logger.info("Inicializando DataSource para el perfil dev");
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl(
                "jdbc:mysql://localhost:3306/tresenraya?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC");
        dataSource.setUsername("ruben");
        dataSource.setPassword("1q2w3e4r");
        return dataSource;
    }

    @Bean
    @Profile("production")
    DataSource dataSourceProduction() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl(
                "jdbc:mysql://db:3306/tresenraya?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC");
        dataSource.setUsername("root");
        dataSource.setPassword("1q2w3e4r");
        return dataSource;
    }

    @Bean
    @Profile("test")
    DataSource dataSourceTest() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:file:/tmp/tresenrayatest;DB_CLOSE_DELAY=-1");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        return dataSource;
    }

    @Bean
    MongoClient mongoClientProd() {
        return MongoClients.create("mongodb://localhost:27017");
    }

    @Bean(name = "mongoTemplate")
    MongoTemplate mongoTemplateProd() {
        return new MongoTemplate(new SimpleMongoClientDatabaseFactory(mongoClientProd(), "tresenraya"));
    }
}