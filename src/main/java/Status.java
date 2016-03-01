import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class Status {
  private int id;
  private String status;

  public Status(String statusName) {
    this.status = statusName;
  }

  public int getId() {
    return id;
  }
  public String getStatus() {
    return status;
  }

  public static int getId(String status) {
    String sql = "SELECT id FROM status WHERE status LIKE :input";
    try (Connection con = DB.getSql2o().open()) {
      return (int) con.createQuery(sql)
      .addParameter("input", status)
      .executeScalar(Integer.class);
    }
  }

  public static String getStatusName(int statusId) {
    String sql = "SELECT status FROM status WHERE id = :statusId";
    try (Connection con = DB.getSql2o().open()) {
      return con.createQuery(sql)
      .addParameter("statusId", statusId)
      .executeScalar(String.class);
    }
  }

  public static List<Status> all() {
    String sql = "SELECT id, status FROM status";
    try (Connection con = DB.getSql2o().open()) {
      return con.createQuery(sql)
      .executeAndFetch(Status.class);
    }
  }

  public static int getNextStatus(Task task){
    int statusId = 0;
    if (Status.getStatusName(task.getStatus()).equals("To Do")) {
      statusId = Status.getId("In Progress");
    } else if (Status.getStatusName(task.getStatus()).equals("In Progress")) {
      statusId = Status.getId("Test");
    } else {
      statusId = Status.getId("Done");
    }
    return statusId;
  }
}
