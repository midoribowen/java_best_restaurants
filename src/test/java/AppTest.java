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

  @ClassRule
  public static ServerRule server = new ServerRule();

  @Test
  public void rootTest() {
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("Discover Portland's Food Carts");
  }

  @Test
  public void newRestaurantAddedToMainPage() {
    Cuisine japanese = new Cuisine("Japanese");
    japanese.save();
    Restaurant newRestaurant = new Restaurant ("Momo", japanese.getCuisineId(), "$$$");
    newRestaurant.save();
    String path = "http://localhost:4567/restaurants";
    goTo(path);
    assertThat(pageSource()).contains("Momo");
  }

  @Test
  public void deleteRestaurantById_removesRestaurant() {
    Cuisine american = new Cuisine("American");
    american.save();
    Restaurant newRestaurant = new Restaurant("Summer", american.getCuisineId(), "$");
    newRestaurant.save();
    newRestaurant.deleteRestaurantById(newRestaurant.getId());
    String path = String.format("http://localhost:4567/delete/%d", newRestaurant.getId());
    goTo(path);
    assertEquals(false, pageSource().contains("Summer"));
  }
}
