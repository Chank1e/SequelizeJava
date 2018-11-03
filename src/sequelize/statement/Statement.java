package sequelize.statement;

import sequelize.model.Model;
import sequelize.model.Schema;
import sequelize.exception.InvalidAttributeException;
import sequelize.exception.NoSchemaProvidedException;

import java.util.ArrayList;
import java.util.List;

public class Statement {
    private Model model;
    private Schema schema;
    private String sql = "";
    private List<String> attributes = new ArrayList<>();

    public Statement where(String columnName, Operation op, List<String> params){
        sql += " WHERE "+columnName+" ";
        sql += op.getAsSQL();
        sql += "\'" + params.get(0) + "\'";
        return this;
    }

    public Statement(){}

    public Statement(Model model){
        this.model = model;
    }

    public Statement setModel(Model model) {
        this.model = model;
        this.schema = model.getSchema();
        return this;
    }

    public Model getModel() {
        return model;
    }

    public Statement attributes(List<String> attrs) throws InvalidAttributeException, NoSchemaProvidedException {
        if( schema == null )
            throw new NoSchemaProvidedException();
        for(String attr : attrs){
            if(!schema.getNamesOnly().contains(attr))
                throw new InvalidAttributeException("Can't find attribute with name \"" + attr + "\"");
        }
        this.attributes = attrs;

        return this;
    }

    public List<String> getAttributes() {
        return attributes;
    }

    public Statement and(){
        sql+=" AND ";
        return this;
    }

    public Statement or(){
        sql+=" OR ";
        return this;
    }


    public String getSql() {
        return sql;
    }
}
