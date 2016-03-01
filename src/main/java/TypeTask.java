import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class TypeTask {
  public static int getId(String type) {
    String sql = "SELECT id FROM type_task WHERE name LIKE :input";
    try (Connection con = DB.getSql2o().open()) {
      return (int) con.createQuery(sql)
      .addParameter("input", type)
      .executeScalar(Integer.class);
    }
  }

  public static String getTypeTaskName(int typeTaskId) {
    String sql = "SELECT name FROM type_task WHERE id = :typeTaskId";
    try (Connection con = DB.getSql2o().open()) {
      return con.createQuery(sql)
      .addParameter("typeTaskId", typeTaskId)
      .executeScalar(String.class);
    }
  }
}
