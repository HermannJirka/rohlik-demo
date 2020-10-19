package cz.tut.rohlik.rohlikdemo.exceptions;

public class BadRequestException extends AbstractException {
    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, String description) {
        super(message, description);
    }

    public BadRequestException(String message, String description, Throwable cause) {
        super(cause, message, description);
    }
}
