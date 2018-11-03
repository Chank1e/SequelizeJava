package sequelize.model;

import sequelize.datatypes._Factory;
import sequelize.datatypes._IndexDatatypeInterface;
import sequelize.model.Exception.InvalidColumnNameException;
import sequelize.model.Exception.InvalidTypeException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SchemaValues {
    private Map<String, Object> values = new HashMap<>();
    private Schema schema;

    public SchemaValues from(Schema schema) {
        this.schema = schema;
        return this;
    }

    public SchemaValues withValue(String column, Object value) throws InvalidTypeException, InvalidColumnNameException {

        if(schema.getSchema().get(column) == null )
            throw new InvalidColumnNameException("Column " + column + "is not defined");

        _IndexDatatypeInterface tmpType = (_IndexDatatypeInterface) schema.getSchema().get(column);

        if(!tmpType.is(value))
            throw new InvalidTypeException("Column " + column + " expected to be of type " + tmpType.getKey());

        values.put(column, value);
        return this;
    }

    public List<String> getValuesOnly(){
        List<String> list = new ArrayList<String>();

        values.forEach((key,value)->{
            _IndexDatatypeInterface tmpType = (_IndexDatatypeInterface) schema.getSchema().get(key);
            list.add(_Factory.getSimpleType(tmpType.getKey()).create(value));
        });

        return list;
    }

    public Map<String, Object> getValues() {
        return values;
    }
}
