import sequelize.DataTypes;
import sequelize.Sequelize;
import sequelize.exception.InvalidColumnNameException;
import sequelize.exception.InvalidTypeException;
import sequelize.model.Model;
import sequelize.model.Schema;
import sequelize.model.SchemaDTO;
import sequelize.model.SchemaValues;
import sequelize.exception.InvalidAttributeException;
import sequelize.exception.NoSchemaProvidedException;
import sequelize.statement.Operation;
import sequelize.statement.Statement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Main {

    public static void main(String[] args)  {
        Sequelize seq = new Sequelize("postgres","rsadmin","rsadminpassword");


        Schema testSchema = new Schema();

        testSchema
                .insert("name", DataTypes.TEXT)
                .insert("surname", DataTypes.TEXT)
                .insert("views", DataTypes.INTEGER)
                .withCreatedAt()
                .withUpdatedAt();

        Model testModel = seq.define("Test", testSchema);

        Schema test2Schema = new Schema();
        test2Schema
                .insert("qq", DataTypes.TEXT);

        Model test2Model = seq.define("Test2", test2Schema);

        test2Model.belongsTo(testModel, "test1_id");
        seq.sync();


    }

}
