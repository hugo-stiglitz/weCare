package eu.ldob.wecare.backend.rest;

import eu.ldob.wecare.backend.exception.BadRequestException;
import eu.ldob.wecare.backend.exception.ElementNotInDatabaseException;
import eu.ldob.wecare.backend.exception.InsertDatabaseException;
import eu.ldob.wecare.backend.persistence.IUserDao;
import eu.ldob.wecare.backend.persistence.impl.DatabaseConnection;
import eu.ldob.wecare.backend.persistence.impl.UserDao;
import eu.ldob.wecare.entity.user.AUser;
import eu.ldob.wecare.entity.user.Dispatcher;
import eu.ldob.wecare.entity.user.Doctor;
import eu.ldob.wecare.entity.user.Paramedic;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    IUserDao userDao = new UserDao(new JdbcTemplate(DatabaseConnection.getConnection()));

    @RequestMapping("/user/create")
    public AUser create(
            @RequestParam(value = "type")
                    String type,
            @RequestParam(value = "firstName")
                    String firstName,
            @RequestParam(value = "lastName")
                    String lastName
    ) {
        AUser user;

        switch (type) {
            case "dispatcher": user = new Dispatcher(-1, lastName, firstName); break;
            case "doctor": user = new Doctor(-1, lastName, firstName); break;
            case "paramedic": user = new Paramedic(-1, lastName, firstName); break;
            default: user = null; //TODO error handling
        }

        try {
            return userDao.insert(user);

        } catch(InsertDatabaseException e) {
            e.printStackTrace();
            //TODO error handling
        }

        throw new RuntimeException("Unknown Error");
    }

    @RequestMapping("user/all")
    public List<AUser> getAll() {

        try {
            return userDao.selectAll();

        } catch (BadRequestException e) {
            //TODO error handling
            e.printStackTrace();
        }

        throw new RuntimeException("Unknown Error");
    }

    @RequestMapping("user")
    public AUser get(
            @RequestParam(value = "id")
                    Long id
    ) {
        try {
            return userDao.selectById(id);

        } catch (BadRequestException e1) {
            //TODO error handling
            e1.printStackTrace();
        } catch (ElementNotInDatabaseException e2) {
            //TODO error handling
            e2.printStackTrace();
        }

        throw new RuntimeException("Unknown Error");
    }
}
