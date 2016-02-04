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

    get("/task/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/task.vtl");
      Task task = Task.find(4);
      model.put("task", task);
      model.put("status", Status.class);
      model.put("taskType", TypeTask.class);
      model.put("user", User.class);
      model.put("userCreator", User.find(task.getCreatorId()));
      model.put("userImplementor", User.find(task.getImplementorId()));
      model.put("currentUser", request.session().attribute("user"));
      model.put("message", Message.class);
      model.put("history", History.class);
      model.put("isHistoryTabActive", true);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/task/history/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/task.vtl");
      Task task = Task.find(4);
      model.put("task", task);
      model.put("status", Status.class);
      model.put("taskType", TypeTask.class);
      model.put("user", User.class);
      model.put("userCreator", User.find(task.getCreatorId()));
      model.put("userImplementor", User.find(task.getImplementorId()));
      model.put("currentUser", request.session().attribute("user"));
      model.put("message", Message.class);
      model.put("history", History.class);
      model.put("isHistoryTabActive", false);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
  }
}
