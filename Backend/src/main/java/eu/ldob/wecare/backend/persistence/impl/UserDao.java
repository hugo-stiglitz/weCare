package eu.ldob.wecare.backend.persistence.impl;

import eu.ldob.wecare.backend.exception.*;
import eu.ldob.wecare.backend.persistence.IUserDao;
import eu.ldob.wecare.backend.persistence.mapper.UserRowMapper;
import eu.ldob.wecare.entity.user.AUser;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

public class UserDao implements IUserDao {

    private JdbcTemplate template;

    private static final String INSERT = "INSERT INTO user (user_type, first_name, last_name) VALUES (?, ?, ?)";
    private static final String UPDATE = "UPDATE user SET user_type = ?, first_name = ?, last_name = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM user WHERE id = ?";

    private static final String SELECT_ALL = "SELECT id, user_type, first_name, last_name FROM user";
    private static final String SELECT_ID = "SELECT id, user_type, first_name, last_name FROM user WHERE id = ?";

    public UserDao(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public AUser insert(AUser user) throws InsertDatabaseException {

        KeyHolder holder = new GeneratedKeyHolder();
        PreparedStatementCreator psc = connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getType());
            ps.setString(2, user.getFirstName());
            ps.setString(3, user.getLastName());
            return ps;
        };

        try {
            template.update(psc, holder);

        } catch (DataAccessException e) {
            throw new InsertDatabaseException(user);
        }

        try {
            return selectById(holder.getKey().longValue());
        } catch (APersistenceException e) {
            throw new InsertDatabaseException(user);
        }
    }

    @Override
    public AUser update(AUser user) throws UpdateDatabaseException {

        KeyHolder holder = new GeneratedKeyHolder();
        PreparedStatementCreator psc = connection -> {
            PreparedStatement ps = connection.prepareStatement(UPDATE, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getType());
            ps.setString(2, user.getFirstName());
            ps.setString(3, user.getLastName());
            ps.setLong(4, user.getId());
            return ps;
        };

        try {
            template.update(psc, holder);

        } catch (DataAccessException e) {
            throw new UpdateDatabaseException(user);
        }

        try {
            return selectById(holder.getKey().longValue());
        } catch (APersistenceException e) {
            throw new UpdateDatabaseException(user);
        }
    }

    @Override
    public void delete(AUser user) throws DeleteDatabaseException {

        PreparedStatementCreator psc = connection -> {
            PreparedStatement ps = connection.prepareStatement(DELETE);
            ps.setLong(1, user.getId());
            return ps;
        };

        try {
            template.update(psc);

        } catch (DataAccessException e) {
            throw new DeleteDatabaseException(user);
        }
    }

    @Override
    public List<AUser> selectAll() throws BadRequestException {

        PreparedStatementCreator psc = connection -> connection.prepareStatement(SELECT_ALL);

        try {
            return template.query(psc, new UserRowMapper());

        } catch (DataAccessException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public AUser selectById(long id) throws BadRequestException, ElementNotInDatabaseException {

        PreparedStatementCreator psc = connection -> {
            PreparedStatement ps = connection.prepareStatement(SELECT_ID);
            ps.setLong(1, id);
            return ps;
        };

        List<AUser> results;
        try {
            results = template.query(psc, new UserRowMapper());

        } catch (DataAccessException e) {
            throw new BadRequestException(e.getMessage());
        }

        if(results.size() == 1) {
            return results.get(0);
        }

        throw new ElementNotInDatabaseException("User with id: " + id);
    }
}
