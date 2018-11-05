package sequelize.exception;

public class InvalidTypeException extends Exception {
    public InvalidTypeException() { super(); }
    public InvalidTypeException(String message) { super(message); }
    public InvalidTypeException(String message, Throwable cause) { super(message, cause); }
    public InvalidTypeException(Throwable cause) { super(cause); }
}
