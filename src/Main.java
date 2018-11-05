import sequelize.DataTypes;
import sequelize.Sequelize;
import sequelize.model.Model;
import sequelize.model.Schema;
import sequelize.model.SchemaValues;
import sequelize.statement.Operation;
import sequelize.statement.Statement;
import sequelize.statement.newStatement;

public class Main {

    public static void main(String[] args)  {
        Sequelize seq = new Sequelize("postgres","rsadmin","rsadminpassword");


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


        seq.sync();

        User.create(
                new SchemaValues()
                    .insert("name","Alexandr")
                    .insert("surname","Pashkevich")
        );

        User.delete(
                new newStatement()
                    .where("id", Operation.eq, 1)
                    .and()
                    .where("name", Operation.eq, "Alexandr")
        );





    }

}
