package sequelize.exception;

public class NoSchemaProvidedException extends Exception {

    public NoSchemaProvidedException() { super(); }
    public NoSchemaProvidedException(String message) { super(message); }
    public NoSchemaProvidedException(String message, Throwable cause) { super(message, cause); }
    public NoSchemaProvidedException(Throwable cause) { super(cause); }
}
