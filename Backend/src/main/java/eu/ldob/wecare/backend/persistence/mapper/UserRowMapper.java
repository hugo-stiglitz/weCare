package eu.ldob.wecare.backend.persistence.mapper;

import eu.ldob.wecare.entity.user.AUser;
import eu.ldob.wecare.entity.user.Dispatcher;
import eu.ldob.wecare.entity.user.Doctor;
import eu.ldob.wecare.entity.user.Paramedic;
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

            AUser user;
            switch (rs.getString(2)) {
                case "dispatcher": user = new Dispatcher(rs.getLong(1), rs.getString(3), rs.getString(4)); break;
                case "doctor": user = new Doctor(rs.getLong(1), rs.getString(3), rs.getString(4)); break;
                case "paramedic": user = new Paramedic(rs.getLong(1), rs.getString(3), rs.getString(4)); break;
                default: user = null; //TODO error handling
            }

            return user;
        }
    }
}
