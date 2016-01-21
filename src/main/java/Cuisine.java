import org.sql2o.*;
import java.util.List;

public class Cuisine {
  private int mId;
  private String mType;

  public Cuisine (String type) {
    this.mType = type;
  }

  public int getId() {
    return mId;
  }

  public String getType() {
    return mType;
  }

  @Override
  public boolean equals(Object otherCuisine){
    if (!(otherCuisine instanceof Cuisine)) {
      return false;
    } else {
      Cuisine newCuisine = (Cuisine) otherCuisine;
      return this.getType().equals(newCuisine.getType()) &&
        this.getId() == newCuisine.getId();
    }
  }

  public void save() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO cuisines(type) VALUES (:type)";
      this.mId = (int) con.createQuery(sql, true)
        .addParameter("type", this.mType)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<Cuisine> all() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT id AS mId, type AS mType FROM cuisines";
      return con.createQuery(sql)
        .executeAndFetch(Cuisine.class);
    }
  }

  public void update(String newType) {
    this.mType = newType;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE cuisines SET type = :newType WHERE id=:id";
      con.createQuery(sql)
         .addParameter("newType", newType)
         .addParameter("id", this.mId)
         .executeUpdate();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM cuisines WHERE id = :id";
      con.createQuery(sql)
         .addParameter("id", this.mId)
         .executeUpdate();
    }
  }

  public static Cuisine find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT id AS mId, type as mType FROM cuisines WHERE id=:id";
      return con.createQuery(sql)
                .addParameter("id", id)
                .executeAndFetchFirst(Cuisine.class);
    }
  }

  public List<Restaurant> getAllRestaurants() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT id AS mId, name as mName FROM restaurants WHERE cuisine_id=:id";
      return con.createQuery(sql)
                .addParameter("id", this.mId)
                .executeAndFetch(Restaurant.class);
    }
  }

}
