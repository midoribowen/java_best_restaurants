import org.fluentlenium.adapter.FluentTest;
import static org.junit.Assert.*;
import org.junit.*;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.fluentlenium.core.filter.FilterConstructor.*;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();

  @Override
  public WebDriver getDefaultDriver() {
      return webDriver;
  }

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @ClassRule
  public static ServerRule server = new ServerRule();

  @Test
  public void rootTest() {
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("Restaurants of Portland");
  }

  @Test
  public void restaurantsSearchedAreNotDisplayedOnSearchPage() {
    goTo("http://localhost:4567/");
    fill("#search").with("Shut Up And Eat");
    submit(".btn-warning");
    assertThat(pageSource()).contains("Sorry!");
  }

  @Test
  public void restaurantsSearchedAreDisplayedOnSearchPage() {
    Restaurant myRestaurant = new Restaurant("Local Boyz");
    Cuisine myCuisine = new Cuisine("Hawaiian");
    myRestaurant.save();
    myCuisine.save();
    myRestaurant.assignCuisine(myCuisine.getId());
    goTo("http://localhost:4567/");
    fill("#search").with("Local");
    submit(".btn-warning");
    assertThat(pageSource()).contains("Local Boyz");
    assertThat(pageSource()).contains("Hawaiian");
  }

  @Test
  public void restaurantAddedSuccessfully() {
    Cuisine myCuisine = new Cuisine("Southern");
    myCuisine.save();
    goTo("http://localhost:4567/restaurant-search?search=Local");
    fill("#name").with("Burgerville");
    click("option", withText("Southern"));
    submit(".btn-primary");
    assertThat(pageSource()).contains("Burgerville");
    assertThat(pageSource()).contains("Southern");
  }

  @Test
  public void goToRestaurantPage() {
    Cuisine myCuisine = new Cuisine("Southern");
    myCuisine.save();
    Restaurant newRestaurant = new Restaurant("Chipotle");
    newRestaurant.save();
    newRestaurant.assignCuisine(myCuisine.getId());
    goTo("http://localhost:4567/restaurant/" + Integer.toString(newRestaurant.getId()));
    assertThat(pageSource()).contains("Chipotle");
  }
}
