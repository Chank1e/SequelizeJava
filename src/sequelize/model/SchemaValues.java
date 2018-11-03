package sequelize.model;

import sequelize.DataTypes;
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

    public SchemaValues from(Schema schema) {
        this.schema = schema;
        return this;
    }

    public SchemaValues withValue(String column, Object value) throws InvalidTypeException, InvalidColumnNameException, NoSchemaProvidedException {

        if(schema == null)
            throw new NoSchemaProvidedException();

        if(schema.getColumns().get(column) == null )
            throw new InvalidColumnNameException("Column " + column + "is not defined");

        DataTypes tmpType = (DataTypes) schema.getColumns().get(column);

        if(!tmpType.is(value))
            throw new InvalidTypeException("Column " + column + " expected to be of type " + tmpType.getAsSQL());

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
}
