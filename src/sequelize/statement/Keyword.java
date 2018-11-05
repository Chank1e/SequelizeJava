package sequelize.statement;

public enum  Keyword {
    AND, OR;
    private String asSQL;

    static {
        AND.asSQL = "AND";
        OR.asSQL = "OR";
    }

    public String getAsSQL() {
        return asSQL;
    }
}
