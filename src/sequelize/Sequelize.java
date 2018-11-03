package sequelize;

import sequelize.model.Model;
import sequelize.model.Schema;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Sequelize {


    public Model define(String name, Schema schema) {
        if(!schema.hasPrimaryKey())
            schema.insert("id", DataTypes.PRIMARY_KEY);
        Model nModel = new Model(name, schema);
        models.add(nModel);
        return nModel;
    }

    List<Model> models = new ArrayList<>();

    public Sequelize(String database, String username, String password){
        String url = "jdbc:postgresql://localhost/"+database;
        try {
            SQLExecutor.setConnection(new ConnectionDB().connect(url, username, password));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public CompletableFuture<Boolean> sync() {

        List<String> relationsSQL = new ArrayList<>();

        models.forEach((Model model) -> {
            String name = model.getName();
            Schema schema = model.getSchema();

            final String[] createTableSQL = {""};
            createTableSQL[0] += "DROP TABLE IF EXISTS \"" + name + "\" CASCADE;";
            createTableSQL[0] += "CREATE TABLE IF NOT EXISTS \"" + name + "\" (";
            List<String> schemaSQL = new ArrayList<>();
            schema.getColumns().forEach((key, value) -> {
                String columnName = (String) key;
                DataTypes columnType = (DataTypes) value;

                schemaSQL.add(columnName + " " + columnType.getAsSQL());
            });

            createTableSQL[0] += String.join(",", schemaSQL);


            if(model.hasRelations()) {
                model.getRelations().forEach((String tName, List<String> keys) -> {
//                    relationsSQL.add(keys.get(0) + " " + DataTypes.INTEGER.getAsSQL() + " REFERENCES " + tName + "(" + keys.get(1) + ")");
                    relationsSQL
                            .add(
                                    "ALTER TABLE \"" + model.getName() + "\" " +
                                    "ADD COLUMN " + keys.get(0) + " " + DataTypes.INTEGER.getAsSQL() + "," +
                                    "ADD FOREIGN KEY (" + keys.get(0) + ") " +
                                    "REFERENCES \"" + tName + "\" ("+keys.get(1) + ");"
                            );
                });
            }
            createTableSQL[0] += ");";

            SQLExecutor.execute(createTableSQL[0]);

        });

        SQLExecutor.execute(String.join("",relationsSQL));

        return CompletableFuture.completedFuture(true);


    }
}
