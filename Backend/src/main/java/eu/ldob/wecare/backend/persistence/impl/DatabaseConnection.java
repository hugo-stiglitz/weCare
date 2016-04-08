package eu.ldob.wecare.backend.persistence.impl;

import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

public class DatabaseConnection {

    private static EmbeddedDatabase database;

    private DatabaseConnection() { }

    public static EmbeddedDatabase getConnection() {

        if(database == null) {
            database = new EmbeddedDatabaseBuilder()
                    .setType(EmbeddedDatabaseType.HSQL)
                    .setName("wecare_test_db")
                    //.setName("wecare_work_db")
                    .build();
        }

        return database;
    }
}
