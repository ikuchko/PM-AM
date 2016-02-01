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
    Epic epic = new Epic("Title", 1, "2016-02-01", "In Progress", "This isn't a real epic", 1, 2);
    epic.save();
    assertEquals("Title", Epic.find(epic.getId()).getTitle());
  }

  @Test
  public void Epic_deleteWorksProperly() {
    Epic epic = new Epic("Title", 1, "2016-02-01", "In Progress", "This isn't a real epic", 1, 2);
    epic.save();
    epic.delete();
    assertEquals(Epic.all().size(), 0);
  }

  @Test
  public void epic_updateWorksProperly() {
    Epic epic = new Epic("Title", 1, "2016-02-01", "In Progress", "This isn't a real epic", 1, 2);
    epic.save();
    epic.update("Name", 1, "2016-02-01", "In Progress", "This isn't a real epic", 1, 2);
    assertEquals(epic.getTitle(), "Name");
    assertEquals(Epic.find(epic.getId()).getTitle(), "Name");
  }

  @Test
  public void equals_returnsTrueIfSameNameAddressAndPhoneNumber() {
    Epic firstEpic = new Epic("Title", 1, "2016-02-01", "In Progress", "This isn't a real epic", 1, 2);
    firstEpic.save();
    Epic secondEpic = new Epic("Title", 1, "2016-02-01", "In Progress", "This isn't a real epic", 1, 2);
    secondEpic.save();
    assertTrue(firstEpic.equals(secondEpic));
  }
}
