import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;
import java.util.Date;

// project status, project duration, task amount

public class Report {

  private String mStartDate;
  private String mFinishDate;
  private String mDuration;


  public Report(int taskId) {
    Task task = Task.find(taskId);
    this.mStartDate = task.getDateCreated();
    this.mFinishDate = findFinishDate(taskId);
    this.mDuration = findDuration(taskId);
  }

  public String getStartDate() {
    return mStartDate;
  }
  public String getFinishDate() {
    return mFinishDate;
  }
  public String getDuration() {
    return mDuration;
  }

  // public static String getStartDate(int taskId) {
  //   Task mTaskId = Task.find(taskId);
  //   return mTaskId.getDateCreated();
  // }

  public static String findFinishDate(int taskId) {
    String sql = "SELECT created_date AS mDateComplete FROM histories WHERE current_condition LIKE :doneStatus AND change_type LIKE :status AND task_id = :taskId";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
      .addParameter("doneStatus", "Done")
      .addParameter("status", "Change Status")
      .addParameter("taskId", taskId)
      .executeScalar(String.class);
    }
  }

  public static String findDuration(int taskId) {
    String sql = "SELECT (date_created - created_date) AS duration FROM histories INNER JOIN tasks ON tasks.id=histories.task_id WHERE current_condition LIKE :doneStatus AND change_type LIKE :status AND task_id = :taskId";
    try(Connection con = DB.sql2o.open()) {
      return (String) con.createQuery(sql)
      .addParameter("doneStatus", "Done")
      .addParameter("status", "Change Status")
      .addParameter("taskId", taskId)
      .executeScalar(String.class);
    }
  }

  }
