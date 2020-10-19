package cz.tut.rohlik.rohlikdemo.exceptions;

public class ItemCountException extends AbstractException{
    public ItemCountException(String message) {
        super(message);
    }

    public ItemCountException(String message, String description) {
        super(message, description);
    }

    public ItemCountException(String message, String description, Throwable cause) {
        super(cause, message, description);
    }
}
