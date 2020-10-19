package cz.tut.rohlik.rohlikdemo.exceptions;

public class NotFoundException extends AbstractException {
    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, String description) {
        super(message, description);
    }

    public NotFoundException(String message, String description, Throwable cause) {
        super(cause, message, description);
    }
}
