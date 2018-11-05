package sequelize.statement;

import sequelize.ErrorHandler;
import sequelize.exception.InvalidColumnNameException;
import sequelize.exception.InvalidTypeException;
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


    private List<WhereStatement> whereStatements = new ArrayList<>();

    private StatementType type;

    public void setType(StatementType type) {
        this.type = type;
    }

    public Statement where(String columnName, Operation op, Object init) {
        whereStatements.add(
                new WhereStatement()
                    .setColumnName(columnName)
                    .setInit(init)
                    .setOp(op)
        );

        return this;
    }

    public Statement(){}

    public Statement setModel(Model model) {
        this.model = model;
        this.schema = model.getSchema();
        this.checkValues();
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

        final String[] resultSql = {""};

        if(type == StatementType.FIND_ONE || type == StatementType.FIND_ALL) {
            resultSql[0] += "SELECT ";
            resultSql[0] += this.getAttributes().size()>0?String.join(",",this.getAttributes()):" * ";
            resultSql[0] += " FROM \"" + this.model.getName() + "\"";
        }


        final Boolean[] isFirstWhereStatement = {true};
//        whereStatements.forEach((whereStatement) -> {
//                            resultSql[0] +=
//                                    whereStatement.withKeyword(isFirstWhereStatement[0]).setColumnType(this.schema.getColumnType(whereStatement.getColumnName())).getSql();
//                            isFirstWhereStatement[0] = false;
//                        });
        resultSql[0] += sql;

        if(this.type == StatementType.FIND_ONE)
            resultSql[0] += " LIMIT 1";

        return resultSql[0];
    }

    public void checkValues(){
        if(this.schema == null)
            try {
                throw new NoSchemaProvidedException();
            } catch (NoSchemaProvidedException e) {
                ErrorHandler.handle(e);

            }

        whereStatements.forEach((WhereStatement whereStatement) -> {
            if(schema.getColumns().get(whereStatement.getColumnName()) == null )
                try {
                    throw new InvalidColumnNameException("Column " + whereStatement.getColumnName() + " is not defined");
                } catch (InvalidColumnNameException e) {
                    ErrorHandler.handle(e);
                }

            if(!this.schema.getColumnType(whereStatement.getColumnName()).is(whereStatement.getInit()))
                try {
                    throw new InvalidTypeException();
                } catch (InvalidTypeException e) {
                    ErrorHandler.handle(e);

                }
        });


//        sql += " WHERE "+columnName+" ";
//        sql += op.getAsSQL();
//        sql += " " + this.schema.getColumnType(columnName).from(init);
    }
}
