import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.Arrays;

public class CuisineTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
      assertEquals(Cuisine.all().size(), 0);
  }

  @Test
  public void save_savesCuisineInDatabase() {
    Cuisine cuisine = new Cuisine("Hawaiian");
    cuisine.save();
    assertTrue(Cuisine.all().get(0).equals(cuisine));
  }

  @Test
  public void update_changesNameOfCuisine() {
    Cuisine myCuisine = new Cuisine("Hawaiian");
    myCuisine.save();
    myCuisine.update("Dessert");
    assertEquals("Dessert", Cuisine.find(myCuisine.getId()).getType());
  }

  @Test
  public void delete_deletesCuisineFromDatabase() {
    Cuisine myCuisine = new Cuisine("Mexican");
    Cuisine myOtherCuisine = new Cuisine("Hamburgers");
    myCuisine.save();
    myOtherCuisine.save();
    myCuisine.delete();
    assertEquals(1, Cuisine.all().size());
  }

  @Test
  public void getAllRestaurants_returnsAllRestaurantsWithCuisine() {
    Cuisine myCuisine = new Cuisine("Mexican");
    myCuisine.save();
    Restaurant firstRestaurant = new Restaurant("La Sirenita");
    Restaurant secondRestaurant = new Restaurant("El Sol");
    firstRestaurant.save();
    secondRestaurant.save();
    firstRestaurant.assignCuisine(myCuisine.getId());
    secondRestaurant.assignCuisine(myCuisine.getId());
    Restaurant[] restaurants = new Restaurant[] {firstRestaurant, secondRestaurant};
    assertTrue(myCuisine.getAllRestaurants()
      .containsAll(Arrays.asList(restaurants)));
  }

}
