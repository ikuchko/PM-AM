import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class Status {
  public static int getId(String status) {
    String sql = "SELECT id FROM status WHERE status LIKE :input";
    try (Connection con = DB.sql2o.open()) {
      return (int) con.createQuery(sql)
      .addParameter("input", status)
      .executeScalar(Integer.class);
    }
  }

  public static String getStatusName(Int statusId) {
    String sql = "SELECT status FROM status WHERE id = :statusId";
    try (Connection con = DB.sql2o.open()) {
      return (int) con.createQuery(sql)
      .addParameter("statusId", statusId)
      .executeScalar(String.class);
    }
  }
}
