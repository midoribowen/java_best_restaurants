import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;

public class RestaurantTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Restaurant.all().size(), 0);
  }

  @Test
  public void save_savesRestaurantInDatabase() {
    Restaurant restaurant = new Restaurant("Local Boyz");
    restaurant.save();
    assertTrue(Restaurant.all().get(0).equals(restaurant));
  }

  @Test
  public void update_changesNameOfRestaurant() {
    Restaurant myRestaurant = new Restaurant("Local Boyz");
    myRestaurant.save();
    myRestaurant.update("Local Pies");
    assertEquals("Local Pies", Restaurant.find(myRestaurant.getId()).getName());
  }

  @Test
  public void delete_deletesRestaurantFromDatabase() {
    Restaurant myRestaurant = new Restaurant("Local Boyz");
    Restaurant myOtherRestaurant = new Restaurant("Shut Up And Eat");
    myRestaurant.save();
    myOtherRestaurant.save();
    myRestaurant.delete();
    assertEquals(1, Restaurant.all().size());
  }

  @Test
  public void assignCuisineWithId_assignsCuisineIdToRestaurant() {
    Restaurant myRestaurant = new Restaurant("Local Boyz");
    Cuisine myCuisine = new Cuisine("Hawaiian");
    myRestaurant.save();
    myCuisine.save();
    myRestaurant.assignCuisine(myCuisine.getId());
    assertTrue(Cuisine.find(myCuisine.getId())
      .equals(Cuisine.find(myRestaurant.getCuisineId())));
  }

  @Test
  public void search_returnsSimilarRestaurantsByName() {
    Restaurant myRestaurant = new Restaurant("Local Boyz");
    myRestaurant.save();
    assertTrue(Restaurant.search("Local").contains(myRestaurant));
  }
}
