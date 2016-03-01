import org.sql2o.*;
import java.net.*;

public class DB {
  public static Sql2o getSql2o(){
    URI dbUri = null;
    try {
      dbUri = new URI(System.getenv("DATABASE_URL"));
    } catch (URISyntaxException e) {
      System.err.println("IndexOutOfBoundsException: " + e.getMessage());
    }
    Sql2o sql2o = null;

    String username = dbUri.getUserInfo().split(":")[0];
    String password = dbUri.getUserInfo().split(":")[1];
    String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + dbUri.getPath();

      sql2o = new Sql2o(dbUrl, username, password);

    return sql2o;
  }
   public static Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/project_trackin", null, null);

}
