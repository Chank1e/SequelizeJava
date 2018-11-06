package sequelize.model;

import sequelize.DataTypes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class Schema {
    private Map<String, DataTypes> columns = new HashMap<>();
    private Boolean setupCreatedAt = false;
    private Boolean setupUpdatedAt = false;

    public Boolean hasCreatedAt() {
        return setupCreatedAt;
    }

    public Boolean hasUpdatedAt() {
        return setupUpdatedAt;
    }

    public Schema insert(String name, DataTypes type){
        columns.put(name, type);
        return this;
    }

    public Schema(){
    }

    public Schema withCreatedAt(){
        setupCreatedAt = true;
        this.insert("createdAt", DataTypes.TIMESTAMP);
        return this;
    }

    public Schema withUpdatedAt(){
        setupUpdatedAt = true;
        this.insert("updatedAt", DataTypes.TIMESTAMP);
        return this;
    }

    public Map getColumns(){
        return this.columns;
    }

    public List<String> getNamesOnly(){
        List<String> list = new ArrayList<String>();

        columns.forEach((key,value)->list.add(key));
        return list;
    }

    public Boolean hasPrimaryKey(){
        AtomicReference<Boolean> res = new AtomicReference<>(false);
        columns.forEach((key,type)->{
            if(type == DataTypes.PRIMARY_KEY)
                res.set(true);
        });
        return res.get();
    }

    public String PrimaryKey(){
        AtomicReference<String> res = new AtomicReference<>(null);
        columns.forEach((key,type)->{
            if(type == DataTypes.PRIMARY_KEY)
                res.set(key);
        });
        return res.get();
    }

    public DataTypes getColumnType(String columnName) {
        return columns.get(columnName);
    }


}
