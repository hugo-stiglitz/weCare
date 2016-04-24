package eu.ldob.wecare.backend.exception;

public class BadRequestException extends APersistenceException {

    public BadRequestException(String request) {
        super("Bad DB Request: " + request);
    }
}
