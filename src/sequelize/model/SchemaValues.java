package sequelize.model;

import sequelize.DataTypes;
import sequelize.ErrorHandler;
import sequelize.exception.InvalidColumnNameException;
import sequelize.exception.InvalidTypeException;
import sequelize.exception.NoSchemaProvidedException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SchemaValues {

    private Map<String, Object> values = new HashMap<>();

    private Schema schema;

    public SchemaValues(){}

//    public SchemaValues(Schema schema){
//        this.schema = schema;
//    }
//
//    public SchemaValues from(Schema schema) {
//        this.schema = schema;
//        return this;
//    }

    public SchemaValues insert(String column, Object value) {
        values.put(column, value);
        return this;
    }

    public List<String> getValuesOnly(){
        List<String> list = new ArrayList<String>();

        values.forEach((key,value)->{
            DataTypes tmpType = (DataTypes) schema.getColumns().get(key);
            list.add(tmpType.from(value));
        });

        return list;
    }

    public Map<String, Object> getValues() {
        return values;
    }

    public Schema getSchema() {
        return schema;
    }

    public void setSchema(Schema schema) {
        this.schema = schema;
        this.checkValues();
    }

    private void checkValues(){
        values.forEach((String column, Object value) -> {
            if(schema == null)
                try {
                    throw new NoSchemaProvidedException();
                } catch (NoSchemaProvidedException e) {
                    ErrorHandler.handle(e);
                }

            if(schema.getColumns().get(column) == null )
                try {
                    throw new InvalidColumnNameException("Column " + column + " is not defined");
                } catch (InvalidColumnNameException e) {
                    ErrorHandler.handle(e);
                }

            DataTypes tmpType = (DataTypes) schema.getColumns().get(column);

            if(!tmpType.is(value))
                try {
                    throw new InvalidTypeException("Column " + column + " expected to be of type " + tmpType.getAsSQL());
                } catch (InvalidTypeException e) {
                    ErrorHandler.handle(e);
                }
        });
    }
}
