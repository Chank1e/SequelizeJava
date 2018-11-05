package sequelize.model.relation;

import sequelize.model.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class RelationList {
    private List<Relation> relations = new ArrayList<>();

    public void addRelation(Relation newRelation){
        relations.add(newRelation);
    }

    public Boolean hasRelation(Model model1, Model model2) {
        AtomicReference<Boolean> res = new AtomicReference<>(false);
        relations.forEach((Relation rel)->{
            List<Model> type1 = new ArrayList<>();
            type1.add(model1);
            type1.add(model2);

            List<Model> type2 = new ArrayList<>();
            type2.add(model1);
            type2.add(model2);

            if(rel.getModels().equals(type1) || rel.getModels().equals(type2))
                res.set(true);
        });

        return res.get();
    }

    public Integer size(){
        return relations.size();
    }

    public List<Relation> getIterable(){
        return relations;
    }
}
