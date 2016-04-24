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
}
