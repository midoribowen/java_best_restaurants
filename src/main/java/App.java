import java.util.List;
import java.util.Map;
import java.util.HashMap;
import static spark.Spark.*;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

public class App {

  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    /******************************************************
      Students: TODO: Display all restaurants on main page
    *******************************************************/
    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("cuisines", Cuisine.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/restaurant-search", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      boolean successfulSearch = false;
      String userSearch = request.queryParams("search").trim();
      List<Restaurant> searchResults = Restaurant.search(userSearch);
      if (!(searchResults.isEmpty())) {
        successfulSearch = true;
      }
      model.put("successfulsearch", successfulSearch);
      model.put("searchresults", searchResults);
      model.put("userSearch", userSearch);

      model.put("cuisines", Cuisine.all());

      model.put("template", "templates/restaurant-search.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/restaurant/new", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String newName = request.queryParams("newname");
      Restaurant newRestaurant = new Restaurant(newName);
      newRestaurant.save();

      Integer newCuisineId = Integer.parseInt(request.queryParams("cuisineId"));
      newRestaurant.assignCuisine(newCuisineId);


      model.put("restaurant", newRestaurant);
      model.put("template", "templates/restaurant.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    /******************************************************
    STUDENTS:
    TODO: Create page to display information about the selected restaurant
    TODO: Create page to display restaurants by cuisine type
    *******************************************************/

  }
}
