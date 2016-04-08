package eu.ldob.wecare.backend.persistence.mapper;

import eu.ldob.wecare.entity.user.AUser;
import eu.ldob.wecare.entity.user.Doctor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<AUser> {

    private UserResultSetExtractor extractor = new UserResultSetExtractor();

    @Override
    public AUser mapRow(ResultSet rs, int rowNum) throws SQLException {
        return extractor.extractData(rs);
    }

    private static final class UserResultSetExtractor implements ResultSetExtractor<AUser> {
        @Override
        public AUser extractData(ResultSet rs) throws SQLException, DataAccessException {
            AUser user = new Doctor(rs.getLong(1), rs.getString(2), rs.getString(3));

            return user;
        }
    }
}
