package sequelize.logger;

public enum AwesomeLoggerTypes {
    INFO, WARNING, ERROR;

    public String what(){
        switch(this){
            case INFO:
                return "Info";
            case ERROR:
                return "!!Error";
            case WARNING:
                return "!Warning";
            default:
                return "Invalid type";
        }
    }

}
