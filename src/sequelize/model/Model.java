package sequelize.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import sequelize.DataTypes;
import sequelize.SQLExecutor;
import sequelize.statement.Statement;

public class Model {
    private String name;
    private Schema schema;
    /**
     * Another Table name : [Foreign Key name, Another Table PK]
     */
    private Map<String, List<String>> relations = new HashMap<>();


    public Model(String name, Schema schema){
        this.schema = schema;
        this.name = name;
    }

    public Schema getSchema() {
        return schema;
    }

    public String getName() {
        return name;
    }

    public void belongsTo(Model anotherModel, String foreignKey){
        List<String> FK_PK = new ArrayList<>();

        FK_PK.add(foreignKey);

        String anotherModelPK = anotherModel.getSchema().PrimaryKey();
        if(anotherModelPK == null)
            anotherModelPK = "id";

        FK_PK.add(anotherModelPK);

        relations.put(anotherModel.getName(), FK_PK);
    }

    public Boolean hasRelations(){
        return relations.size() > 0;
    }

    public Map<String, List<String>> getRelations() {
        return relations;
    }

    public CompletableFuture<SchemaDTO> create(SchemaValues schemaV) {
        final String[] insertIntoSQL = {""};

        List<String> columnNames = new ArrayList<>();
        List<String> columnValues = new ArrayList<>();

        schemaV.getValues().forEach((key, value)->{
            DataTypes tmpType = (DataTypes)schema.getColumns().get(key);
            columnNames.add(key);
            columnValues.add(tmpType.from(value));
        });

        if(schema.hasUpdatedAt()){
            columnNames.add("updatedAt");
            columnValues.add("CURRENT_TIMESTAMP");
        }

        if(schema.hasCreatedAt()){
            columnNames.add("createdAt");
            columnValues.add("CURRENT_TIMESTAMP");
        }



        insertIntoSQL[0] += "INSERT INTO \"" + name + "\" (" + String.join(",", columnNames) + ") VALUES (";

        insertIntoSQL[0] += String.join(",", columnValues);

        insertIntoSQL[0] += ") RETURNING *";

        SchemaDTO res = new SchemaDTO();
        res.setSchema(this.getSchema());

        return SQLExecutor.executeWithResult(insertIntoSQL[0]).thenApply(DBres -> {
            try {
                return res.from(DBres);
            } catch (SQLException e) {
                e.printStackTrace();
                return new SchemaDTO();
            }
        });

    }

    public CompletableFuture<SchemaDTO> findAll(Statement statement){
        SchemaDTO res = new SchemaDTO();
        res.setSchema(this.getSchema());

        String sql = "SELECT ";

        if(statement.getAttributes().size()>0){
            sql+=String.join(",",statement.getAttributes());
            res.setAttributes(statement.getAttributes());
        } else {
            sql += "*";
        }

        sql += " FROM \"" + name + "\"";
        sql += statement.getSql();


        return SQLExecutor.executeWithResult(sql).thenApply(DBres -> {
            try {
                return res.from(DBres);
            } catch (SQLException e) {
                e.printStackTrace();
                return new SchemaDTO();
            }
        });
    }


}
