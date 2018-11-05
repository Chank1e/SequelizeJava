package sequelize.model.relation;

import sequelize.model.Model;

import java.util.ArrayList;
import java.util.List;

public  abstract class Relation {
    private Model source;

    private Model target;

    private RelationType type;

    private RelationOptions options;

    public Relation(){
    }

    public void setTarget(Model target) {
        this.target = target;
    }

    public void setSource(Model source) {
        this.source = source;
    }

    public void setOptions(RelationOptions options) {
        this.options = options;
    }

    public RelationOptions getOptions() {
        return options;
    }

    public Model getTarget() {
        return target;
    }

    public Model getSource() {
        return source;
    }

    public List<Model> getModels(){

        List<Model> res = new ArrayList<>();
        res.add(source);
        res.add(target);

        return res;
    }
}
