import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.Arrays;

public class PriceTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
      assertEquals(Price.all().size(), 0);
  }

  @Test
  public void getPriceRange_returnsPriceRange() {
    Price newPrice = new Price("$");
    assertEquals("$", newPrice.getPriceRange());
  }

  @Test
  public void equals_returnsTrueIfPriceRangesAreTheSame() {
    Price price1 = new Price("$");
    Price price2 = new Price("$");
    assertEquals(true, price1.equals(price2));
  }

  @Test
  public void save_savesPriceToDatabase() {
    Price myPrice = new Price("$");
    myPrice.save();
    assertTrue(Price.all().get(0).equals(myPrice));
  }

  @Test
  public void save_assignsIdToObject() {
    Price myPrice = new Price("$");
    myPrice.save();
    assertEquals(Price.all().get(0).getPriceId(), myPrice.getPriceId());
  }

  @Test
  public void find_findsPriceInDatabase_true() {
    Price myPrice = new Price("$");
    myPrice.save();
    Price savedPrice = Price.find(myPrice.getPriceId());
    assertTrue(myPrice.equals(savedPrice));
  }

  @Test
  public void update_updatesPriceObjectInMemory_true() {
    Price myPrice = new Price("$");
    myPrice.save();
    myPrice.update("$$");
    assertEquals("$$", myPrice.getPriceRange());
  }

  @Test
  public void update_updatesPriceInDatabase_true() {
    Price myPrice = new Price("$");
    myPrice.save();
    myPrice.update("$$");
    assertEquals("$$", Price.all().get(0).getPriceRange());
  }

  @Test
  public void delete_deletesPriceInDatabase_true() {
    Price myPrice = new Price("$");
    myPrice.save();
    myPrice.delete();
    assertEquals(0, Price.all().size());
  }

  @Test
  public void getPrices_retrievesAllPricesFromDatabase_restaurantsList() {
    Price myPrice = new Price("$");
    myPrice.save();
    Restaurant restaurant1 = new Restaurant("Sakura Sushi", 1, myPrice.getPriceId());
    restaurant1.save();
    Restaurant restaurant2 = new Restaurant("Boxer Ramen", 1, myPrice.getPriceId());
    restaurant2.save();
    Restaurant[] restaurants = new Restaurant[] { restaurant1, restaurant2 };
    assertTrue(myPrice.getRestaurantsByPrice().containsAll(Arrays.asList(restaurants)));
  }
}
