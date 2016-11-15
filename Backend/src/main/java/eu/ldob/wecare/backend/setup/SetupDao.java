package eu.ldob.wecare.backend.setup;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class SetupDao implements ISetupDao {

    private JdbcTemplate template;

    public SetupDao(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public void createTableUser() {
        template.execute("DROP TABLE user IF EXISTS");
        template.execute("CREATE TABLE user(id INTEGER IDENTITY PRIMARY KEY, user_type VARCHAR(63), first_name VARCHAR(63), last_name VARCHAR(63))");
    }

    @Override
    public void createTableOperation() {
        template.execute("DROP TABLE operation IF EXISTS");
        template.execute("DROP TABLE operation_status_timestamp IF EXISTS");
        template.execute("DROP TABLE status IF EXISTS");

        template.execute("CREATE TABLE status(id INTEGER IDENTITY PRIMARY KEY, name VARCHAR(25) UNIQUE)");
        template.execute("CREATE TABLE operation(id INTEGER IDENTITY PRIMARY KEY, external_id VARCHAR(15) UNIQUE, dispatcher_id INTEGER, incident_message VARCHAR(255), incident_patient_lastname VARCHAR(63), incident_patient_firstname VARCHAR(63), incident_patient_age VARCHAR(25), incident_location_address_city VARCHAR(63), incident_location_address_zip INTEGER, incident_location_address_street VARCHAR(63), incident_location_coordinates_lat DOUBLE, incident_location_coordinates_lng DOUBLE)");

        template.execute("CREATE TABLE operation_status_timestamp(id INTEGER IDENTITY PRIMARY KEY, status_id INTEGER NOT NULL, operation_id INTEGER NOT NULL, timestamp TIME)");
        template.execute("ALTER TABLE operation_status_timestamp ADD FOREIGN KEY (status_id) REFERENCES status(id)");
        template.execute("ALTER TABLE operation_status_timestamp ADD FOREIGN KEY (operation_id) REFERENCES operation(id)");

        template.update("INSERT INTO status (name) VALUES('RECEIVED')");
        template.update("INSERT INTO status (name) VALUES('ALARMED')");
        template.update("INSERT INTO status (name) VALUES('ACCEPTED')");
        template.update("INSERT INTO status (name) VALUES('ARRIVED')");
        template.update("INSERT INTO status (name) VALUES('FINISHED')");
        template.update("INSERT INTO status (name) VALUES('DOCUMENTED')");
        template.update("INSERT INTO status (name) VALUES('CANCELED')");
    }
}
