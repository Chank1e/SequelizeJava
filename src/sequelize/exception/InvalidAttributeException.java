package sequelize.exception;

public class InvalidAttributeException extends Exception{
    public InvalidAttributeException() { super(); }
    public InvalidAttributeException(String message) { super(message); }
    public InvalidAttributeException(String message, Throwable cause) { super(message, cause); }
    public InvalidAttributeException(Throwable cause) { super(cause); }
}
