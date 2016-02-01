
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
    String sql = "INSERT INTO messages(description, user_id) VALUES (:description, :userId)";
    try(Connection con = DB.sql2o.open()) {
      this.mId = (int) con.createQuery(sql, true)
        .addParameter("description", this.mDescription)
        .addParameter("userId", this.mUserId)
        .executeUpdate()
        .getKey();
    }
  }

  public static Message find(int id) {
    String sql = "SELECT id AS mId,  description AS mDescription, user_id AS mUserId FROM messages WHERE id = :id";
    try(Connection con = DB.sql2o.open()) {
      Message message = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Message.class);
    return message;
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
    String deleteMessage = "DELETE FROM messages WHERE id = :id;";
    con.createQuery(deleteMessage)
      .addParameter("id", mId)
      .executeUpdate();
    }
  }

  public void update(String newDescription, int newUserId) {
    mDescription = newDescription;
    mUserId = newUserId;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE messages SET description = :description, user_id = :userId";
      con.createQuery(sql)
      .addParameter("description", newDescription)
      .addParameter("userId", newUserId)
      .executeUpdate();
    }
  }
}
