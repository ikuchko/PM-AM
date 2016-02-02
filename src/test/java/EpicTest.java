import java.util.Arrays;
import java.util.List;

import org.junit.*;
import static org.junit.Assert.*;

public class EpicTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Epic.all().size(), 0);
  }

  @Test
  public void save_savesEpicIntoDatabase() {
    User user = new User("PM", "Nathan", "nathan@pmam.com");
    user.save();
    Epic epic = new Epic("Title", user.getId(), "In Progress", "This isn't a real epic", 1, 2);
    epic.save();
    assertEquals("Title", Epic.find(epic.getId()).getTitle());
  }

  @Test
  public void Epic_deleteWorksProperly() {
    User user = new User("PM", "Nathan", "nathan@pmam.com");
    user.save();
    Epic epic = new Epic("Title", user.getId(), "In Progress", "This isn't a real epic", 1, user.getId());
    epic.save();
    epic.delete();
    assertEquals(Epic.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfSame() {
    User user = new User("PM", "Nathan", "nathan@pmam.com");
    user.save();
    Epic firstEpic = new Epic("Title", 1, "In Progress", "This isn't a real epic", 1, 2);
    firstEpic.save();
    Epic secondEpic = new Epic("Title", 1, "In Progress", "This isn't a real epic", 1, 2);
    secondEpic.save();
    assertTrue(firstEpic.equals(secondEpic));
  }

  @Test
  public void Epic_updateTitleAndDescriptionWorksProperly() {
    User user = new User("PM", "Nathan", "nathan@pmam.com");
    user.save();
    Epic epic = new Epic("Title", 1, "In Progress", "This isn't a real epic", 1, 2);
    epic.save();
    epic.updateTitleAndDescription("New title", "haaaaaands");
    assertEquals(epic.getTitle(), "New title");
    assertEquals(Epic.find(epic.getId()).getTitle(), "New title");
  }

  @Test
  public void Epic_updateStatus() {
    User user = new User("PM", "Nathan", "nathan@pmam.com");
    user.save();
    Epic epic = new Epic("Title", 1, "In Progress", "This isn't a real epic", 1, 2);
    epic.save();
    epic.updateStatus("Completed");
    assertEquals(epic.getStatus(), "Completed");
    assertEquals(Epic.find(epic.getId()).getStatus(), "Completed");
  }

  @Test
  public void Epic_updateImplementer() {
    User user = new User("PM", "Nathan", "nathan@pmam.com");
    user.save();
    User newUser = new User("PM", "Andrey", "nathan@pmam.com");
    newUser.save();
    Epic epic = new Epic("Title", user.getId(), "In Progress", "This isn't a real epic", 1, user.getId());
    epic.save();
    epic.updateImplementer(newUser.getId());
    assertEquals(epic.getImplementer().getId(), newUser.getId());
    assertEquals(Epic.find(epic.getId()).getImplementer().getId(), newUser.getId());
  }

}
