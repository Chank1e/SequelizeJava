package sequelize.datatypes;

public class _Factory {
    public static _IndexDatatype getSimpleType(String dataType) {
        switch(dataType){
            case "text":
                return _Text.getInstance();

            case "int":
                return _Int.getInstance();

            case "timestamp":
                return _Timestamp.getInstance();

            default:
                return null;

        }

    }
}
