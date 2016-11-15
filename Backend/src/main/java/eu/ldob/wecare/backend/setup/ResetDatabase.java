package eu.ldob.wecare.backend.setup;

import eu.ldob.wecare.backend.exception.InsertDatabaseException;
import eu.ldob.wecare.backend.exception.UpdateDatabaseException;
import eu.ldob.wecare.backend.persistence.IOperationDao;
import eu.ldob.wecare.backend.persistence.IUserDao;
import eu.ldob.wecare.backend.persistence.impl.DatabaseConnection;
import eu.ldob.wecare.backend.persistence.impl.OperationDao;
import eu.ldob.wecare.backend.persistence.impl.UserDao;
import eu.ldob.wecare.entity.operation.*;
import eu.ldob.wecare.entity.user.AUser;
import eu.ldob.wecare.entity.user.Dispatcher;
import eu.ldob.wecare.entity.user.Doctor;
import eu.ldob.wecare.entity.user.Paramedic;
import org.springframework.jdbc.core.JdbcTemplate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ResetDatabase {

    private ResetDatabase() { }

    public static void execute() {
        resetDatabase();
        insertData();
    }

    private static void resetDatabase() {

        JdbcTemplate template = new JdbcTemplate(DatabaseConnection.getConnection());
        ISetupDao setupDao = new SetupDao(template);

        setupDao.createTableUser();
        setupDao.createTableOperation();
    }

    private static void insertData() {

        JdbcTemplate template = new JdbcTemplate(DatabaseConnection.getConnection());
        IUserDao userDao = new UserDao(template);
        IOperationDao operationDao = new OperationDao(template);

        Paramedic paramedic = new Paramedic(-1, "Lucas", "Dobler");
        Doctor doctor = new Doctor(-1, "Jacqueline", "Dr. Adlassnigg");
        Dispatcher dispatcher = new Dispatcher(-1, "Daniel", "Gehrer");

        List<AUser> users = new ArrayList<>();
        users.add(paramedic);
        users.add(doctor);
        users.add(dispatcher);

        try {
            paramedic = (Paramedic) userDao.insert(paramedic);
            doctor = (Doctor) userDao.insert(doctor);
            dispatcher = (Dispatcher) userDao.insert(dispatcher);
        } catch (InsertDatabaseException e) {
            //TODO error handling
            e.printStackTrace();
        }

        Operation op1 = new Operation(1, "R1623426", dispatcher, new Incident("Bewusstlose Frau auf Strasse, nichts näheres bekannt, vermutlich CPR", new Patient("Y", "X", "ca. 60"), new Location(new Address("Gaschurn", 6793, "Unteres Vand 134"), new GPSCoordinates(46.994855, 10.018765))));
        Operation op2 = new Operation(2, "R1623427", dispatcher, new Incident("[atemkreislaufstillstand] ersthelfer vor ort", new Patient("Kastner", "Kunigunde", "89j"), new Location(new Address("St. Gallenkirch", 6791, "Ziggamweg 16"), new GPSCoordinates(47.022247, 9.977781))));
        Operation op3 = new Operation(3, "R1623428", dispatcher, new Incident("ACS sympthomatik, Einweiser wartet an der strasse", new Patient("Düringer", "Hartmut", "jg 49"), new Location(new Address("Gargellen", 6791, "Gargellen 38"), new GPSCoordinates(46.971839, 9.917053))));
        Operation op4 = new Operation(4, "R1623429", dispatcher, new Incident("CPR, C8 im Anflug", new Patient("Edeltraud", "Frau", "71J"), new Location(new Address("Partenen", 6793, "Montafonerstrasse 36a"), new GPSCoordinates(46.963764, 10.074537))));

        List<Operation> operations = new ArrayList<>();
        operations.add(op1);
        operations.add(op2);
        operations.add(op3);
        operations.add(op4);

        try {
            for (Operation o : operations) {
                operationDao.insert(o);
            }
        } catch (InsertDatabaseException e) {
            //TODO error handling
            e.printStackTrace();
        } catch (UpdateDatabaseException e) {
            //TODO error handling
            e.printStackTrace();
        }
    }
}
