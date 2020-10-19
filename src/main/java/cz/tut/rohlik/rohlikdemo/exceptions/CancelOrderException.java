package cz.tut.rohlik.rohlikdemo.exceptions;

public class CancelOrderException extends AbstractException{
    public CancelOrderException(String message) {
        super(message);
    }

    public CancelOrderException(String message, String description) {
        super(message, description);
    }

    public CancelOrderException(String message, String description, Throwable cause) {
        super(cause, message, description);
    }
}
