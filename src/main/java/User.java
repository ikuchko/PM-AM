import org.sql2o.*;
import java.util.List;

public class User{
  private String role;
  private String name;
  private String email;
  private int roleId;
  private int id;

  public String getRole() {
    return role;
  }
  public String getName() {
    return name;
  }
  public String getEmail() {
    return email;
  }
  public int getRoleId() {
    return roleId;
  }
  public int getId() {
    return id;
  }

  public User(String role, String name, String email) {
    this.role = role;
    this.name = name;
    this.email = email;
    this.roleId = Role.getId(role);
    save();
  }

  @Override
  public boolean equals(Object otherUser) {
    if (!(otherUser instanceof User)) {
      return false;
    } else {
      User newUser = (User) otherUser;
      return (newUser.getName().equals(this.getName())) &&
             (newUser.getRole().equals(this.getRole())) &&
             (newUser.getEmail().equals(this.getEmail())) &&
             (newUser.getRoleId() == this.getRoleId()) &&
             (newUser.getId() == this.getId());
    }
  }

  public void save() {
    String sql = "INSERT INTO users (name, email, role_id) VALUES (:name, :email, :roleId)";
    try(Connection con = DB.getSql2o().open()) {
        this.id = (int) con.createQuery(sql, true)
          .addParameter("name", this.name)
          .addParameter("email", this.email)
          .addParameter("roleId", this.roleId)
          .executeUpdate()
          .getKey();
    }
  }

  public static User find(int id) {
    String sql = "SELECT users.id, users.name, users.email, users.role_id AS roleId, roles.name AS role FROM users " +
                 "INNER JOIN roles ON roles.id = users.role_id WHERE users.id = :id";
    try(Connection con = DB.getSql2o().open()) {
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(User.class);
    }
  }

  public static List<User> all() {
    String sql = "SELECT users.id, users.name, users.email, users.role_id AS roleId, roles.name AS role FROM users " +
                 "INNER JOIN roles ON roles.id = users.role_id";
    try(Connection con = DB.getSql2o().open()) {
      return con.createQuery(sql)
        .executeAndFetch(User.class);
    }
  }

  public static List<User> all(int roleId) {
    String sql = "SELECT users.id, users.name, users.email, users.role_id AS roleId, roles.name AS role FROM users " +
                 "INNER JOIN roles ON roles.id = users.role_id WHERE users.role_id = :roleId";
    try(Connection con = DB.getSql2o().open()) {
      return con.createQuery(sql)
        .addParameter("roleId", roleId)
        .executeAndFetch(User.class);
    }
  }

  public void update(String role, String name, String email) {
    int roleId = Role.getId(role);
    String sql = "UPDATE users SET role_id = :role, name = :name, email = :email WHERE id = :id";
    try(Connection con = DB.getSql2o().open()) {
      con.createQuery(sql)
        .addParameter("name", name)
        .addParameter("email", email)
        .addParameter("role", roleId)
        .addParameter("id", this.id)
        .executeUpdate();
        this.role = role;
        this.name = name;
        this.email = email;
        this.roleId = roleId;
    }
  }

  public void delete() {
    String sql = "DELETE FROM users WHERE id = :id";
    try(Connection con = DB.getSql2o().open()) {
      con.createQuery(sql)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

}
