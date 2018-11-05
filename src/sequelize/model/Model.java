package sequelize.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import sequelize.DataTypes;
import sequelize.ErrorHandler;
import sequelize.SQLExecutor;
import sequelize.model.relation.BelongsToRelation;
import sequelize.model.relation.Relation;
import sequelize.model.relation.RelationList;
import sequelize.model.relation.RelationOptions;
import sequelize.statement.Statement;
import sequelize.statement.StatementType;
import sequelize.statement.newStatement;

public class Model {
    private String name;
    private Schema schema;

    private RelationList relations = new RelationList();


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

        Relation newRelation = new BelongsToRelation();
        newRelation.setSource(this);
        newRelation.setTarget(anotherModel);
        newRelation.setOptions(
                new RelationOptions()
                    .setSourceFK(foreignKey)
                    .setTargetPK(anotherModel.getSchema().PrimaryKey())
        );

        relations.addRelation(newRelation);

    }

    public Boolean hasRelations(){
        return relations.size() > 0;
    }

    public RelationList getRelations() {
        return relations;
    }

    public List<Relation> getIterableRelations(){
        return relations.getIterable();
    }

    public CompletableFuture<SchemaDTO> create(SchemaValues schemaV) {

        if(schemaV.getSchema() == null)
            schemaV.setSchema(this.schema);

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
                ErrorHandler.handle(e);
                return new SchemaDTO();
            }
        });

    }

    public CompletableFuture<SchemaDTO> findAll(newStatement statement){

        if(statement.getModel() == null)
            statement.setModel(this);

        SchemaDTO res = new SchemaDTO();
        res.setSchema(this.getSchema());

        if(statement.getAttributes().size()>0){
            res.setAttributes(statement.getAttributes());
        }

        statement.setType(StatementType.FIND_ALL);
        String sql = statement.getSQL();

        return SQLExecutor.executeWithResult(sql).thenApply(DBres -> {
            try {
                return res.from(DBres);
            } catch (SQLException e) {
                ErrorHandler.handle(e);
                return new SchemaDTO();
            }
        });
    }

    public CompletableFuture<SchemaDTO> findOne(newStatement statement){

        if(statement.getModel() == null)
            statement.setModel(this);

        SchemaDTO res = new SchemaDTO();
        res.setSchema(this.getSchema());

        if(statement.getAttributes().size()>0){
            res.setAttributes(statement.getAttributes());
        }

        statement.setType(StatementType.FIND_ONE);
        String sql = statement.getSQL();

        return SQLExecutor.executeWithResult(sql).thenApply(DBres -> {
            try {
                return res.from(DBres);
            } catch (SQLException e) {
                ErrorHandler.handle(e);
                return new SchemaDTO();
            }
        });
    }

    public CompletableFuture<SchemaDTO> update(SchemaValues newValues, newStatement statement) {

        if(statement.getModel() == null)
            statement.setModel(this);

        if(newValues.getSchema() == null)
            newValues.setSchema(this.schema);

        String sql = "UPDATE \"" + this.name + "\" SET ";

        List<String> updateSql = new ArrayList<>();

        newValues.getValues().forEach((String key, Object value) -> {
            DataTypes tmpType = (DataTypes)schema.getColumns().get(key);
            updateSql.add(key + " = " + tmpType.from(value));
        });

        sql += String.join("," , updateSql);

        sql += " " + statement.getSQL();

        sql += " RETURNING *;";

        SchemaDTO res = new SchemaDTO();
        res.setSchema(this.getSchema());

        return SQLExecutor.executeWithResult(sql).thenApply(DBres -> {
            try {
                return res.from(DBres);
            } catch (SQLException e) {
                ErrorHandler.handle(e);
                return new SchemaDTO();
            }
        });
    }

    public CompletableFuture<SchemaDTO> delete(newStatement statement) {

        statement.setModel(this);

        String sql = "DELETE FROM \"" + this.name + "\"";

        sql += " " + statement.getSQL();

        sql += " RETURNING *;";

        SchemaDTO res = new SchemaDTO();
        res.setSchema(this.getSchema());

        return SQLExecutor.executeWithResult(sql).thenApply(DBres -> {
            try {
                return res.from(DBres);
            } catch (SQLException e) {
                ErrorHandler.handle(e);
                return new SchemaDTO();
            }
        });
    }


}
