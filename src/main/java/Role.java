public class Role{
  private String name;
  private int id;

  public Role(String name) {
    String sql = "SELECT * FROM roles WHERE name LIKE ':name'";
    try (Connection con = DB.sql2o.open()) {
      this.id = con.createQuery(sql)
        .addParameter("name", name);
        .executeScalar(Integer.class)
    }
    if (this.id) {
      this.name = name;
    }
  }
}
