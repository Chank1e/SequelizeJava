package sequelize.model.relation;

public class RelationOptions {
    String targetPK;
    String sourceFK;
    DeleteType deleteType;

    public RelationOptions setSourceFK(String sourceFK) {
        this.sourceFK = sourceFK;
        return this;
    }

    public RelationOptions setTargetPK(String targetPK) {
        this.targetPK = targetPK;
        return this;
    }

    public void setDeleteType(DeleteType deleteType) {
        this.deleteType = deleteType;
    }

    public String getSourceFK() {
        return sourceFK;
    }

    public String getTargetPK() {
        return targetPK;
    }

    public DeleteType getDeleteType() {
        return deleteType;
    }
}
