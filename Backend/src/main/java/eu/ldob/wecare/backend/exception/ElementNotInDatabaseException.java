package eu.ldob.wecare.backend.exception;

public class ElementNotInDatabaseException extends APersistenceException {

    public ElementNotInDatabaseException() {
        super("Element was not found in Database!");
    }

    public ElementNotInDatabaseException(Object element) {
        super(element.toString() + " was not found in Database!");
    }
}
