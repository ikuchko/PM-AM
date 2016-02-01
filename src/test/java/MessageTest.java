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
    Message message = new Message("description", 1);
    message.save();
    assertEquals("description", Message.find(message.getId()).getDescription());
  }

  @Test
  public void Message_deleteWorksProperly() {
    Message message = new Message("description", 1);
    message.save();
    message.delete();
    assertEquals(Message.all().size(), 0);
  }

  @Test
  public void message_updateWorksProperly() {
    Message message = new Message("description", 1);
    message.save();
    message.update("Joe", 7);
    assertEquals(message.getDescription(), "Joe");
    assertEquals(Message.find(message.getId()).getDescription(), "Joe");
  }

  @Test
  public void equals_returnsTrueIfSameNameAddressAndPhoneNumber() {
    Message firstMessage = new Message("Title", 1);
    firstMessage.save();
    Message secondMessage = new Message("Title", 1);
    secondMessage.save();
    assertTrue(firstMessage.equals(secondMessage));
  }
}
