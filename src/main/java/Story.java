import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class Story {
  private int mId;
  private String mTitle;
  private String mStatus;
  private String mDescription;
  private int mTypeId;
  private User mImplementor;
  private User mCreator;

  public int getId(){
    return mId;
  }

  public String getTitle() {
    return mTitle;
  }

  public String getStatus() {
    return mStatus;
  }

  public String getDescription() {
    return mDescription;
  }

  public int getTypeTask() {
    return mTypeId;
  }

  public User getImplementor() {
    return mImplementor;
  }

  public User getCreator() {
    return mCreator;
  }

  public Story(String title, int creatorId, String status, String description, int type, int implementorId) {
     this.mTitle = title;
     this.mStatus = status;
     this.mDescription = description;
     this.mTypeId = type;
     this.mImplementor = User.find(implementorId);
     this.mCreator = User.find(creatorId);
  }

  @Override
  public boolean equals(Object otherStory) {
    if (!(otherStory instanceof Story)) {
      return false;
    } else {
      Story newStory = (Story) otherStory;
      return this.getTitle().equals(newStory.getTitle()) &&

             this.getStatus().equals(newStory.getStatus()) &&
             this.getDescription().equals(newStory.getDescription()) &&
             this.getTypeTask() == (newStory.getTypeTask()) &&
             this.getImplementor().equals(newStory.getImplementor());
            //  this.getImplementor().getName().equals(newStory.getImplementor().getName()) ;
    }
  }

  public void save() {
    String sql = "INSERT INTO tasks (title, creator_user_id, status, description, type_task_id, developer_id) VALUES (:title, :creatorUser, :status, :description, :typeId, :implementorUser)";
    try(Connection con = DB.sql2o.open()) {
      this.mId = (int) con.createQuery(sql, true)
        .addParameter("title", this.mTitle)
        .addParameter("status", this.mStatus)
        .addParameter("description", this.mDescription)
        .addParameter("typeId", this.mTypeId)
        .addParameter("implementorUser", this.mImplementor.getId())
        .addParameter("creatorUser", this.mCreator.getId())
        .executeUpdate()
        .getKey();
    }
  }

  public static Story find(int id) {
    String sql = "SELECT id AS mId, title AS mTitle, creator_user_id AS mCreator, status AS mStatus, description AS mDescription, type_task_id AS mTypeId, developer_id AS mImplementor FROM tasks WHERE id = :id";
    try(Connection con = DB.sql2o.open()) {
      Story story = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Story.class);
    return story;
    }
  }

}
