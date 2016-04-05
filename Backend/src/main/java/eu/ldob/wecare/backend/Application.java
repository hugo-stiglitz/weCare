package eu.ldob.wecare.backend;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.config.Neo4jConfiguration;

@SpringBootApplication
@EnableNeo4jRepositories
public class Application extends Neo4jConfiguration {

    public Application() {
        setBasePackage("eu/ldob/wecare");
    }

    @Bean(destroyMethod = "shutdown")
    public GraphDatabaseService graphDatabaseService() {
        return new GraphDatabaseFactory().newEmbeddedDatabase("database/test.db");
        //return new GraphDatabaseFactory().newEmbeddedDatabase("database/productive.db");
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
