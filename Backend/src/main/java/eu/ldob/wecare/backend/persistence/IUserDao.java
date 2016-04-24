package eu.ldob.wecare.backend.persistence;

import eu.ldob.wecare.backend.exception.*;
import eu.ldob.wecare.entity.user.AUser;

import java.util.List;

public interface IUserDao {

    AUser insert(AUser user) throws InsertDatabaseException;

    AUser update(AUser user) throws UpdateDatabaseException;

    void delete(AUser user) throws DeleteDatabaseException;

    List<AUser> selectAll() throws BadRequestException;

    AUser selectById(long id) throws BadRequestException, ElementNotInDatabaseException;


}
