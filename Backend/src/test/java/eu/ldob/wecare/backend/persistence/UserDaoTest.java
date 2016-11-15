package eu.ldob.wecare.backend.persistence;

import eu.ldob.wecare.backend.exception.BadRequestException;
import eu.ldob.wecare.backend.exception.InsertDatabaseException;
import eu.ldob.wecare.backend.persistence.impl.UserDao;
import eu.ldob.wecare.backend.setup.ISetupDao;
import eu.ldob.wecare.backend.setup.SetupDao;
import eu.ldob.wecare.entity.user.AUser;
import eu.ldob.wecare.entity.user.Doctor;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

public class UserDaoTest {

    private EmbeddedDatabase database;

    @Before
    public void setUp() {

        database = new EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.HSQL)
            .build();
    }

    @Test
    public void testInsertAndSelectUser() {

        JdbcTemplate template = new JdbcTemplate(database);

        ISetupDao setupDao = new SetupDao(template);
        IUserDao userDao = new UserDao(template);

        setupDao.createTableUser();

        AUser user = new Doctor(-1, "Lucas", "Dobler");
        try {
            userDao.insert(user);
        } catch (InsertDatabaseException e) {
            e.printStackTrace();
        }

        AUser resultUser = null;
        try {
            resultUser = userDao.selectAll().get(0);
        } catch (BadRequestException e) {
            e.printStackTrace();
        }

        Assert.assertEquals(user.getFirstName(), resultUser.getFirstName());
        Assert.assertEquals(user.getLastName(), resultUser.getLastName());
    }

    @After
    public void tearDown() {
        database.shutdown();
    }
}
