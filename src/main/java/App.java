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

    get("/transfer", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      User user = User.find(Integer.parseInt(request.queryParams("user")));
      request.session().attribute("user", user);
      if (user.getRoleId() == Role.getId("PM")) {
        response.redirect("/pm/");
      } else {
        response.redirect("/dev/main");
      }
      return null;
    });

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
      User user = request.session().attribute("user");
      List epics = Task.allByCreator(2, user.getId());
      model.put("report", Report.class);
      model.put("epics", epics);
      model.put("user", user);
      model.put("template", "templates/pm-main.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/pm/create-epic/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      User user = request.session().attribute("user");
      List epics = Task.allByCreator(2, user.getId());
      model.put("report", Report.class);
      model.put("epics", epics);
      model.put("user", user);
      model.put("createEpicActive", true);
      model.put("template", "templates/pm-main.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/pm/create-epic/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      User user = (request.session().attribute("user"));
      Task task = new Task(request.queryParams("add-title"), user.getId(), request.queryParams("add-description"), 2, user.getId());
      model.put("user", user);
      response.redirect("/pm/?user=" + user.getId());
      return null;
    });

    get("/pm/update-epic/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      User user = request.session().attribute("user");
      List epics = Task.allByCreator(2, user.getId());
      Task epic = Task.find(Integer.parseInt(request.params("id")));
      model.put("report", Report.class);
      model.put("epics", epics);
      model.put("epic", epic);
      model.put("user", user);
      model.put("updateEpicActive", true);
      model.put("template", "templates/pm-main.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/pm/update-epic/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Task epic = Task.find(Integer.parseInt(request.params("id")));
      User user = request.session().attribute("user");
      epic.update(request.queryParams("updateTitle"), request.queryParams("updateDescription"));

      model.put("user", user);
      response.redirect("/pm/?user=" + user.getId());
      return null;
    });

    get("/dev/main", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("epics", Task.all(2));
      model.put("stories", Task.all(1));
      model.put("tasks", Task.class);
      model.put("user", request.session().attribute("user"));
      model.put("users", User.all(2));
      model.put("template", "templates/dev-home.vtl");
      return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());

    post("/dev/assign-toboard/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      User user = (request.session().attribute("user"));
      Task task = Task.find(Integer.parseInt(request.params(":id")));
      if (task.getOnBoard()){
        task.changeOnBoard(false);
      } else if (!(task.getOnBoard())) {
        User assignedDev = User.find(Integer.parseInt(request.queryParams("developerId")));
        task.changeOnBoard(true);
        task.assignTask(assignedDev.getId());
      }
      model.put("task", task);
      model.put("user", user);
      response.redirect("/dev/main?user=" + "user.getId()");
      return null;
    });

    post("/dev/new-task", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      User user = (request.session().attribute("user"));
      int creator = user.getId();
      String title = request.queryParams("title");
      String description = request.queryParams("add-description");
      Task story = Task.find(Integer.parseInt(request.queryParams("storyId")));
      User implementor = User.find(Integer.parseInt(request.queryParams("developerId")));
      int implementorId = implementor.getId();

      Task task = new Task (title, creator, description, 3, implementorId);
      story.assign(task);

      model.put("task", task);
      model.put("user", user);
      response.redirect("/dev/main?user=" + "user.getId()");
      return null;
    });

  }
}
