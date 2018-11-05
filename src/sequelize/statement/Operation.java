package sequelize.statement;

public enum  Operation {
    gt, lt, eq, like;
    private String asSQL;

    static {
        gt.asSQL = ">";
        lt.asSQL = "<";
        eq.asSQL = "=";
        like.asSQL = "LIKE";
    }

    public String getAsSQL() {
        return asSQL;
    }
}
