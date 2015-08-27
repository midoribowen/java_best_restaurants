import java.util.Random;
import java.util.HashMap;
import java.util.Arrays;
import java.util.ArrayList;
import java.lang.*;
import static spark.Spark.*;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import java.util.Map;

public class App {

  public static void main(String[] args) {
    staticFileLocation("/public"); // Relative path for images, css, etc.
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("restaurants", Restaurant.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //Add restaurant page
    get("/new-restaurant", (request, reponse) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("cuisines", Cuisine.all());//makes cuisine types available in select
      model.put("template", "templates/newrestaurant.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //Show all added restaurants
    get("/restaurants", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String cuisine_type = "";
      if (request.queryParams("cuisine_type") == "") {
        // grab from drop down menu
        cuisine_type = request.queryParams("existingCuisines");
      }
      if (request.queryParams("existingCuisines") != "new" && cuisine_type == "") {
        // grab from text field
        cuisine_type = request.queryParams("cuisine_type");
      }
      Cuisine newCuisine = new Cuisine(cuisine_type);
      newCuisine.save();
      String name = request.queryParams("name");
      String price = request.queryParams("price");
      Integer cuisine_id = newCuisine.getCuisineId();
      Restaurant newRestaurant = new Restaurant(name, cuisine_id, price);
      newRestaurant.save();
      model.put("cuisines", Cuisine.all());
      model.put("restaurants", Restaurant.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/delete/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      Integer restaurantId = Integer.parseInt(request.params(":id"));
      Restaurant.deleteRestaurantById(restaurantId);

      model.put("restaurants", Restaurant.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

  }

}
