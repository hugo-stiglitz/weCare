package eu.ldob.wecare.backend.exception;

public abstract class APersistenceException extends Exception {

    public APersistenceException() {
        super("Unknown Persistence Exception!");
    }

    public APersistenceException(String message) {
        super(message);
    }
}
