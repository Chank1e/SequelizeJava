package sequelize;

import java.util.Date;

public enum DataTypes {
    INTEGER, TEXT, TIMESTAMP, PRIMARY_KEY;

    private String asSQL;

    static {
        INTEGER.asSQL = "int";
        TEXT.asSQL = "text";
        TIMESTAMP.asSQL = "timestamp";
        PRIMARY_KEY.asSQL = "SERIAL PRIMARY KEY";
    }


    public String getAsSQL(){
        return asSQL;
    }

    public Boolean is(Object obj) {
        switch(this){
            case TEXT:
                return obj instanceof String;
            case INTEGER:
                return obj instanceof Integer;
            case TIMESTAMP:
                return obj instanceof Date;
            default:
                return true;
        }
    }
    public String from(Object obj) {
        switch(this){
            case TEXT:
                return "'" + obj + "'";
            case INTEGER:
                Integer tmpInt = (Integer) obj;
                return tmpInt.toString();
            case TIMESTAMP:
                Date tmpDate = (Date) obj;
                return "to_timestamp(" + Long.toString(tmpDate.getTime()/1000) + ")";
            default:
                return "";
        }
    }
}
