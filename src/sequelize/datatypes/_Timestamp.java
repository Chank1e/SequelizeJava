package sequelize.datatypes;

import java.util.Date;

class _Timestamp extends _IndexDatatype implements _IndexDatatypeInterface{
    private static _Timestamp ourInstance = new _Timestamp();

    public static _Timestamp getInstance() {
        return ourInstance;
    }

    private _Timestamp() {
    }

    @Override
    public String getKey(){
        return "timestamp";
    }

    @Override
    public boolean is(Object obj){
        return obj instanceof Date;
    }

    @Override
    public String create(Object obj){
        Date tmp = (Date) obj;
        return "to_timestamp(" + Long.toString(tmp.getTime()/1000) + ")";
    }
}
