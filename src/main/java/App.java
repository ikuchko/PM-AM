import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;
import java.util.ArrayList;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("users", User.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/new-user", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/new-user.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/new-user", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      User role = new User((request.queryParams("role-selection")), (request.queryParams("user-name")), (request.queryParams("user-email")));
      response.redirect("/");
      return null;
    });

    get("/pm/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      User user = User.find(Integer.parseInt(request.queryParams("user")));
      request.session().attribute("user", user);
      List epics = Task.allByCreator(2, user.getId());
      model.put("epics", epics);
      model.put("user", user);
      model.put("template", "templates/pm-main.vtl");
      return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());

    get("/pm/create-epic", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("createEpic", Task.find(Integer.parseInt(request.params("id"))));
      response.redirect("/");
      return null;
    });

    get("/transfer", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      User user = User.find(Integer.parseInt(request.queryParams("user")));
      request.session().attribute("user", user);
      if (user.getRoleId() == Role.getId("PM")) {
        response.redirect("/");
      } else {
        response.redirect("/dev/main");
      }
      return null;
    });

    get("/dev/main", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("epics", Task.all(2));
      model.put("tasks", Task.class);
      model.put("user", request.session().attribute("user"));
      model.put("template", "templates/dev-home.vtl");
      return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());

    post("/assign-inprogress", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      User user = User.find(Integer.parseInt(request.queryParams("user")));
      request.session().attribute("user", user);
      if (user.getRoleId() == Role.getId("PM")) {
        response.redirect("/");
      } else {
        response.redirect("/dev/main");
      }
      return null;
    });
  }
}
