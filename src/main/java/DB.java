import org.sql2o.*;
import java.net.*;

public class DB {
  public static Sql2o getSql2o(){
    Sql2o sql2o = null;
    URI dbUri = null;
    try {
      dbUri = new URI(System.getenv("DATABASE_URL"));
    } catch (URISyntaxException e) {
      System.err.println("IndexOutOfBoundsException: " + e.getMessage());
    } catch (NullPointerException npe) {
      System.err.println("ikuchko: NullPointerException: " + npe.getMessage());
    } finally {
      if (dbUri == null) {
        return new Sql2o("jdbc:postgresql://localhost:5432/project_tracking", null, null);
      } else {
        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + dbUri.getPath();
        return new Sql2o(dbUrl, username, password);
      }
    }
  }
   public static Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/project_trackin", null, null);

}
