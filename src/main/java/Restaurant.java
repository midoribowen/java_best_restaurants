import org.sql2o.*;
import java.util.List;
// import stuff

public class Restaurant {

  private int id;
  private String name;
  private int cuisine_id;
  private int price_id;

  public Restaurant (String name, int cuisine_id, int price_id) {
    this.name = name;
    this.cuisine_id = cuisine_id;
    this.price_id = price_id;
  }

  public String getName() {
    return name;
  }

  public int getCuisineId() {
    return cuisine_id;
  }

  public int getPriceId() {
    return price_id;
  }

  public int getId() {
    return id;
  }

  public static List<Restaurant> all() {
    String sql = "SELECT * FROM restaurants";
    try (Connection con = DB.sql2o.open()) {
        return con.createQuery(sql).executeAndFetch(Restaurant.class);
    }
  }

  public void save() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO restaurants (name, cuisine_id, price_id) VALUES (:name, :cuisine_id, :price_id)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", name)
        .addParameter("cuisine_id", cuisine_id)
        .addParameter("price_id", price_id)
        .executeUpdate()
        .getKey();
    }
  }

  @Override
  public boolean equals(Object otherRestaurant){
    if (!(otherRestaurant instanceof Restaurant)) {
      return false;
    } else {
      Restaurant newRestaurant = (Restaurant) otherRestaurant;
      return this.getName().equals(newRestaurant.getName()) &&
        this.getId() == newRestaurant.getId();
        // TODO potentially add more fields
    }
  }

  public static Restaurant find(int id) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM restaurants WHERE id=:id";
      Restaurant restaurant = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Restaurant.class);
      return restaurant;
    }
  }

  public void update(String newName) {
    this.name = newName;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE restaurants SET name=:name WHERE id=:id";
      con.createQuery(sql)
        .addParameter("name", newName)
        .addParameter("id", id)
        .executeUpdate();
      }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM restaurants WHERE id=:id";
      con.createQuery(sql)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public String getCuisineType() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM cuisine WHERE cuisine_id=:id";
      Cuisine cuisine = con.createQuery(sql)
        .addParameter("id", cuisine_id)
        .executeAndFetchFirst(Cuisine.class);
      return cuisine.getType();
    }
  }

  public String getPriceRange() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM prices WHERE price_id=:id";
      Price price = con.createQuery(sql)
        .addParameter("id", price_id)
        .executeAndFetchFirst(Price.class);
      return price.getPriceRange();
    }
  }
}
