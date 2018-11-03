package sequelize.model;

import sequelize.datatypes._IndexDatatype;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Schema {
    private Map<String, _IndexDatatype> columns = new HashMap<>();

    public Schema ModelSchema(){
        return this;
    }

    public Schema insert(String name, _IndexDatatype type){
        columns.put(name, type);
        return this;
    }

    public Map getSchema(){
        return this.columns;
    }

    public List<String> getNamesOnly(){
        List<String> list = new ArrayList<String>();

        columns.forEach((key,value)->list.add(key));

        return list;
    }


}
