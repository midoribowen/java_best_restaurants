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
  public void getType_returnsType() {
    Cuisine newCuisine = new Cuisine("Japanese");
    assertEquals("Japanese", newCuisine.getType());
  }

  @Test
  public void equals_returnsTrueIfTypesAreTheSame() {
    Cuisine cuisine1 = new Cuisine("Japanese");
    Cuisine cuisine2 = new Cuisine("Japanese");
    assertEquals(true, cuisine1.equals(cuisine2));
  }

  @Test
  public void save_savesCuisineToDatabase() {
    Cuisine myCuisine = new Cuisine("Japanese");
    myCuisine.save();
    assertTrue(Cuisine.all().get(0).equals(myCuisine));
  }

  @Test
  public void save_assignsIdToObject() {
    Cuisine myCuisine = new Cuisine("Japanese");
    myCuisine.save();
    assertEquals(Cuisine.all().get(0).getCuisineId(), myCuisine.getCuisineId());
  }

  @Test
  public void find_findsCuisineInDatabase_true() {
    Cuisine myCuisine = new Cuisine("Japanese");
    myCuisine.save();
    Cuisine savedCuisine = Cuisine.find(myCuisine.getCuisineId());
    assertTrue(myCuisine.equals(savedCuisine));
  }

  @Test
  public void update_updatesCuisineObjectInMemory_true() {
    Cuisine myCuisine = new Cuisine("Japanese");
    myCuisine.save();
    myCuisine.update("Japanese");
    assertEquals("Japanese", myCuisine.getType());
  }

  @Test
  public void update_updatesCuisineInDatabase_true() {
    Cuisine myCuisine = new Cuisine("Japanese");
    myCuisine.save();
    myCuisine.update("Japanese");
    assertEquals("Japanese", Cuisine.all().get(0).getType());
  }

  @Test
  public void delete_deletesCuisineInDatabase_true() {
    Cuisine myCuisine = new Cuisine("Japanese");
    myCuisine.save();
    myCuisine.delete();
    assertEquals(0, Cuisine.all().size());
  }

  @Test
  public void getRestaurants_getsRestaurantsByCuisine_true() {
    Cuisine myCuisine = new Cuisine("Japanese");
    myCuisine.save();
    Restaurant restaurant1 = new Restaurant("Sakura Sushi", myCuisine.getCuisineId(), 2);
    restaurant1.save();
    Restaurant restaurant2 = new Restaurant("Boxer Ramen", myCuisine.getCuisineId(), 2);
    restaurant2.save();
    Restaurant[] restaurants = new Restaurant[] {restaurant1, restaurant2};
    assertTrue(myCuisine.getRestaurantsByCuisine().containsAll(Arrays.asList(restaurants)));
  }
}
