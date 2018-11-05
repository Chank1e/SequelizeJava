package sequelize.statement;

public abstract class aStatement {
    public abstract String getSQL();

    public abstract StatementType getType();

}
