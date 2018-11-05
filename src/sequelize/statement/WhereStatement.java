package sequelize.statement;

import sequelize.DataTypes;

class WhereStatement extends aStatement{
    private String columnName;
    private Operation op;
    private Object init;
    private DataTypes columnType;
    private Boolean isFirst = true;
    private StatementType type = StatementType.WHERE;


    WhereStatement setColumnName(String columnName) {
        this.columnName = columnName;
        return this;
    }

    WhereStatement setColumnType(DataTypes type){
        this.columnType = type;
        return this;
    }

    WhereStatement setInit(Object init) {
        this.init = init;
        return this;
    }

    WhereStatement setOp(Operation op) {
        this.op = op;
        return this;
    }

    Boolean getFirst() {
        return isFirst;
    }

    WhereStatement setFirst(Boolean first) {
        isFirst = first;
        return this;
    }

    Object getInit() {
        return init;
    }

    String getColumnName() {
        return columnName;
    }

    @Override
    public String getSQL(){
        String keyword = this.getFirst() ? " WHERE ":" ";
        return keyword + columnName + " " + op.getAsSQL() + " " + columnType.from(init);
    }

    @Override
    public StatementType getType() {
        return type;
    }
}
