import org.sql2o.*;
import java.util.List;


public class Cuisine {

  private int cuisine_id;
  private String type;

  public Cuisine (String type) {
    this.type = type;
  }

  public int getCuisineId() {
    return cuisine_id;
  }

  public String getType() {
    return type;
  }

  public static List<Cuisine> all() {
    String sql = "SELECT * FROM cuisine";
    try (Connection con = DB.sql2o.open()) {
        return con.createQuery(sql).executeAndFetch(Cuisine.class);
    }
  }

  public void save() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO cuisine (cuisine_id, type) VALUES (:cuisine_id, :type)";
      this.cuisine_id = (int) con.createQuery(sql, true)
        .addParameter("cuisine_id", cuisine_id)
        .addParameter("type", type)
        .executeUpdate()
        .getKey();
    }
  }

  @Override
  public boolean equals(Object otherCuisine){
    if (!(otherCuisine instanceof Cuisine)) {
      return false;
    } else {
      Cuisine newCuisine = (Cuisine) otherCuisine;
      return this.getType().equals(newCuisine.getType()) &&
        this.getCuisineId() == newCuisine.getCuisineId();
    }
  }

  public static Cuisine find(int id) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM cuisine WHERE cuisine_id=:id";
      Cuisine cuisine = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Cuisine.class);
      return cuisine;
    }
  }

  public void update(String newType) {
    this.type = newType;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE cuisine SET type=:type WHERE cuisine_id=:id";
      con.createQuery(sql)
        .addParameter("type", newType)
        .addParameter("id", cuisine_id)
        .executeUpdate();
      }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM cuisine WHERE cuisine_id=:id";
      con.createQuery(sql)
        .addParameter("id", cuisine_id)
        .executeUpdate();
    }
  }

  public List<Restaurant> getRestaurants() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM cuisine WHERE cuisine_id=:id";
      return con.createQuery(sql)
        .addParameter("id", this.cuisine_id)
        .executeAndFetch(Restaurant.class);
    }
  }
}
