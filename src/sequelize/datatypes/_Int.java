package sequelize.datatypes;

class _Int extends _IndexDatatype implements _IndexDatatypeInterface {
    private static _Int ourInstance = new _Int();

    public static _Int getInstance() {
        return ourInstance;
    }

    private _Int() {
    }

    @Override
    public String getKey(){
        return "int";
    }

    @Override
    public boolean is(Object obj){
        return obj instanceof Integer;
    }

    @Override
    public String create(Object obj){
        Integer tmp = (Integer) obj;
        return tmp.toString();
    }
}
