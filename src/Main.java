import sequelize.Sequelize;
import sequelize.model.Exception.InvalidColumnNameException;
import sequelize.model.Exception.InvalidTypeException;
import sequelize.model.Model;
import sequelize.model.Schema;
import sequelize.datatypes.*;
import sequelize.model.SchemaValues;

import java.sql.SQLException;
import java.util.Date;

public class Main {

    public static void main(String[] args)  {
        Sequelize seq = new Sequelize("postgres","rsadmin","rsadminpassword");
        Schema UserSchema = new Schema()
                .insert("Name", _Factory.getSimpleType("text"))
                .insert("Surname", _Factory.getSimpleType("text"));

        Schema ArticleSchema = new Schema()
                .insert("title", _Factory.getSimpleType("text"))
                .insert("text", _Factory.getSimpleType("text"))
                .insert("views", _Factory.getSimpleType("int"))
                .insert("lastUpdate", _Factory.getSimpleType("timestamp"));

        Model User = seq.define("User", UserSchema);
        Model Article = seq.define("Article", ArticleSchema);


        seq.sync()
                .thenAccept((Boolean isDone)->{
                    if(isDone){
                        try {
                            User.create(
                                new SchemaValues()
                                    .from(UserSchema)
                                    .withValue("Name", "Max")
                                    .withValue("Surname", "Parapapam")
                            );
                            Article.create(
                                new SchemaValues()
                                    .from(ArticleSchema)
                                    .withValue("title", "Title 1")
                                    .withValue("text","That works!!!")
                                    .withValue("views",123)
                                    .withValue("lastUpdate", new Date(1000))
                            );
                        } catch( InvalidColumnNameException | InvalidTypeException e ){
                            e.printStackTrace();
                        }

                    }
                });
    }

}
