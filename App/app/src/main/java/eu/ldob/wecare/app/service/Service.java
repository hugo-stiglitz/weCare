package eu.ldob.wecare.app.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

public class Service {

    private List<Operation> operations;
    private List<Documentation> documentations = new ArrayList<>();

    private Dispatcher dispatcher;
    private FirstResponder user;

    public Service() {

        dispatcher = new Dispatcher(-1l, "Stefan", "Burtscher");
        user = new FirstResponder();

        Calendar cal = new GregorianCalendar();
        cal.set(1951, 2, 15);

        operations = new ArrayList<>();
        operations.add(new Operation(1l, "R1603476", dispatcher, new Incident("Frau ohne Bewusstsein auf Strasse, nichts näheres bekannt, vermutlich CPR", new Patient("X", "Y", cal.getTime()), new Location(new Address("Gaschurn", 6793, "Silvrettastraße 196"), new GPSCoordinates(10, 42)))));
        operations.add(new Operation(1l, "R1603477", dispatcher, new Incident("[atemkreislaufstillstand] ersthelfer vor ort", new Patient("Kastner", "Kunigunde", cal.getTime()), new Location(new Address("Gaschurn", 6793, "Silvrettastraße 196"), new GPSCoordinates(10, 42)))));
        operations.add(new Operation(1l, "R1603478", dispatcher, new Incident("ACS sympthomatik, Einweiser wartet an der strasse", new Patient("Dünser", "Hartmut", cal.getTime()), new Location(new Address("Gaschurn", 6793, "Silvrettastraße 196"), new GPSCoordinates(10, 42)))));
        operations.add(new Operation(1l, "R1603479", dispatcher, new Incident("CPR, C8 im Anflug", new Patient("Edeltraud", "Frau", cal.getTime()), new Location(new Address("Gaschurn", 6793, "Silvrettastraße 196"), new GPSCoordinates(10, 42)))));

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


    public void cancel(String id) {
        for(Operation op : operations) {
            if(op.getId().equals(id)) {
                op.setStatus(Operation.EStatus.CANCELED);
                break;
            }
        }
    }

    public void accept(String id) {
        for(Operation op : operations) {
            if(op.getId().equals(id)) {
                op.setStatus(Operation.EStatus.ACCEPTED);
                break;
            }
        }
    }

    public void arrive(String id) {
        for(Operation op : operations) {
            if(op.getId().equals(id)) {
                op.setStatus(Operation.EStatus.ARRIVED);
                break;
            }
        }
    }

    public void finish(String id) {
        for(Operation op : operations) {
            if(op.getId().equals(id)) {
                op.setStatus(Operation.EStatus.FINISHED);
                break;
            }
        }
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
    }

    public Documentation getDocumentation(String operationId, FirstResponder user) {
        for(Documentation d : documentations) {
            if(d.getOperation().getId().equals(operationId) && d.getFirstResponder().equals(user)) {
                return d;
            }
        }

        return null;
    }
}
