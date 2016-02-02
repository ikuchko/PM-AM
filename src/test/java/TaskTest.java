import java.util.Arrays;
import java.util.List;

import org.junit.*;
import static org.junit.Assert.*;

public class TaskTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Task.all().size(), 0);
  }

  @Test
  public void save_savesTaskIntoDatabase() {
    User user = new User("PM", "Nathan", "nathan@pmam.com");
    user.save();
    Task Task = new Task("Title", user.getId(), "In Progress", "This isn't a real Task", 1, 2);
    Task.save();
    assertEquals("Title", Task.find(Task.getId()).getTitle());
  }

  @Test
  public void Task_deleteWorksProperly() {
    User user = new User("PM", "Nathan", "nathan@pmam.com");
    user.save();
    Task Task = new Task("Title", user.getId(), "In Progress", "This isn't a real Task", 1, user.getId());
    Task.save();
    Task.delete();
    assertEquals(Task.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfSame() {
    User user = new User("PM", "Nathan", "nathan@pmam.com");
    user.save();
    Task firstTask = new Task("Title", 1, "In Progress", "This isn't a real Task", 1, 2);
    firstTask.save();
    Task secondTask = new Task("Title", 1, "In Progress", "This isn't a real Task", 1, 2);
    secondTask.save();
    assertTrue(firstTask.equals(secondTask));
  }

  @Test
  public void Task_updateTitleAndDescriptionWorksProperly() {
    User user = new User("PM", "Nathan", "nathan@pmam.com");
    user.save();
    Task Task = new Task("Title", 1, "In Progress", "This isn't a real Task", 1, 2);
    Task.save();
    Task.updateTitleAndDescription("New title", "haaaaaands");
    assertEquals(Task.getTitle(), "New title");
    assertEquals(Task.find(Task.getId()).getTitle(), "New title");
  }

  @Test
  public void Task_updateStatus() {
    User user = new User("PM", "Nathan", "nathan@pmam.com");
    user.save();
    Task Task = new Task("Title", 1, "In Progress", "This isn't a real Task", 1, 2);
    Task.save();
    Task.updateStatus("Completed");
    assertEquals(Task.getStatus(), "Completed");
    assertEquals(Task.find(Task.getId()).getStatus(), "Completed");
  }

  @Test
  public void Task_updateImplementer() {
    User user = new User("PM", "Nathan", "nathan@pmam.com");
    user.save();
    User newUser = new User("PM", "Andrey", "nathan@pmam.com");
    newUser.save();
    Task Task = new Task("Title", user.getId(), "In Progress", "This isn't a real Task", 1, user.getId());
    Task.save();
    Task.updateImplementer(newUser.getId());
    assertEquals(Task.getImplementerId(), newUser.getId());
    assertEquals(Task.find(Task.getId()).getImplementerId(), newUser.getId());
  }

}
