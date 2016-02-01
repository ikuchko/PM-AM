
import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class Store {
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

  private String getTitle() {
    return mTitle;
  }


  private int getCreatorId() {
    return mCreatorId;
  }

  private String getDateCreated() {
    return mDateCreated;
  }

  private String getStatus() {
    return mStatus;
  }

  private String getDescription() {
    return mDescription;
  }

  private int getTypeTaskId() {
    return mTypeTaskId;
  }

  private int getDeveloperId() {
    return mDeveloperId;
  }

  public Epic(String title, int creatorID, String dateCreated, String status, String description, int typeTask, int developerId) {
    this.mName = name;
    this.mAddress = address;
    this.mPhoneNumber = phoneNumber;
  }

  @Override
  public boolean equals(Object otherStore) {
    if (!(otherStore instanceof Store)) {
      return false;
    } else {
      Store newStore = (Store) otherStore;
      return this.getName().equals(newStore.getName()) &&
        this.getAddress().equals(newStore.getAddress())  &&
        this.getPhoneNumber().equals(newStore.getPhoneNumber());
    }
  }

  public static List<Store> all() {
    String sql = "SELECT id AS mId, name AS mName, address AS mAddress, phone_number AS mPhoneNumber FROM stores";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Store.class);
    }
  }

  public void save() {
    String sql = "INSERT INTO stores(name, address, phone_number) VALUES (:name, :address, :phoneNumber)";
    try(Connection con = DB.sql2o.open()) {
      this.mId = (int) con.createQuery(sql, true)
        .addParameter("name", this.mName)
        .addParameter("address", this.mAddress)
        .addParameter("phoneNumber", this.mPhoneNumber)
        .executeUpdate()
        .getKey();
    }
  }

  public static Store find(int id) {
    String sql = "SELECT id AS mId, name AS mName, address AS mAddress, phone_number AS mPhoneNumber FROM stores WHERE id = :id";
    try(Connection con = DB.sql2o.open()) {
      Store store = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Store.class);
    return store;
    }
  }
  //
  // public void delete() {
  //   try(Connection con = DB.sql2o.open()) {
  //   String deleteStore = "DELETE FROM stores WHERE id = :id;";
  //   con.createQuery(deleteStore)
  //     .addParameter("id", mId)
  //     .executeUpdate();
  //   }
  // }
  //
  // public void update(String newName, String newAddress, String newPhoneNumber) {
  //   mName = newName;
  //   mAddress = newAddress;
  //   mPhoneNumber = newPhoneNumber;
  //   try(Connection con = DB.sql2o.open()) {
  //     String sql = "UPDATE stores SET name = :name, address = :address, phone_number = :phoneNumber WHERE id = :id";
  //     con.createQuery(sql)
  //       .addParameter("name", newName)
  //       .addParameter("address", newAddress)
  //       .addParameter("phoneNumber", newPhoneNumber)
  //       .addParameter("id", mId)
  //       .executeUpdate();
  //   }
  // }
  //
  // public void addBrand(int brandId) {
  // try(Connection con = DB.sql2o.open()) {
  //   String sql = "INSERT INTO stores_brands(store_id, brand_id)  VALUES (:storeId, :brandId)";
  //   con.createQuery(sql)
  //     .addParameter("storeId", this.getId())
  //     .addParameter("brandId", brandId)
  //     .executeUpdate();
  //   }
  // }
  //
  // public List<Brand> getAllBrands(){
  //   try(Connection con = DB.sql2o.open()) {
  //     String sql = "SELECT brands.id AS mId, brands.name AS mName, brands.specialty AS mSpecialty FROM brands INNER JOIN stores_brands ON brands.id = stores_brands.brand_id WHERE stores_brands.store_id = :id";
  //     List<Brand> brandList = con.createQuery(sql)
  //       .addParameter("id", mId)
  //       .executeAndFetch(Brand.class);
  //     return brandList;
  //   }
  // }
  //
  // public List<Brand> getAllSpecialties(){
  //   try(Connection con = DB.sql2o.open()) {
  //     String sql = "SELECT brands.id AS mId, brands.specialty AS mSpecialty FROM brands INNER JOIN stores_brands ON brands.id = stores_brands.brand_id WHERE stores_brands.store_id = :id";
  //     List<Brand> brandList = con.createQuery(sql)
  //       .addParameter("id", mId)
  //       .executeAndFetch(Brand.class);
  //     return brandList;
  //   }
  // }
  //
  // public static List<Store> storeSearch(String userInput){
  //   try(Connection con = DB.sql2o.open()) {
  //     String sql = "SELECT id AS mId, name AS mName FROM stores WHERE name LIKE :userInput";
  //     List<Store> storeList = con.createQuery(sql)
  //       .addParameter("userInput", "%" + userInput + "%")
  //       .executeAndFetch(Store.class);
  //     return storeList;
  //   }
  // }
}
