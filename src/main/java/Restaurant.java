import org.sql2o.*;
import java.util.List;
// import stuff

public class Restaurant {

  private int id;
  private String name;
  private int cuisine_id;
  private String price;

  public Restaurant (String name, int cuisine_id, String price) {
    this.name = name;
    this.cuisine_id = cuisine_id;
    this.price = price;
  }

  public String getName() {
    return name;
  }

  public int getCuisineId() {
    return cuisine_id;
  }

  public String getPrice() {
    return price;
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
      String sql = "INSERT INTO restaurants (name, cuisine_id, price) VALUES (:name, :cuisine_id, :price)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", name)
        .addParameter("cuisine_id", cuisine_id)
        .addParameter("price", price)
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

  public static void deleteRestaurantById(int restaurantId) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM restaurants WHERE id=:id";
      con.createQuery(sql)
        .addParameter("id", restaurantId)
        .executeUpdate();
    }
  }
}
