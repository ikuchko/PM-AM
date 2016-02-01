import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class DatabaseRule extends ExternalResource {

  protected void before() {
    //DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/shoe_stores_test", null, null);
   }

  protected void after() {
    // try(Connection con = DB.sql2o.open()) {
    //    String deleteStoresBrandsQuery = "DELETE FROM stores_brands *;";
    //    String deleteStoresQuery = "DELETE FROM stores *;";
    //    String deleteBrandsQuery = "DELETE FROM brands *;";
    //    con.createQuery(deleteStoresBrandsQuery).executeUpdate();
    //    con.createQuery(deleteStoresQuery).executeUpdate();
    //    con.createQuery(deleteBrandsQuery).executeUpdate();
    // }
  }
}
