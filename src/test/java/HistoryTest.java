import org.junit.*;
import static org.junit.Assert.*;
import org.junit.Rule;
import java.util.List;

public class HistoryTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void history_createsNewHistory() {
    User newUser = new User("PM", "Nathan", "nathan@pmam.com");
    newUser.save();
    Task newTask = new Task("Some title", newUser.getId(), Status.getId("In Progress"), "small description", TypeTask.getId("Epic"), newUser.getId());
    newTask.save();
    Message message = new Message("Title", 1, 1);
    message.save();
    History history = new History(newTask.getId(), "Add message", "", message.getMessage());
    history.save();
    History savedHistory = History.find(history.getId());
    System.out.println(history.getCreatedDate());
    System.out.println(savedHistory.getCreatedDate());
    assertTrue(savedHistory.equals(history));
    assertEquals(history.getTaskId(), newTask.getId());
    assertEquals(history.getChangeType(), "Add message");
    assertEquals(history.getPreviousCondition(), "");
    assertEquals(history.getCurrentCondition(), message.getMessage());
  }

  @Test
  public void delete_deletesHistory() {
    User newUser = new User("PM", "Nathan", "nathan@pmam.com");
    newUser.save();
    Task newTask = new Task("Some title", newUser.getId(), Status.getId("In Progress"), "small description", TypeTask.getId("Epic"), newUser.getId());
    newTask.save();
    Message message = new Message("Title", 1, 1);
    message.save();
    History history = new History(newTask.getId(), "Add message", "", message.getMessage());
    history.save();
    history.delete();
    assertEquals(0, History.all(newTask.getId()).size());
  }
}
