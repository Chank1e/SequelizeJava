package sequelize.model;


import sequelize.SQLExecutor;
import sequelize.datatypes._Factory;
import sequelize.datatypes._IndexDatatypeInterface;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Objects;

public class Model {
    private String name;
    private Schema schema;

    public Model(String name, Schema schema){
        this.schema = schema;
        this.name = name;
    }

    public void create(SchemaValues schemaV) {
        final String[] insertIntoSQL = {""};
        insertIntoSQL[0] += "INSERT INTO \"" + name + "\" (" + String.join(",", schema.getNamesOnly()) + ", createdAt, updatedAt) VALUES (";

        insertIntoSQL[0] += String.join(",", schemaV.getValuesOnly());

//        String currentTime = Long.toString(new Date().getTime());
        insertIntoSQL[0] += ", CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";

        SQLExecutor.execute(insertIntoSQL[0]);

//        System.out.println(schemaV.getValues());
    }


}
