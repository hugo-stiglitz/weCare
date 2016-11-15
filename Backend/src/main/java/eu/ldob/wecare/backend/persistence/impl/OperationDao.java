package eu.ldob.wecare.backend.persistence.impl;

import eu.ldob.wecare.backend.exception.*;
import eu.ldob.wecare.backend.persistence.IOperationDao;
import eu.ldob.wecare.backend.persistence.IUserDao;
import eu.ldob.wecare.backend.persistence.mapper.OperationRowMapper;
import eu.ldob.wecare.backend.persistence.mapper.UserRowMapper;
import eu.ldob.wecare.entity.operation.Operation;
import eu.ldob.wecare.entity.user.AUser;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.*;
import java.util.List;

public class OperationDao implements IOperationDao {

    private JdbcTemplate template;

    private static final String INSERT = "INSERT INTO operation (external_id, dispatcher_id, incident_message, incident_patient_lastname, incident_patient_firstname, incident_patient_age, incident_location_address_city, incident_location_address_zip, incident_location_address_street, incident_location_coordinates_lat, incident_location_coordinates_lng) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE user SET external_id = ?, dispatcher_id = ?, incident_message = ?, incident_patient_lastname = ?, incident_patient_firstname = ?, incident_patient_age = ?, incident_location_address_city = ?, incident_location_address_zip = ?, incident_location_address_street = ?, incident_location_coordinates_lat = ?, incident_location_coordinates_lng = ? WHERE id = ?";

    private static final String INSERT_OPERATION_STATUS = "INSERT INTO operation_status_timestamp (operation_id, status_id, timestamp) VALUES ((SELECT id FROM operation WHERE external_id = ?), (SELECT id FROM status WHERE name = ?), ?)";

    private static final String SELECT_ALL = "SELECT o.id AS operation_id, o.external_id AS external_id, o.incident_message AS incident_message, o.incident_patient_lastname AS incident_patient_lastname, o.incident_patient_firstname AS incident_patient_firstname, o.incident_patient_age AS incident_patient_age, o.incident_location_address_city AS incident_location_address_city, o.incident_location_address_zip AS incident_location_address_zip, o.incident_location_address_street AS incident_location_address_street, o.incident_location_coordinates_lat AS incident_location_coordinates_lat, o.incident_location_coordinates_lng AS incident_location_coordinates_lng, u.id AS dispatcher_id, u.first_name AS dispatcher_firstname, u.last_name AS dispatcher_lastname, s.name AS latest_status, ost.timestamp AS latest_status_timestamp FROM operation o JOIN user u ON (o.dispatcher_id = u.id) JOIN operation_status_timestamp ost ON (o.id = ost.operation_id) JOIN status s ON (ost.status_id = s.id)";
    private static final String SELECT_ID = "SELECT o.id AS operation_id, o.external_id AS external_id, o.incident_message AS incident_message, o.incident_patient_lastname AS incident_patient_lastname, o.incident_patient_firstname AS incident_patient_firstname, o.incident_patient_age AS incident_patient_age, o.incident_location_address_city AS incident_location_address_city, o.incident_location_address_zip AS incident_location_address_zip, o.incident_location_address_street AS incident_location_address_street, o.incident_location_coordinates_lat AS incident_location_coordinates_lat, o.incident_location_coordinates_lng AS incident_location_coordinates_lng, u.id AS dispatcher_id, u.first_name AS dispatcher_firstname, u.last_name AS dispatcher_lastname, s.name AS latest_status, ost.timestamp AS latest_status_timestamp FROM operation o JOIN user u ON (o.dispatcher_id = u.id) JOIN operation_status_timestamp ost ON (o.id = ost.operation_id) JOIN status s ON (ost.status_id = s.id) WHERE o.id = ?";

    public OperationDao(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public Operation insert(Operation operation) throws InsertDatabaseException, UpdateDatabaseException {

        KeyHolder holder = new GeneratedKeyHolder();
        PreparedStatementCreator psc = connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, operation.getId());
            ps.setLong(2, operation.getDispatcher().getId());
            ps.setString(3, operation.getIncident().getMessage());
            ps.setString(4, operation.getIncident().getPatient().getLastname());
            ps.setString(5, operation.getIncident().getPatient().getFirstname());
            ps.setString(6, operation.getIncident().getPatient().getAge());
            ps.setString(7, operation.getIncident().getLocation().getAddress().getCity());
            ps.setInt(8, operation.getIncident().getLocation().getAddress().getZipCode());
            ps.setString(9, operation.getIncident().getLocation().getAddress().getStreet());
            ps.setDouble(10, operation.getIncident().getLocation().getCoordinates().getLatitude());
            ps.setDouble(11, operation.getIncident().getLocation().getCoordinates().getLongitude());
            return ps;
        };

        try {
            template.update(psc, holder);
        } catch (DataAccessException e) {
            throw new InsertDatabaseException(operation);
        }

        insertOperationStatus(operation);

        try {
            return selectById(holder.getKey().intValue());
        } catch (APersistenceException e) {
            throw new InsertDatabaseException(operation);
        }
    }

    @Override
    public Operation update(Operation operation) throws UpdateDatabaseException {

        KeyHolder holder = new GeneratedKeyHolder();
        PreparedStatementCreator psc = connection -> {
            PreparedStatement ps = connection.prepareStatement(UPDATE, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, operation.getId());
            ps.setLong(2, operation.getDispatcher().getId());
            ps.setString(3, operation.getIncident().getMessage());
            ps.setString(4, operation.getIncident().getPatient().getLastname());
            ps.setString(5, operation.getIncident().getPatient().getFirstname());
            ps.setString(6, operation.getIncident().getPatient().getAge());
            ps.setString(7, operation.getIncident().getLocation().getAddress().getCity());
            ps.setInt(8, operation.getIncident().getLocation().getAddress().getZipCode());
            ps.setString(9, operation.getIncident().getLocation().getAddress().getStreet());
            ps.setDouble(10, operation.getIncident().getLocation().getCoordinates().getLatitude());
            ps.setDouble(11, operation.getIncident().getLocation().getCoordinates().getLongitude());
            ps.setLong(12, operation.getInternalId());
            return ps;
        };

        try {
            template.update(psc, holder);
        } catch (DataAccessException e) {
            throw new UpdateDatabaseException(operation);
        }

        insertOperationStatus(operation);

        try {
            return selectById(holder.getKey().intValue());
        } catch (APersistenceException e) {
            throw new UpdateDatabaseException(operation);
        }
    }

    @Override
    public List<Operation> selectAll() throws BadRequestException {

        PreparedStatementCreator psc = connection -> connection.prepareStatement(SELECT_ALL);

        try {
            return template.query(psc, new OperationRowMapper());
        } catch (DataAccessException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public Operation selectById(int id) throws BadRequestException, ElementNotInDatabaseException {

        PreparedStatementCreator psc = connection -> {
            PreparedStatement ps = connection.prepareStatement(SELECT_ID);
            ps.setInt(1, id);
            return ps;
        };

        List<Operation> results;
        try {
            results = template.query(psc, new OperationRowMapper());
        } catch (DataAccessException e) {
            throw new BadRequestException(e.getMessage());
        }

        if(results.size() == 1) {
            return results.get(0);
        }

        throw new ElementNotInDatabaseException("operation with id " + id);
    }

    public void insertOperationStatus(Operation operation) throws UpdateDatabaseException {
        PreparedStatementCreator psc = connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_OPERATION_STATUS);
            ps.setString(1, operation.getId());
            ps.setString(2, operation.getStatus().toString());
            ps.setTime(3, new Time(new java.util.Date().getTime()));
            return ps;
        };

        try {
            template.update(psc);
        } catch (DataAccessException e) {
            throw new UpdateDatabaseException("status of " + operation);
        }
    }
}
