package sequelize;

import sequelize.datatypes._IndexDatatype;
import sequelize.model.Model;
import sequelize.model.Schema;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class Sequelize {


    public Model define(String name, Schema schema) {

        models.put(name, schema);
        return new Model(name, schema);
    }

    Map<String, Schema> models = new HashMap<>();

    public Sequelize(String database, String username, String password){
        String url = "jdbc:postgresql://localhost/"+database;
        try {
            SQLExecutor.setConnection(new ConnectionDB().connect(url, username, password));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public CompletableFuture<Boolean> sync() {

        for (Map.Entry<String, Schema> entryModel : models.entrySet()) {
            final String[] createTableSQL = {""};
            String name = entryModel.getKey();
            Schema schema = entryModel.getValue();
            createTableSQL[0] += "DROP TABLE IF EXISTS \"" + name + "\";";
            createTableSQL[0] += "CREATE TABLE IF NOT EXISTS \"" + name + "\" (";
            createTableSQL[0] += "id SERIAL PRIMARY KEY , ";

            schema.getSchema().forEach((key, value) -> {
                String columnName = (String) key;
                _IndexDatatype columnType = (_IndexDatatype) value;

                createTableSQL[0] += columnName + " " + columnType.getKey() + ",";


            });
            createTableSQL[0] += "createdAt timestamp NOT NULL, ";
            createTableSQL[0] += "updatedAt timestamp NOT NULL";

            createTableSQL[0] += ");";

            SQLExecutor.execute(createTableSQL[0]);
        }

        return CompletableFuture.completedFuture(true);

    }
}
