import org.sql2o.*;

public class Role {
  public static int getId(String role) {
    String sql = "SELECT id FROM roles WHERE name LIKE :name";
    try (Connection con = DB.getSql2o().open()) {
      return (int) con.createQuery(sql)
      .addParameter("name", role)
      .executeScalar(Integer.class);
    }
  }
}
