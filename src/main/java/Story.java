import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class Story {
  private int mId;
  private String mTitle;
  private int mStatus;
  private String mDescription;
  private int mTypeId;
  private int mImplementorId;
  private int mCreatorId;

  public int getId(){
    return mId;
  }

  public String getTitle() {
    return mTitle;
  }

  public int getStatus() {
    return mStatus;
  }

  public String getDescription() {
    return mDescription;
  }

  public int getTypeTask() {
    return mTypeId;
  }

  public int getImplementorId() {
    return mImplementorId;
  }

  public int getCreatorId() {
    return mCreatorId;
  }

  public Story(String title, int creatorId, int status, String description, int type, int implementorId) {
     this.mTitle = title;
     this.mStatus = status;
     this.mDescription = description;
     this.mTypeId = type;
     this.mImplementorId = implementorId;
     this.mCreatorId = creatorId;
  }

  @Override
  public boolean equals(Object otherStory) {
    if (!(otherStory instanceof Story)) {
      return false;
    } else {
      Story newStory = (Story) otherStory;
      return this.getTitle().equals(newStory.getTitle()) &&
             this.getStatus() == newStory.getStatus() &&
             this.getDescription().equals(newStory.getDescription()) &&
             this.getTypeTask() == (newStory.getTypeTask()) &&
             this.getImplementorId() == newStory.getImplementorId() &&
             this.getCreatorId() == newStory.getCreatorId();
    }
  }

  public void save() {
    String sql = "INSERT INTO tasks (title, creator_user_id, status_id, description, type_task_id, developer_id) VALUES (:title, :creatorUser, :status, :description, :typeId, :implementorUser)";
    try(Connection con = DB.sql2o.open()) {
      this.mId = (int) con.createQuery(sql, true)
        .addParameter("title", this.mTitle)
        .addParameter("status", this.mStatus)
        .addParameter("description", this.mDescription)
        .addParameter("typeId", this.mTypeId)
        .addParameter("implementorUser", this.mImplementorId)
        .addParameter("creatorUser", this.mCreatorId)
        .executeUpdate()
        .getKey();
    }
  }

  public static Story find(int id) {
    String sql = "SELECT id AS mId, title AS mTitle, creator_user_id AS mCreatorId, status_id AS mStatus, description AS mDescription, type_task_id AS mTypeId, developer_id AS mImplementorId FROM tasks WHERE id = :id";
    try(Connection con = DB.sql2o.open()) {
      Story story = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Story.class);
    return story;
    }
  }

  public static List<Story> all() {
    String sql = "SELECT id AS mId, title AS mTitle, creator_user_id AS mCreatorId, status_id AS mStatus, description AS mDescription, type_task_id AS mTypeId, developer_id AS mImplementorId FROM  tasks WHERE type_task_id = : type_id";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
        .addParameter("type_id", getTypeTask())
        .executeAndFetch(Story.class);
    }
  }

  public void update(String title, String description) {
    String sql = "UPDATE tasks SET title = :title, description = :description WHERE id = :id";
    try(Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
        .addParameter("title", mTitle)
        .addParameter("description", mDescription)
        .addParameter("id", this.mId)
        .executeUpdate();
        this.mTitle = title;
        this.mDescription = description;
    }
  }

  public void updateStatus(int statusId) {
    String sql = "UPDATE tasks SET status_id = :statusId WHERE id = :id";
    try(Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
        .addParameter("statusId", statusId)
        .addParameter("id", this.mId)
        .executeUpdate();
        this.mStatus = statusId;
    }
  }

  public void updateImplementor(int implementorId) {
    String sql = "UPDATE tasks SET developer_id = :implementorId WHERE id = :id";
    try(Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
        .addParameter("implementorId", implementorId)
        .addParameter("id", this.mId)
        .executeUpdate();
        this.mImplementorId = implementorId;
    }
  }

  public void delete() {
    String sql = "DELETE FROM tasks WHERE id = :id";
    try(Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
        .addParameter("id", this.mId)
        .executeUpdate();
    }
  }

}
