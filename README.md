# MyORM
MyORM greatly facilitates interaction with the database.


I intend to implement an ORM that does not automatically handle one-to-many and many-to-one relationships, nor does it want to introduce complex states to Entities. Therefore, for an Entity, it is simply a pure JavaBean without any proxies.

In addition, the ORM should balance usability and versatility. Usability means covering 95% of application scenarios, but there are always some complex SQL queries that are difficult to generate automatically with ORM. Therefore, we also need to provide native JDBC interfaces to support 5% of special requirements.

Finally, I hope that the designed interface is easy to write and uses a fluent API for readability. To facilitate compiler checks, it should also support generics to avoid forced type casting.

Taking the User class as an example, the designed query interface is as follows:

// Query by primary key: SELECT * FROM users WHERE id = ?
User u = db.get(User.class, 123);

// Unique record query with conditions: SELECT * FROM users WHERE email = ? AND password = ?
User u = db.from(User.class)
.where("email=? AND password=?", "Alice@example.com", "alice123")
.unique();

// Multiple record query with conditions: SELECT * FROM users WHERE id < ? ORDER BY email LIMIT ?, ?
List<User> us = db.from(User.class)
.where("id < ?", 1000)
.orderBy("email")
.limit(0, 10)
.list();

// Query specific columns: SELECT id, name FROM users WHERE email = ?
User u = db.select("id", "name")
.from(User.class)
.where("email = ?", "Alice@example.com")
.unique();

This fluent API is easy to read and it's straightforward to deduce the resulting SQL.

For insert, update, and delete operations, they are relatively simple:

// Insert User:
db.insert(user);

// Update User by primary key:
db.update(user);

// Delete User by primary key:
db.delete(User.class, 123);

For Entities, typically one table corresponds to one Entity. Manually listing all Entities is very cumbersome, so it's necessary to pass in a package for automatic scanning.

Finally, ORM always requires metadata to understand how to map. We do not want to write complex XML configurations, nor is it necessary to define our own rules. Instead, we can directly use JPA annotations.