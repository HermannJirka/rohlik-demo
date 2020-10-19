package cz.tut.rohlik.rohlikdemo.exceptions;

public class AbstractException extends RuntimeException {
    private String description;

    public AbstractException(String message){
        super(message);
    }

    public AbstractException(String message, String description) {
        super(message);
        this.description = description;
    }

    public AbstractException(Throwable cause, String message, String description) {
        super(message, cause);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
