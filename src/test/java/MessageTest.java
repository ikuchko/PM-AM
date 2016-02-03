import java.util.Arrays;
import java.util.List;

import org.junit.*;
import static org.junit.Assert.*;

public class MessageTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Message.all().size(), 0);
  }

  @Test
  public void save_savesMessageIntoDatabase() {
    Message message = new Message("description", 1, 1);
    assertEquals("description", Message.find(message.getId()).getMessage());
  }

  @Test
  public void Message_deleteWorksProperly() {
    Message message = new Message("Title", 1, 1);
    message.delete();
    assertEquals(Message.all().size(), 0);
  }

  @Test
  public void message_updateWorksProperly() {
    User newUser = new User("PM", "Nathan", "nathan@pmam.com");
    Task newTask = new Task("Some title", newUser.getId(), "small description", 1, newUser.getId());
    Message message = new Message("Title", newUser.getId(), newTask.getId());
    message.update("Joe", 7);
    assertEquals(message.getMessage(), "Joe");
    assertEquals(Message.find(message.getId()).getMessage(), "Joe");
  }

  @Test
  public void equals_returnsTrueIfSameNameAddressAndPhoneNumber() {
    Message firstMessage = new Message("Title", 1, 1);
    Message secondMessage = new Message("Title", 1, 1);
    assertTrue(firstMessage.equals(secondMessage));
  }

  @Test
  public void assign_assignsMessageToTask() {
    Message message = new Message("Title", 1, 1);
    User newUser = new User("PM", "Illia", "illia@pmam.com");
    Task newTask = new Task("Some title", newUser.getId(), "small description", 1, newUser.getId());
    message.assignTask(newTask);
    assertEquals(newTask.getMessages().size(), 1);
  }

  @Test
  public void message_updateMakesNewHistory() {
    User newUser = new User("PM", "Nathan", "nathan@pmam.com");
    Task newTask = new Task("Some title", newUser.getId(), "small description", 1, newUser.getId());
    Message message = new Message("Title", newUser.getId(), newTask.getId());
    message.update("Joe", 7);
    assertEquals(message.getMessage(), "Joe");
    assertEquals(Message.find(message.getId()).getMessage(), "Joe");
    assertEquals(History.all(message.getTaskId()).get(0).getChangeType(), "Update Message");
  }
}
