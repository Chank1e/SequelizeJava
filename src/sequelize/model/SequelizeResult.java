package sequelize.model;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SequelizeResult {
    private List<Map<String, Object>> data = new ArrayList<>();

    public SequelizeResult from(ResultSet DBres) throws SQLException {
        ResultSetMetaData metadata = DBres.getMetaData();
        int columnCount = metadata.getColumnCount();
        while (DBres.next()) {
            Map<String, Object> tmp = new HashMap<>();
            for (int i = 1; i <= columnCount; i++) {
                tmp.put(metadata.getColumnName(i), DBres.getObject(i));
            }
            data.add(tmp);
        }

        return this;
    }

    public SequelizeResult(){}

    public List<Map<String, Object>> getData() {
        return data;
    }
}
