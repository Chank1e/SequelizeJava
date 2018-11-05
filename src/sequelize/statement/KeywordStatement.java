package sequelize.statement;

public class KeywordStatement extends aStatement {
    private StatementType type = StatementType.KEYWORD;
    private Keyword keyword;

    public KeywordStatement setKeyword(Keyword keyword) {
        this.keyword = keyword;
        return this;
    }

    public Keyword getKeyword() {
        return keyword;
    }

    @Override
    public String getSQL() {
        return " " + this.keyword.getAsSQL() + " ";
    }

    @Override
    public StatementType getType() {
        return type;
    }
}
