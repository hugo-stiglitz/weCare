package eu.ldob.wecare.backend.persistence.mapper;

import eu.ldob.wecare.entity.operation.*;
import eu.ldob.wecare.entity.user.AUser;
import eu.ldob.wecare.entity.user.Dispatcher;
import eu.ldob.wecare.entity.user.Doctor;
import eu.ldob.wecare.entity.user.Paramedic;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class OperationRowMapper implements RowMapper<Operation> {

    private UserResultSetExtractor extractor = new UserResultSetExtractor();

    @Override
    public Operation mapRow(ResultSet rs, int rowNum) throws SQLException {
        return extractor.extractData(rs);
    }

    private static final class UserResultSetExtractor implements ResultSetExtractor<Operation> {
        @Override
        public Operation extractData(ResultSet rs) throws SQLException, DataAccessException {

            Dispatcher dispatcher = new Dispatcher(rs.getLong("dispatcher_id"), rs.getString("dispatcher_firstname"), rs.getString("dispatcher_lastname"));
            Patient patient = new Patient(rs.getString("incident_patient_lastname"), rs.getString("incident_patient_firstname"), rs.getString("incident_patient_age"));
            Location location = new Location(new Address(rs.getString("incident_location_address_city"), rs.getInt("incident_location_address_zip"), rs.getString("incident_location_address_street")), new GPSCoordinates(rs.getDouble("incident_location_coordinates_lat"), rs.getDouble("incident_location_coordinates_lng")));
            Incident incident = new Incident(rs.getString("incident_message"), patient, location);

            Operation operation = new Operation(rs.getInt("id"), rs.getString("external_id"), dispatcher, incident);
            operation.setStatus(Operation.EStatus.valueOf(rs.getString("latest_status")), rs.getTime("latest_status_timestamp"));

            return operation;
        }
    }
}
