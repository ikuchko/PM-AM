
import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class Message {
  private int mId;
  private String mDescription;
  private int mUserId;


  public int getId(){
    return mId;
  }

  public String getDescription() {
    return mDescription;
  }

  public int getUserId() {
    return mUserId;
  }



  public Message(String description, int userId) {
     this.mDescription = description;
     this.mUserId = userId;
  }

  @Override
  public boolean equals(Object otherMessage) {
    if (!(otherMessage instanceof Message)) {
      return false;
    } else {
      Message newMessage = (Message) otherMessage;
      return this.getDescription().equals(newMessage.getDescription()) &&
             this.getUserId() == (newMessage.getUserId());
    }
  }

  public static List<Message> all() {
    String sql = "SELECT id AS mId,  description AS mDescription, user_id AS mUserId FROM messages";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Message.class);
    }
  }

  public void save() {
    String sql = "INSERT INTO messages(description, user_id) VALUES (:title, :creatorId, TO_DATE(:dateCreated, 'YYYY-MM-DD'), :status, :description, :typeTaskId, :developerId)";
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
