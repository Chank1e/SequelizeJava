package sequelize.datatypes;

public interface  _IndexDatatypeInterface {
    boolean is(Object obj);
    String getKey();

    String create(Object obj);
}
