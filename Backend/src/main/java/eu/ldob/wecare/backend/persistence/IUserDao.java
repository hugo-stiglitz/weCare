package eu.ldob.wecare.backend.persistence;

import eu.ldob.wecare.entity.user.AUser;

import javax.sql.DataSource;
import java.util.List;

public interface IUserDao {

    AUser insert(AUser user);

    AUser update(AUser user);

    void delete(AUser user);

    List<AUser> selectAll();

    AUser selectById(long id);


}
