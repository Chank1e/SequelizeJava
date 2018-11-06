package sequelize.exception;

public class NoRelationFoundException extends Exception  {
    public NoRelationFoundException() { super(); }
    public NoRelationFoundException(String message) { super(message); }
    public NoRelationFoundException(String message, Throwable cause) { super(message, cause); }
    public NoRelationFoundException(Throwable cause) { super(cause); }
}
