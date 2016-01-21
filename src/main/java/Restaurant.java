import org.sql2o.*;
import java.util.List;

public class Restaurant {
  private int mId;
  private String mName;
  private int mCuisineId;

  public Restaurant (String name) {
    this.mName = name;
  }

  public int getId() {
    return mId;
  }

  public String getName() {
    return mName;
  }

  @Override
  public boolean equals(Object otherRestaurant){
    if (!(otherRestaurant instanceof Restaurant)) {
      return false;
    } else {
      Restaurant newRestaurant = (Restaurant) otherRestaurant;
      return this.getName().equals(newRestaurant.getName()) &&
        this.getId() == newRestaurant.getId();
    }
  }

  public void save() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO Restaurants(name) VALUES (:name)";
      this.mId = (int) con.createQuery(sql, true)
        .addParameter("name", this.mName)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<Restaurant> all() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT id AS mId, name AS mName FROM restaurants";
      return con.createQuery(sql).executeAndFetch(Restaurant.class);
    }
  }

  public void update(String newName) {
    this.mName = newName;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE restaurants SET name = :newName WHERE id=:id";
      con.createQuery(sql)
         .addParameter("newName", newName)
         .addParameter("id", this.mId)
         .executeUpdate();
      }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM restaurants WHERE id = :id";
      con.createQuery(sql)
        .addParameter("id", this.mId)
        .executeUpdate();
    }
  }

  public static Restaurant find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT id AS mId, name AS mName FROM restaurants WHERE id = :id";
      return con.createQuery(sql)
                .addParameter("id", id)
                .executeAndFetchFirst(Restaurant.class);
    }
  }

  public void assignCuisine(int cuisineId) {
    mCuisineId = cuisineId;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE restaurants SET cuisine_id = :cuisineId WHERE id=:id";
      con.createQuery(sql)
         .addParameter("cuisineId", cuisineId)
         .addParameter("id", this.mId)
         .executeUpdate();
    }
  }

  public int getCuisineId() {
    return mCuisineId;
  }
}
