
import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class Epic {
  private int mId;
  private String mTitle;
  private int mCreatorId;
  private String mDateCreated;
  private String mStatus;
  private String mDescription;
  private int mTypeTaskId;
  private int mDeveloperId;

  public int getId(){
    return mId;
  }

  public String getTitle() {
    return mTitle;
  }

  public int getCreatorId() {
    return mCreatorId;
  }

  public String getDateCreated() {
    return mDateCreated;
  }

  public String getStatus() {
    return mStatus;
  }

  public String getDescription() {
    return mDescription;
  }

  public int getTypeTaskId() {
    return mTypeTaskId;
  }

  public int getDeveloperId() {
    return mDeveloperId;
  }

  public Epic(String title, int creatorId, String dateCreated, String status, String description, int typeTaskId, int developerId) {
     this.mTitle = title;
     this.mCreatorId = creatorId;
     this.mDateCreated = dateCreated;
     this.mStatus = status;
     this.mDescription = description;
     this.mTypeTaskId = typeTaskId;
     this.mDeveloperId = developerId;
  }

  @Override
  public boolean equals(Object otherEpic) {
    if (!(otherEpic instanceof Epic)) {
      return false;
    } else {
      Epic newEpic = (Epic) otherEpic;
      return this.getTitle().equals(newEpic.getTitle()) &&
            this.getCreatorId() == (newEpic.getCreatorId()) &&
            this.getDateCreated().equals(newEpic.getDateCreated()) &&
            this.getStatus().equals(newEpic.getStatus()) &&
            this.getDescription().equals(newEpic.getDescription()) &&
            this.getTypeTaskId() == (newEpic.getTypeTaskId()) &&
            this.getDeveloperId() == (newEpic.getDeveloperId());
    }
  }

  public static List<Epic> all() {
    String sql = "SELECT id AS mId, title AS mTitle, creator_user_id AS mCreatorId, date_created AS mDateCreated, status AS mStatus, description AS mDescription, type_task_id AS mTypeTaskId, developer_id AS mDeveloperId FROM tasks";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Epic.class);
    }
  }

  public void save() {
    String sql = "INSERT INTO tasks(title, creator_user_id, date_created, status, description, type_task_id, developer_id) VALUES (:title, :creatorId, TO_DATE(:dateCreated, 'YYYY-MM-DD'), :status, :description, :typeTaskId, :developerId)";
    try(Connection con = DB.sql2o.open()) {
      this.mId = (int) con.createQuery(sql, true)
        .addParameter("title", this.mTitle)
        .addParameter("creatorId", this.mCreatorId)
        .addParameter("dateCreated", this.mDateCreated)
        .addParameter("status", this.mStatus)
        .addParameter("description", this.mDescription)
        .addParameter("typeTaskId", this.mTypeTaskId)
        .addParameter("developerId", this.mDeveloperId)
        .executeUpdate()
        .getKey();
    }
  }

  public static Epic find(int id) {
    String sql = "SELECT id AS mId, title AS mTitle, creator_user_id AS mCreatorId, date_created AS mDateCreated, status AS mStatus, description AS mDescription, type_task_id AS mTypeTaskId, developer_id AS mDeveloperId FROM tasks WHERE id = :id";
    try(Connection con = DB.sql2o.open()) {
      Epic epic = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Epic.class);
    return epic;
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
    String deleteEpic = "DELETE FROM tasks WHERE id = :id;";
    con.createQuery(deleteEpic)
      .addParameter("id", mId)
      .executeUpdate();
    }
  }

  public void update(String newTitle, int newCreatorId, String newDateCreated, String newStatus, String newDescription, int newTypeTaskId, int newDeveloperId) {
    mTitle = newTitle;
    mCreatorId = newCreatorId;
    mDateCreated = newDateCreated;
    mStatus = newStatus;
    mDescription = newDescription;
    mTypeTaskId = newTypeTaskId;
    mDeveloperId = newDeveloperId;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE tasks SET title = :title, creator_user_id = :creatorId, date_created = (TO_DATE(:dateCreated, 'YYYY-MM-DD')), status = :status, description = :description, type_task_id = :typeTaskId, developer_id = :developerId";
      con.createQuery(sql)
      .addParameter("title", newTitle)
      .addParameter("creatorId", newCreatorId)
      .addParameter("dateCreated", newDateCreated)
      .addParameter("status", newStatus)
      .addParameter("description", newDescription)
      .addParameter("typeTaskId", newTypeTaskId)
      .addParameter("developerId", newDeveloperId)
      .executeUpdate();
    }
  }

}
