package eu.ldob.wecare.backend.exception;

public class InsertDatabaseException extends APersistenceException {

    public InsertDatabaseException() {
        super("Could not insert Element in Database!");
    }

    public InsertDatabaseException(Object element) {
        super("Could not insert " + element.toString() + " in Database!");
    }
}
