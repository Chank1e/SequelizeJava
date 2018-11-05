package sequelize.exception;

public class InvalidColumnNameException extends Exception {
    public InvalidColumnNameException() { super(); }
    public InvalidColumnNameException(String message) { super(message); }
    public InvalidColumnNameException(String message, Throwable cause) { super(message, cause); }
    public InvalidColumnNameException(Throwable cause) { super(cause); }
}
