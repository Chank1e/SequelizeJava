package sequelize.datatypes;

public class _IndexDatatype implements _IndexDatatypeInterface{

    public String getKey(){
        return null;
    }

    public boolean is(Object obj) { return false; }

    public String create(Object obj){ return ""; }

}
