package eu.ldob.wecare.backend.rest;

import eu.ldob.wecare.backend.exception.BadRequestException;
import eu.ldob.wecare.backend.exception.ElementNotInDatabaseException;
import eu.ldob.wecare.backend.exception.InsertDatabaseException;
import eu.ldob.wecare.backend.exception.UpdateDatabaseException;
import eu.ldob.wecare.backend.persistence.IOperationDao;
import eu.ldob.wecare.backend.persistence.impl.DatabaseConnection;
import eu.ldob.wecare.backend.persistence.impl.OperationDao;
import eu.ldob.wecare.entity.operation.Incident;
import eu.ldob.wecare.entity.operation.Operation;
import eu.ldob.wecare.entity.user.Dispatcher;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost")
@RequestMapping("/operations")
public class OperationController {

    IOperationDao operationDao = new OperationDao(new JdbcTemplate(DatabaseConnection.getConnection()));

    @RequestMapping(method = RequestMethod.POST)
    public Operation postOperation(@RequestBody Operation operation) {

        try {
            //TODO set dispatcher via auth token
            operation.setDispatcher(new Dispatcher(1, "Stefan", "Burtscher"));

            return operationDao.insert(operation);

        } catch (InsertDatabaseException e) {
            //TODO error handling
            e.printStackTrace();
            return null;
        } catch (UpdateDatabaseException e) {
            //TODO error handling
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Operation> getOperations() {

        try {
            return operationDao.selectAll();

        } catch (BadRequestException e) {
            //TODO error handling
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Operation getOperation(@PathVariable Integer id) {

        try {
            return operationDao.selectById(id);

        } catch (BadRequestException e) {
            //TODO error handling
            e.printStackTrace();
            return null;
        } catch (ElementNotInDatabaseException e) {
            //TODO error handling
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void updateItem(@RequestBody Operation operation, @PathVariable Integer id) {

        try {
            operationDao.update(operation);
        } catch (UpdateDatabaseException e) {
            //TODO error handling
            e.printStackTrace();
        }
    }
}