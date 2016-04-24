package eu.ldob.wecare.backend.exception;

public class DeleteDatabaseException extends APersistenceException {

    public DeleteDatabaseException() {
        super("Could not delete Element in Database!");
    }

    public DeleteDatabaseException(Object element) {
        super("Could not delete " + element.toString() + " in Database!");
    }
}
