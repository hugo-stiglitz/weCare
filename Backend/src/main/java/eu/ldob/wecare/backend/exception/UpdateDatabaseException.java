package eu.ldob.wecare.backend.exception;

public class UpdateDatabaseException extends APersistenceException {

    public UpdateDatabaseException() {
        super("Could not update Element in Database!");
    }

    public UpdateDatabaseException(Object element) {
        super("Could not update " + element.toString() + " in Database!");
    }
}
