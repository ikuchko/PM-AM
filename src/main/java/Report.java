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
    this.mDuration = formattedDuration(findDuration(taskId));
  }

  public String getStartDate() {
    return mStartDate;
  }
  public String getFinishDate() {
    if (mFinishDate == null){
      return "N/A";
    } else {
    return mFinishDate; }
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
    try(Connection con = DB.getSql2o().open()) {
      return con.createQuery(sql)
      .addParameter("doneStatus", "Done")
      .addParameter("status", "Update Status")
      .addParameter("taskId", taskId)
      .executeScalar(String.class);
    }
  }

  public static String findDuration(int taskId) {
    String sql = "SELECT (date_created - created_date) AS duration FROM histories INNER JOIN tasks ON tasks.id=histories.task_id WHERE current_condition LIKE :doneStatus AND change_type LIKE :status AND task_id = :taskId";
    try(Connection con = DB.getSql2o().open()) {
      return (String) con.createQuery(sql)
      .addParameter("doneStatus", "Done")
      .addParameter("status", "Update Status")
      .addParameter("taskId", taskId)
      .executeScalar(String.class);
    }

  }

  public static String formattedDuration(String duration) {
    if (!(duration == null)) {
      if (duration.contains("0 years 0 mons 0 days 0 hours")) {
        return duration.replace("0 years 0 mons 0 days 0 hours ", "");
      } else if (duration.contains("0 years 0 mons 0 days")) {
        return duration.replace("0 years 0 mons 0 days ", "");
      } else if (duration.contains("0 years 0 mons")) {
        return duration.replace("0 years 0 mons ", "");
      } else if (duration.contains("0 years")) {
       return duration.replace("0 years ", "");
     } else{
       return duration;
     }
   } else {
     return "N/A";
   }
  }

}
