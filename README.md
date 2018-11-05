#### SequelizeJava - ORM for PostgreSQL

Simplicity of [sequelize](http://docs.sequelizejs.com/) from NodeJS to Java.

- Creating instance of `Sequelize`:
```java
import sequelize.Sequelize;

Sequelize sequelize = new Sequelize("DBNAME", "USER","PASSWORD");
```

- Creating `Schema` for your `Model`:
```java

import sequelize.model.Schema;

Schema UserSchema = new Schema()
                        .withUpdatedAt() // It will create `updatedAt` timestamp to control the date of last update
                        .withCreatedAt() // It will create `createdAt` timestamp to control the date of creation
                        .insert("name", DataTypes.TEXT)
                        .insert("surname", DataTypes.TEXT);

```
`Schema` is like table in Postgres. You should describe columns you need in `Schema`.

- Connect your `Schema` with database and create `Model`:
```java
import sequelize.model.Model;

Model User = sequelize.define("User", UserSchema);

```
Here you say to `Sequelize` that in table `User` there are data in UserSchema format.

- Sync your models with database:
```java
sequelize.sync();
```
This method will delete all tables with each model name if it exists and create them back with columns from `Model Schema`


#### `Model`:
| Method                           | Returns                    |
|----------------------------------|----------------------------|
| .create(SchemaValues)            | CompletedFuture<SchemaDTO> |
| .findAll(Statement)              | CompletedFuture<SchemaDTO> |
| .findOne(Statement)              | CompletedFuture<SchemaDTO> |
| .update(SchemaValues, Statement) | CompletedFuture<SchemaDTO> |
| .delete(Statement)               | CompletedFuture<SchemaDTO> |

#### `Statement`:
| Method                                             | Example                                                        | SQL                          |
|----------------------------------------------------|----------------------------------------------------------------|------------------------------|
| .where(String columnName, Operator op, Object obj) | .where("id", Operator.eq, 1)                                   | ... WHERE id = 1             |
| .and()                                             | .where("id",Operator.gt,1) .and() .where("id",Operator.lt, 10) | ... WHERE id > 1 AND id < 10 |
| .or()                                              | .where("id",Operator.gt,1) .or() .where("id",Operator.lt, 10)  | ... WHERE id > 1 OR id < 10  |
| .setAttributes(List<String> attributes)            | .setAttributes(Arrays.asList("id","name"))                     | SELECT "id","name" ...       |

#### `Schema`:
| Method                               | Example                         |
|--------------------------------------|---------------------------------|
| .insert(String name, DataTypes type) | .insert("name", DataTypes.TEXT) |
| .withCreatedAt()                     |                                 |
| .withUpdatedAt()                     |                                 |
