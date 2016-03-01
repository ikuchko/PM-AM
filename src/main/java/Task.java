import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class Task {
  private int mId;
  private String mTitle;
  private int mStatus;
  private String mDescription;
  private String mDateCreated;
  private int mTypeId;
  private int mImplementorId;
  private int mCreatorId;
  private int mMainListId;
  private boolean mOnBoard;

  // private static final int EPIC = 1;
  // private static final int TASK = 1;
  // private static final int STORY = 1;
  // private static final int BUG = 1;

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

  public String getDateCreated() {
    return mDateCreated;
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

  public boolean getOnBoard() {
    return mOnBoard;
  }

  public Task(String title, int creatorId, String description, int type, int implementorId) {
     this.mTitle = title;
     this.mDescription = description;
     this.mStatus = 1;
     this.mTypeId = type;
     this.mImplementorId = implementorId;
     this.mCreatorId = creatorId;
     save();
  }

  @Override
  public boolean equals(Object otherStory) {
    if (!(otherStory instanceof Task)) {
      return false;
    } else {
      Task newStory = (Task) otherStory;
      return this.getTitle().equals(newStory.getTitle()) &&
             this.getStatus() == newStory.getStatus() &&
             this.getDescription().equals(newStory.getDescription()) &&
             this.getTypeTask() == (newStory.getTypeTask()) &&
             this.getImplementorId() == newStory.getImplementorId() &&
             this.getCreatorId() == newStory.getCreatorId();
    }
  }

  public void save() {
    boolean onBoard = false;
    if (mTypeId == 4) {
      onBoard = true;
    }

    String sql = "INSERT INTO tasks (title, creator_user_id, status_id, description, type_task_id, developer_id, on_board) VALUES (:title, :creatorUser, :statusId, :description, :typeId, :implementorUser, :onBoard)";
    try(Connection con = DB.getSql2o().open()) {
      this.mId = (int) con.createQuery(sql, true)
        .addParameter("title", this.mTitle)
        .addParameter("description", this.mDescription)
        .addParameter("statusId", 1)
        .addParameter("typeId", this.mTypeId)
        .addParameter("implementorUser", this.mImplementorId)
        .addParameter("creatorUser", this.mCreatorId)
        .addParameter("onBoard", onBoard)
        .executeUpdate()
        .getKey();
    }
  }

  public static Task find(int id) {
    String sql = "SELECT id AS mId, title AS mTitle, creator_user_id AS mCreatorId, status_id AS mStatus, description AS mDescription, type_task_id AS mTypeId, developer_id AS mImplementorId, date_created AS mDateCreated, on_board AS mOnBoard FROM tasks WHERE id = :id";
    try(Connection con = DB.getSql2o().open()) {
      Task task = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Task.class);
    return task;
    }
  }

  public static List<Task> all(int task_type) {
    String sql = "SELECT id AS mId, title AS mTitle, creator_user_id AS mCreatorId, status_id AS mStatus, description AS mDescription, type_task_id AS mTypeId, developer_id AS mImplementorId, date_created AS mDateCreated, on_board AS mOnBoard FROM tasks WHERE type_task_id = :type_id";
    try(Connection con = DB.getSql2o().open()) {
      return con.createQuery(sql)
        .addParameter("type_id", task_type)
        .executeAndFetch(Task.class);
    }
  }

  public List<Task> allAssigned() {
    String sql = "SELECT ta.id AS mId, ta.title AS mTitle, ta.creator_user_id AS mCreatorId, ta.status_id AS mStatus, ta.description AS mDescription, ta.type_task_id AS mTypeId, ta.developer_id AS mImplementorId, ta.date_created AS mDateCreated, ta.on_board AS mOnBoard FROM tasks_relationships AS t_r INNER JOIN tasks ON tasks.id = t_r.main_task_id INNER JOIN tasks AS ta ON ta.id = t_r.subtask_id WHERE tasks.id = :id";
    try(Connection con = DB.getSql2o().open()) {
      return con.createQuery(sql)
        .addParameter("id", this.mId)
        .executeAndFetch(Task.class);
    }
  }

  public static List<Task> allByCreator(int task_type, int creatorId) {
    String sql = "SELECT id AS mId, title AS mTitle, creator_user_id AS mCreatorId, status_id AS mStatus, description AS mDescription, type_task_id AS mTypeId, developer_id AS mImplementorId, date_created AS mDateCreated, on_board AS mOnBoard FROM tasks WHERE type_task_id = :type_id AND creator_user_id = :creatorId";
    try(Connection con = DB.getSql2o().open()) {
      return con.createQuery(sql)
        .addParameter("type_id", task_type)
        .addParameter("creatorId", creatorId)
        .executeAndFetch(Task.class);
    }
  }

  public List<Task> allAssigned(int task_type) {
    String sql = "SELECT ta.id AS mId, ta.title AS mTitle, ta.creator_user_id AS mCreatorId, ta.status_id AS mStatus, ta.description AS mDescription, ta.type_task_id AS mTypeId, ta.developer_id AS mImplementorId, ta.date_created AS mDateCreated, ta.on_board AS mOnBoard FROM tasks_relationships AS t_r INNER JOIN tasks ON tasks.id = t_r.main_task_id INNER JOIN tasks AS ta ON ta.id = t_r.subtask_id WHERE tasks.id = :id AND ta.type_task_id = :task_type";
    try(Connection con = DB.getSql2o().open()) {
      return con.createQuery(sql)
        .addParameter("task_type", task_type)
        .addParameter("id", this.mId)
        .executeAndFetch(Task.class);
    }
  }
  public static List<Task> allByImplementor(int task_type, int implementorId) {
    String sql = "SELECT id AS mId, title AS mTitle, creator_user_id AS mCreatorId, status_id AS mStatus, description AS mDescription, type_task_id AS mTypeId, developer_id AS mImplementorId, date_created AS mDateCreated, on_board AS mOnBoard FROM  tasks WHERE type_task_id = :type_id AND developer_id = :implementorId";
    try(Connection con = DB.getSql2o().open()) {
      return con.createQuery(sql)
        .addParameter("type_id", task_type)
        .addParameter("implementorId", implementorId)
        .executeAndFetch(Task.class);
    }
  }

  public List<Task> allAssigned(int task_type, int status) {
    String sql = "SELECT ta.id AS mId, ta.title AS mTitle, ta.creator_user_id AS mCreatorId, ta.status_id AS mStatus, ta.description AS mDescription, ta.type_task_id AS mTypeId, ta.developer_id AS mImplementorId, ta.date_created AS mDateCreated, ta.on_board AS mOnBoard FROM tasks_relationships AS t_r INNER JOIN tasks ON tasks.id = t_r.main_task_id INNER JOIN tasks AS ta ON ta.id = t_r.subtask_id WHERE tasks.id = :id AND ta.type_task_id = :task_type AND ta.status_id = :status";
    try(Connection con = DB.getSql2o().open()) {
      return con.createQuery(sql)
        .addParameter("task_type", task_type)
        .addParameter("id", this.mId)
        .addParameter("status", status)
        .executeAndFetch(Task.class);
    }
  }

  public static List<Task> allByStatus(int task_type, int statusId) {
    String sql = "SELECT id AS mId, title AS mTitle, creator_user_id AS mCreatorId, status_id AS mStatus, description AS mDescription, type_task_id AS mTypeId, developer_id AS mImplementorId, date_created AS mDateCreated on_board AS mOnBoard FROM tasks WHERE type_task_id = :type_id AND status_id = :statusId";
    try(Connection con = DB.getSql2o().open()) {
      return con.createQuery(sql)
        .addParameter("type_id", task_type)
        .addParameter("statusId", statusId)
        .executeAndFetch(Task.class);
    }
  }

  public static List<Task> allRelatedTasksByUser(int mainId, int userId) {
    String sql = "SELECT id AS mId, title AS mTitle, creator_user_id AS mCreatorId, status_id AS mStatus, description AS mDescription, type_task_id AS mTypeId, developer_id AS mImplementorId, date_created AS mDateCreated, on_board AS mOnBoard FROM tasks WHERE developer_id = :userId INNER JOIN tasks_relationships WHERE tasks_relationships.main_task_id = :mainId";
    try(Connection con = DB.getSql2o().open()) {
      return con.createQuery(sql)
        .addParameter("mainId", mainId)
        .addParameter("userId", userId)
        .executeAndFetch(Task.class);
    }
  }


  public void update(String title, String description) {
    String sql = "UPDATE tasks SET title = :title, description = :description WHERE id = :id";
    try(Connection con = DB.getSql2o().open()) {
      con.createQuery(sql)
        .addParameter("title", title)
        .addParameter("description", description)
        .addParameter("id", this.mId)
        .executeUpdate();
      History newHistory = new History(this.getId(), "Update task", mTitle + " | " + mDescription, title + " | " + description);
      this.mTitle = title;
      this.mDescription = description;
    }
  }

// public History(int taskId, String changeType, String previousCondition, String currentCondition)
// Use changeStatus() for changing task status
  public void updateStatus(int statusId) {
    String sql = "UPDATE tasks SET status_id = :statusId WHERE id = :id";
    try(Connection con = DB.getSql2o().open()) {
      con.createQuery(sql)
        .addParameter("statusId", statusId)
        .addParameter("id", this.mId)
        .executeUpdate();
        History newHistory = new History(this.getId(), "Update task", Status.getStatusName(mStatus), Status.getStatusName(statusId));
        this.mStatus = statusId;
    }
  }

  public void updateImplementor(int implementorId) {
    String sql = "UPDATE tasks SET developer_id = :implementorId WHERE id = :id";
    try(Connection con = DB.getSql2o().open()) {
      con.createQuery(sql)
        .addParameter("implementorId", implementorId)
        .addParameter("id", this.mId)
        .executeUpdate();
        this.mImplementorId = implementorId;
    }
    History newHistory = new History(this.getId(), "Update task", User.find(this.getImplementorId()).getName(), User.find(implementorId).getName());
  }

  public void delete() {
    String sql = "DELETE FROM tasks WHERE id = :id";
    try(Connection con = DB.getSql2o().open()) {
      con.createQuery(sql)
        .addParameter("id", this.mId)
        .executeUpdate();
    }
  }

  public void assign(Task task) {
    String sql = "INSERT INTO tasks_relationships (main_task_id, subtask_id) VALUES (:mainId, :subTaskId)";
    try(Connection con = DB.getSql2o().open()) {
      con.createQuery(sql)
        .addParameter("mainId", this.mId)
        .addParameter("subTaskId", task.getId())
        .executeUpdate();
    }
  }

  public List<Message> getMessages() {
    String sql = "SELECT messages.id AS mId, messages.description AS mMessage, messages.user_id AS mUserId, messages.task_id AS mTaskId, messages.date_created AS mDateCreated FROM tasks " +
                 "INNER JOIN tasks_messages AS t_m ON tasks.id = t_m.task_id " +
                 "INNER JOIN messages ON messages.id = t_m.message_id WHERE tasks.id = :id ORDER BY messages.date_created DESC";
    try(Connection con = DB.getSql2o().open()) {
      return con.createQuery(sql)
        .addParameter("id", this.mId)
        .executeAndFetch(Message.class);
    }
  }

  public boolean changeStatus() {
    boolean isChanged = false;
    if (((TypeTask.getTypeTaskName(getTypeTask()).equals("Task")) &&
       (allAssigned(TypeTask.getId("Bug"), Status.getId("To Do")).size() == 0) &&
       (allAssigned(TypeTask.getId("Bug"), Status.getId("In Progress")).size() == 0) &&
       (allAssigned(TypeTask.getId("Bug"), Status.getId("Test")).size() == 0)) ||
       (!(TypeTask.getTypeTaskName(getTypeTask()).equals("Task")))) {
      //updateStatus(Status.getNextStatus(this));
      isChanged = true;
    }
    return isChanged;
  }

  public List<Task> getAllSubTasks() {
    String sql = "SELECT tasks.id AS mId, tasks.title AS mTitle, tasks.creator_user_id AS mCreatorId, tasks.status_id AS mStatus, tasks.description AS mDescription, tasks.type_task_id AS mTypeId, tasks.developer_id AS mImplementorId, tasks.date_created AS mDateCreated, tasks.on_board AS mOnBoard FROM tasks INNER JOIN tasks_relationships AS t_r ON main_task_id = :mainId WHERE tasks.id = t_r.subtask_id";
    try(Connection con = DB.getSql2o().open()) {
      return con.createQuery(sql)
        .addParameter("mainId", this.mId)
        .executeAndFetch(Task.class);
    }
  }

  public List<Task> getAllSubTasks(int status) {
    String sql = "SELECT tasks.id AS mId, tasks.title AS mTitle, tasks.creator_user_id AS mCreatorId, tasks.status_id AS mStatus, tasks.description AS mDescription, tasks.type_task_id AS mTypeId, tasks.developer_id AS mImplementorId, tasks.date_created AS mDateCreated, tasks.on_board AS mOnBoard FROM tasks INNER JOIN tasks_relationships AS t_r ON main_task_id = :mainId WHERE tasks.id = t_r.subtask_id AND tasks.status_id = :status";
    try(Connection con = DB.getSql2o().open()) {
      return con.createQuery(sql)
        .addParameter("mainId", this.mId)
        .addParameter("status", status)
        .executeAndFetch(Task.class);
    }
  }

  public static List<Task> getAllOnBoard(boolean onBoard) {
    String sql = "SELECT tasks.id AS mId, tasks.title AS mTitle, tasks.creator_user_id AS mCreatorId, tasks.status_id AS mStatus, tasks.description AS mDescription, tasks.type_task_id AS mTypeId, tasks.developer_id AS mImplementorId, tasks.date_created AS mDateCreated, tasks.on_board AS mOnBoard FROM tasks WHERE on_board = :onBoard";
    try(Connection con = DB.getSql2o().open()) {
      return con.createQuery(sql)
        .addParameter("onBoard", onBoard)
        .executeAndFetch(Task.class);
    }
  }



  public void assignTask(int userId){
    String sql = "UPDATE tasks SET developer_id = :userId WHERE id = :id";
    try(Connection con = DB.getSql2o().open()) {
      con.createQuery(sql)
        .addParameter("id", this.mId)
        .addParameter("userId", userId)
        .executeUpdate();
    }
  }

  public void unassignTask(int userId){
    String sql = "UPDATE tasks SET developer_id = null WHERE id = :id";
    try(Connection con = DB.getSql2o().open()) {
      con.createQuery(sql)
        .addParameter("id", this.mId)
        .executeUpdate();
    }
  }

  public void changeOnBoard(boolean onBoard) {
    String sql = "UPDATE tasks SET on_board = :onBoard WHERE id = :id";
    try(Connection con = DB.getSql2o().open()) {
      con.createQuery(sql)
        .addParameter("id", this.mId)
        .addParameter("onBoard", onBoard)
        .executeUpdate();
    }
  }

  public Integer getTotalDevelopers() {
    String sql = "SELECT COUNT (ta.developer_id) FROM tasks_relationships AS t_r INNER JOIN tasks ON tasks.id = t_r.main_task_id INNER JOIN tasks AS ta ON ta.id = t_r.subtask_id WHERE tasks.id = :id GROUP BY ta.developer_id";
    try(Connection con = DB.getSql2o().open()) {
      return con.createQuery(sql)
        .addParameter("id", this.mId)
        .executeScalar(Integer.class);
    }
  }

  public List<User> getDeveloperList() {
    String sql = "SELECT (ta.developer_id) FROM tasks_relationships AS t_r INNER JOIN tasks ON tasks.id = t_r.main_task_id INNER JOIN tasks AS ta ON ta.id = t_r.subtask_id WHERE tasks.id = :id";
    try(Connection con = DB.getSql2o().open()) {
      return con.createQuery(sql)
        .addParameter("id", this.mId)
        .executeAndFetch(User.class);
    }
  }



}
