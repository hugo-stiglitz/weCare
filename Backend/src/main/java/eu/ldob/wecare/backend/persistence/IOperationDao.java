package eu.ldob.wecare.backend.persistence;

import eu.ldob.wecare.backend.exception.*;
import eu.ldob.wecare.entity.operation.Operation;
import eu.ldob.wecare.entity.user.AUser;

import java.util.List;

public interface IOperationDao {

    Operation insert(Operation operation) throws InsertDatabaseException, UpdateDatabaseException;

    Operation update(Operation operation) throws UpdateDatabaseException;

    List<Operation> selectAll() throws BadRequestException;

    Operation selectById(int id) throws BadRequestException, ElementNotInDatabaseException;


}
