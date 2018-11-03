package sequelize.datatypes;

class _Text extends _IndexDatatype implements _IndexDatatypeInterface {
    private static _Text ourInstance = new _Text();

    public static _Text getInstance() {
        return ourInstance;
    }

    private _Text() {
    }

    @Override
    public String getKey(){
        return "text";
    }

    @Override
    public boolean is(Object obj){
        return obj instanceof String;
    }

    @Override
    public String create(Object obj){
        return "\'" + obj + "\'";
    }
}
