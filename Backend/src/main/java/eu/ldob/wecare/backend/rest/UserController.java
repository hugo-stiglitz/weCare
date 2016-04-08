package eu.ldob.wecare.backend.rest;

import eu.ldob.wecare.backend.persistence.IUserDao;
import eu.ldob.wecare.backend.persistence.impl.DatabaseConnection;
import eu.ldob.wecare.backend.persistence.impl.UserDao;
import eu.ldob.wecare.entity.user.AUser;
import eu.ldob.wecare.entity.user.Doctor;
import eu.ldob.wecare.entity.user.Paramedic;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @RequestMapping("/user/create")
    public AUser create(
            @RequestParam(value = "type")
            String type,
            @RequestParam(value = "firstname")
            String firstName,
            @RequestParam(value = "lastname")
            String lastName
    ) {
        AUser user;
        if(type.equals("doctor")) {
            user = new Doctor(-1, lastName, firstName);
        }
        else {
            //TODO
            user = new Doctor(-1, lastName, firstName);
        }

        JdbcTemplate template = new JdbcTemplate(DatabaseConnection.getConnection());
        IUserDao userDao = new UserDao(template);

        return userDao.insert(user);
    }
}
