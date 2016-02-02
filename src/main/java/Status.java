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
  //
  // public static String getStatusName(int statusId) {
  //   String sql = "SELECT status FROM status WHERE id = :statusId";
  //   try (Connection con = DB.sql2o.open()) {
  //     return (String) con.createQuery(sql)
  //     .addParameter("statusId", statusId)
  //     .executeAndFetch(String.class);
  //   }
  // }
}
