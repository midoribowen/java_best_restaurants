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
      //model.put("cuisineTypes", Cuisine.all());
      //model.put("prices", Price.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //Add restaurant page
    get("/new-restaurant", (request, reponse) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("cuisineTypes", Cuisine.all());//makes cuisine types available in select
      model.put("template", "templates/newrestaurant.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //Show all added restaurants
    get("/restaurants", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      String name = request.queryParams("name");
      Integer price_id = Integer.parseInt(request.queryParams("price_id"));
      //Integer cuisine_id = Integer.parseInt(request.queryParams("cuisine"));
      Restaurant newRestaurant = new Restaurant(name, 1, price_id);
      newRestaurant.save();


      //Integer price_range = Integer.parseInt(request.queryParams("price_range"));
      //Integer cuisine_type = Integer.parseInt(request.queryParams(""));
      model.put("restaurants", Restaurant.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

  }

}
