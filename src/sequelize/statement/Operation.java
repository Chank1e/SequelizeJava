package sequelize.statement;

public enum  Operation {
    gt, lt, eq;
    private String asSQL;

    static {
        gt.asSQL = ">";
        lt.asSQL = "<";
        eq.asSQL = "=";
    }

    public String getAsSQL() {
        return asSQL;
    }
}
