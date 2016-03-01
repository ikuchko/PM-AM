import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class History {
  private int mId;
  private int mTaskId;
  private String mChangeType;
  private String mPreviousCondition;
  private String mCurrentCondition;
  private String mCreatedDate;
  // private static final int EPIC = 1;
  // private static final int TASK = 1;
  // private static final int STORY = 1;
  // private static final int BUG = 1;

  public int getId(){
    return mId;
  }

  public int getTaskId() {
    return mTaskId;
  }

  public String getChangeType() {
    return mChangeType;
  }

  public String getPreviousCondition() {
    return mPreviousCondition;
  }

  public String getCurrentCondition() {
    return mCurrentCondition;
  }

  public String getCreatedDate() {
    return mCreatedDate;
  }


  public History(int taskId, String changeType, String previousCondition, String currentCondition) {
    this.mTaskId = taskId;
    this.mChangeType = changeType;
    this.mPreviousCondition = previousCondition;
    this.mCurrentCondition = currentCondition;
    save();
  }

  @Override
  public boolean equals(Object otherHistory) {
    if (!(otherHistory instanceof History)) {
      return false;
    } else {
      History newHistory = (History) otherHistory;
      return
             this.getId() == (newHistory.getId()) &&
             this.getTaskId() == (newHistory.getTaskId()) &&
             this.getChangeType().equals(newHistory.getChangeType()) &&
             this.getPreviousCondition().equals(newHistory.getPreviousCondition()) &&
             this.getCurrentCondition().equals(newHistory.getCurrentCondition()) &&
             this.getCreatedDate().equals(newHistory.getCreatedDate());
    }
  }

  public void save() {
    String sql = "INSERT INTO histories(task_id, change_type, previous_condition, current_condition) VALUES (:taskId, :changeType, :previousCondition, :currentCondition)";
    try(Connection con = DB.getSql2o().open()) {
      this.mId = (int) con.createQuery(sql, true)
        .addParameter("taskId", this.mTaskId)
        .addParameter("changeType", this.mChangeType)
        .addParameter("previousCondition", this.mPreviousCondition)
        .addParameter("currentCondition", this.mCurrentCondition)
        .executeUpdate()
        .getKey();
        History newHistory = find(this.mId);
        this.mCreatedDate = newHistory.getCreatedDate();
    }
  }

  public static History find(int id) {
    String sql = "SELECT id AS mId, task_id AS mTaskId, change_type AS mChangeType, previous_condition AS mPreviousCondition, current_condition AS mCurrentCondition, created_date as mCreatedDate FROM histories WHERE id = :id";
    try(Connection con = DB.getSql2o().open()) {
      History history = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(History.class);
    return history;
    }
  }

  public static List<History> all(int taskId) {
    String sql = "SELECT id AS mId, task_id AS mTaskId, change_type AS mChangeType, previous_condition AS mPreviousCondition, current_condition AS mCurrentCondition, created_date as mCreatedDate FROM histories WHERE task_id = :taskId";
    try(Connection con = DB.getSql2o().open()) {
      return con.createQuery(sql)
        .addParameter("taskId", taskId)
        .executeAndFetch(History.class);
    }
  }

  public void delete() {
    String sql = "DELETE FROM histories WHERE id = :id";
    try(Connection con = DB.getSql2o().open()) {
      con.createQuery(sql)
        .addParameter("id", this.mId)
        .executeUpdate();
    }
  }
}
