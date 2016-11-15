package eu.ldob.wecare.service.logic;

import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import eu.ldob.wecare.entity.operation.Address;
import eu.ldob.wecare.entity.operation.Documentation;
import eu.ldob.wecare.entity.operation.FirstResponder;
import eu.ldob.wecare.entity.operation.GPSCoordinates;
import eu.ldob.wecare.entity.operation.Incident;
import eu.ldob.wecare.entity.operation.Location;
import eu.ldob.wecare.entity.operation.Operation;
import eu.ldob.wecare.entity.operation.Patient;
import eu.ldob.wecare.entity.user.Dispatcher;
import eu.ldob.wecare.service.rest.ILoginService;
import eu.ldob.wecare.service.rest.LoginService;

public class Service {

    private ILoginService loginService = new LoginService();

    private String firebaseToken;

    private List<Operation> operations;
    private List<Operation> previousOperations;

    private List<Documentation> documentations = new ArrayList<>();

    private Dispatcher dispatcher;
    private FirstResponder user;

    private GPSCoordinates coordinates;

    private List<IDataChangeListener> dataChangeListeners = new ArrayList<>();

    public Service() {

        dispatcher = new Dispatcher(-1l, "Stefan", "Burtscher");
        user = new FirstResponder();

        Calendar cal = new GregorianCalendar();
        cal.set(1951, 2, 15);

        operations = new ArrayList<>();
        operations.add(new Operation(11l, "R1604481", dispatcher, new Incident("Frau ohne Bewusstsein auf Strasse, nichts näheres bekannt, vermutlich CPR", new Patient("Kastner", "Kunigunde", cal.getTime()), new Location(new Address("Innsbruck", 6020, "Dreiheiligenstrasse 33"), new GPSCoordinates(11.406338, 47.269219)))));


        Operation op1 = new Operation(12l, "R1603467", dispatcher, new Incident("[atemkreislaufstillstand] bekannte Herzinsuffizienz, keine CPR durch Ersthelfer möglich", new Patient("X", "Y", cal.getTime()), new Location(new Address("Innsbruck", 6020, "Amraser Strasse 22"), new GPSCoordinates(11.408404, 47.263500))));
        op1.setStatus(Operation.EStatus.FINISHED);

        Operation op2 = new Operation(13l, "R1603391", dispatcher, new Incident("ACS sympthomatik, Einweiser wartet an der strasse", new Patient("Dünser", "Hartmut", cal.getTime()), new Location(new Address("Wien", 1140, "Maroltingerstrasse 3"), new GPSCoordinates(16.303030, 48.203096))));
        op2.setStatus(Operation.EStatus.DOCUMENTED);

        Operation op3 = new Operation(14l, "R1603185", dispatcher, new Incident("CPR, C8 im Anflug", new Patient("Edeltraud", "Frau", cal.getTime()), new Location(new Address("Wien", 1050, "Ziegelofengasse 41"), new GPSCoordinates(16.360091, 48.191961))));
        op3.setStatus(Operation.EStatus.CANCELED);

        Operation op4 = new Operation(15l, "R1602876", dispatcher, new Incident("unklare sache, 3. hand anruf, möglicherweise kein bewusstsein", new Patient("X", "Y", cal.getTime()), new Location(new Address("Gaschurn", 6793, "Silvrettastraße 196"), new GPSCoordinates(10, 42))));
        op4.setStatus(Operation.EStatus.FINISHED);

        previousOperations = new ArrayList<>();
        previousOperations.add(op1);
        previousOperations.add(op2);
        previousOperations.add(op3);
        previousOperations.add(op4);

        for(Operation op : operations) {
            op.setStatus(Operation.EStatus.ALARMED);
        }
    }

    public FirstResponder getUser() {
        return user;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public List<Operation> getPreviousOperations() {
        return previousOperations;
    }

    public void setFirebaseToken(String token) {
        this.firebaseToken = token;
        //loginService.postFirebaseToken(null, this.firebaseToken, ServiceHandler.getToken());
    }

    public String getFirebaseToken() {
        if(firebaseToken == null) {
            this.setFirebaseToken(FirebaseInstanceId.getInstance().getToken());
        }

        return firebaseToken;
    }

    public Operation getOperation(String id) {
        for(Operation op : operations) {
            if(op.getId().equals(id)) {
                return op;
            }
        }

        return null;
        // TODO implement exceptions
        //throw new OperationNotFoundException();
    }


    public void setCurrentLocation(GPSCoordinates coordinates, float accuracy) {
        if(accuracy < 500) {
            this.coordinates = coordinates;
        }

        notifyDataChangeListeners();
    }


    public void cancel(String id) {
        for(Operation op : operations) {
            if(op.getId().equals(id)) {
                op.setStatus(Operation.EStatus.CANCELED);
                break;
            }
        }

        notifyDataChangeListeners();
    }

    public void accept(String id) {
        for(Operation op : operations) {
            if(op.getId().equals(id)) {
                op.setStatus(Operation.EStatus.ACCEPTED);
                break;
            }
        }

        notifyDataChangeListeners();
    }

    public void arrive(String id) {
        for(Operation op : operations) {
            if(op.getId().equals(id)) {
                op.setStatus(Operation.EStatus.ARRIVED);
                break;
            }
        }

        notifyDataChangeListeners();
    }

    public void finish(String id) {
        for(Operation op : operations) {
            if(op.getId().equals(id)) {
                op.setStatus(Operation.EStatus.FINISHED);
                break;
            }
        }

        notifyDataChangeListeners();
    }

    public void saveDocumentation(Documentation documentation) {
        documentations.add(documentation);
        String id = documentation.getOperation().getId();
        for(Operation op : operations) {
            if(op.getId().equals(id)) {
                op.setStatus(Operation.EStatus.DOCUMENTED);
                break;
            }
        }

        notifyDataChangeListeners();
    }

    public Documentation getDocumentation(String operationId, FirstResponder user) {
        for(Documentation d : documentations) {
            if(d.getOperation().getId().equals(operationId) && d.getFirstResponder().equals(user)) {
                return d;
            }
        }

        return null;
    }

    public void addDataChangeListener(IDataChangeListener listener) {
        dataChangeListeners.add(listener);
    }

    public void notifyDataChangeListeners() {
        for(IDataChangeListener l : dataChangeListeners) {
            l.dataChanged();
        }
    }
}
