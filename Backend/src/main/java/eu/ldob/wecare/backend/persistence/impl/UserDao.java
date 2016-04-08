package eu.ldob.wecare.backend.persistence.impl;

import eu.ldob.wecare.backend.persistence.IUserDao;
import eu.ldob.wecare.backend.persistence.mapper.UserRowMapper;
import eu.ldob.wecare.entity.user.AUser;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class UserDao implements IUserDao {

    private JdbcTemplate template;

    private static final String INSERT = "INSERT INTO user (first_name, last_name) VALUES (?, ?)";
    private static final String UPDATE = "UPDATE user SET first_name = ?, last_name = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM user WHERE id = ?";

    private static final String SELECT_ALL = "SELECT id, first_name, last_name FROM user";
    private static final String SELECT_ID = "SELECT id, first_name, last_name FROM user WHERE id = ?";

    public UserDao(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public AUser insert(AUser user) {
        KeyHolder holder = new GeneratedKeyHolder();
        template.update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(INSERT.toString(), Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, user.getFirstName());
                ps.setString(2, user.getLastName());
                return ps;
            }
        }, holder);

        return this.selectById(holder.getKey().longValue());
    }

    @Override
    public AUser update(AUser user) {
        PreparedStatementSetter pss = ps -> {
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setLong(3, user.getId());
        };
        template.update(UPDATE, pss);

        return null;
    }

    @Override
    public void delete(AUser user) {
        PreparedStatementSetter pss = ps -> {
            ps.setLong(1, user.getId());
        };
        template.update(DELETE, pss);
    }

    @Override
    public List<AUser> selectAll() {
        return template.query(SELECT_ALL, new UserRowMapper());
    }

    @Override
    public AUser selectById(long id) {
        PreparedStatementSetter pss = ps -> {
            ps.setLong(1, id);
        };
        return template.query(SELECT_ID, pss, new UserRowMapper()).get(0);
    }
}
