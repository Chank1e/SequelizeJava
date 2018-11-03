package sequelize.model;

public enum DeleteType {
    CASCADE, RESTRICT;

    private String asSQL;

    static {
        CASCADE.asSQL = "CASCADE";
        RESTRICT.asSQL = "RESTRICT";
    }

    public String getAsSQL() {
        return asSQL;
    }
}
