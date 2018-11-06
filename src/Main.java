import sequelize.DataTypes;
import sequelize.Sequelize;
import sequelize.model.Model;
import sequelize.model.Schema;
import sequelize.model.SequelizeResult;
import sequelize.model.SchemaValues;
import sequelize.statement.Operation;
import sequelize.statement.Statement;

public class Main {

    public static void main(String[] args)  {
        Sequelize seq = new Sequelize("postgres","postgres","");


        Schema UserSchema = new Schema()
                                    .withUpdatedAt()
                                    .withCreatedAt()
                                    .insert("name", DataTypes.TEXT)
                                    .insert("surname", DataTypes.TEXT);

        Schema RoleSchema = new Schema()
                                    .withUpdatedAt()
                                    .withCreatedAt()
                                    .insert("role", DataTypes.INTEGER);

        Model User = seq.define("User", UserSchema);
        Model Role = seq.define("Role", RoleSchema);

        User.belongsTo(Role, "role_id");
//        Role.hasMany(User);


        seq.sync();

        User.create(
                new SchemaValues()
                    .insert("name","Name1")
                    .insert("surname","SurName1")
        );

        User.create(
                new SchemaValues()
                    .insert("name","Name2")
                    .insert("surname","SurName2")
        );

        User.create(
                new SchemaValues()
                    .insert("name","Name3")
                    .insert("surname","SurName3")
        );


        User.findOne(
                new Statement()
//                    .setAttributes(Arrays.asList("id", "name"))
                    .where("id", Operation.eq, 1)
                    .include(Role)
//                    .include(Role, new Statement())
        ).thenAccept((SequelizeResult res)->{
           System.out.println(res.getData());
        });





    }

}
