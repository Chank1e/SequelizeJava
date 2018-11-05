package sequelize.statement;

import sequelize.ErrorHandler;
import sequelize.exception.InvalidAttributeException;
import sequelize.exception.InvalidColumnNameException;
import sequelize.exception.InvalidTypeException;
import sequelize.exception.NoSchemaProvidedException;
import sequelize.model.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class Statement extends aStatement {
    private List<aStatement> chain = new ArrayList<>();
    private StatementType type;
    private List<String> attributes = new ArrayList<>();

    private Model model;

    public void setModel(Model model) {
        this.model = model;
        this.checkValues();
    }

    public Model getModel() {
        return model;
    }

    public Statement where(String columnName, Operation op, Object obj) {
        this.chain.add(new WhereStatement().setColumnName(columnName).setOp(op).setInit(obj));
        return this;
    }

    public Statement and() {
        this.chain.add(new KeywordStatement().setKeyword(Keyword.AND));
        return this;
    }

    public Statement or() {
        this.chain.add(new KeywordStatement().setKeyword(Keyword.OR));
        return this;
    }

    public Statement setAttributes(List<String> attrs) {
        this.attributes = attrs;
        return this;
    }

    public List<String> getAttributes() {
        return attributes;
    }

    public void setType(StatementType type) {
        this.type = type;
    }

    @Override
    public String getSQL() {

        String[] resultSQL = {""};
        if(type == StatementType.FIND_ONE || type == StatementType.FIND_ALL) {
            resultSQL[0] += "SELECT ";
            resultSQL[0] += attributes.size()>0?String.join(",",attributes):" * ";
            resultSQL[0] += " FROM \"" + this.model.getName() + "\"";
        }
        AtomicReference<Boolean> isFirst = new AtomicReference<>(true);
        this.chain.forEach((aStatement statement) -> {

            if(statement.getType() == StatementType.WHERE){
                WhereStatement tmpStatement = (WhereStatement) statement;

                tmpStatement.setFirst(isFirst.get()).setColumnType(this.model.getSchema().getColumnType(tmpStatement.getColumnName()));
                resultSQL[0] += tmpStatement.getSQL();

            } else {
                resultSQL[0] += statement.getSQL();
            }
            isFirst.set(false);
        });


        if(type == StatementType.FIND_ONE)
            resultSQL[0] += " LIMIT 1";

        return resultSQL[0];
    }

    @Override
    public StatementType getType() {
        return type;
    }

    public void checkValues(){
        if(model == null || model.getSchema() == null)
            try {
                throw new NoSchemaProvidedException();
            } catch (NoSchemaProvidedException e) {
                ErrorHandler.handle(e);

            }

        for(String attr : attributes){
            if(!model.getSchema().getNamesOnly().contains(attr))
                try {
                    throw new InvalidAttributeException("Can't find attribute with name \"" + attr + "\"");
                } catch (InvalidAttributeException e) {
                    ErrorHandler.handle(e);
                }
        }

        chain.forEach((aStatement statement) -> {
            if(statement.getType() == StatementType.WHERE){
                WhereStatement whereStatement = (WhereStatement) statement;
                if(model.getSchema().getColumns().get(whereStatement.getColumnName()) == null )
                    try {
                        throw new InvalidColumnNameException("Column " + whereStatement.getColumnName() + " is not defined");
                    } catch (InvalidColumnNameException e) {
                        ErrorHandler.handle(e);
                    }

                if(!model.getSchema().getColumnType(whereStatement.getColumnName()).is(whereStatement.getInit()))
                    try {
                        throw new InvalidTypeException();
                    } catch (InvalidTypeException e) {
                        ErrorHandler.handle(e);

                    }

            }
        });
    }
}
