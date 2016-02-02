import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class DatabaseRule extends ExternalResource {

  protected void before() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/project_tracking_test", null, null);
   }

  protected void after() {
    try(Connection con = DB.sql2o.open()) {
      String deleteTasksMessagesQuery = "DELETE FROM tasks_messages *;";
       String deleteMessagesBrandsQuery = "DELETE FROM messages *;";
       String deleteTasksQuery = "DELETE FROM tasks *;";
       String deleteUsersQuery = "DELETE FROM users *;";
       con.createQuery(deleteTasksMessagesQuery).executeUpdate();
       con.createQuery(deleteMessagesBrandsQuery).executeUpdate();
       con.createQuery(deleteTasksQuery).executeUpdate();
       con.createQuery(deleteUsersQuery).executeUpdate();
    }
  }
}
