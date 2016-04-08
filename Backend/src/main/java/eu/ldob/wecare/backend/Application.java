package eu.ldob.wecare.backend;

import eu.ldob.wecare.backend.setup.ResetDatabase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);

        ResetDatabase.execute();
    }



}
