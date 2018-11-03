package sequelize.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SchemaDTO {
    private Schema schema;
    private List<Map<String, Object>> data = new ArrayList<>();
    private List<String> attributes = new ArrayList<>();

    public SchemaDTO from(ResultSet DBres) throws SQLException {
            List<String> columns;

            if(attributes != null && attributes.size()>0)
                columns = attributes;
            else
                columns = schema.getNamesOnly();

            while(DBres.next()){
                Map<String, Object> tmp = new HashMap<>();

                columns.forEach((String columnName) -> {
                    try {
                        tmp.put(columnName, DBres.getObject(columnName));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
                data.add(tmp);

            }
            return this;
    }

    public SchemaDTO(){}

    public SchemaDTO(Schema schema) { this.schema = schema; }

    public void setSchema(Schema schema) {
        this.schema = schema;
    }

    public void setAttributes(List<String> attributes) {
        this.attributes = attributes;
    }

    public List<Map<String, Object>> getData() {
        return data;
    }
}
