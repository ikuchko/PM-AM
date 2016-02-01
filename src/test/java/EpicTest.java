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
    Epic epic = new Epic("Title", 1, "In Progress", "This isn't a real epic", 1, 2);
    epic.save();
    assertEquals("Title", Epic.find(epic.getId()).getTitle());
  }

  @Test
  public void Epic_deleteWorksProperly() {
    Epic epic = new Epic("Title", 1, "In Progress", "This isn't a real epic", 1, 2);
    epic.save();
    epic.delete();
    assertEquals(Epic.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfSame() {
    Epic firstEpic = new Epic("Title", 1, "In Progress", "This isn't a real epic", 1, 2);
    firstEpic.save();
    Epic secondEpic = new Epic("Title", 1, "In Progress", "This isn't a real epic", 1, 2);
    secondEpic.save();
    assertTrue(firstEpic.equals(secondEpic));
  }

  @Test
  public void Epic_updateTitleAndDescriptionWorksProperly() {
    Epic epic = new Epic("Title", 1, "In Progress", "This isn't a real epic", 1, 2);
    epic.save();
    epic.updateTitleAndDescription("New title", "haaaaaands");
    assertEquals(epic.getTitle(), "New title");
    assertEquals(Epic.find(epic.getId()).getTitle(), "New title");
  }

  @Test
  public void Epic_updateStatus() {
    Epic epic = new Epic("Title", 1, "In Progress", "This isn't a real epic", 1, 2);
    epic.save();
    epic.updateStatus("Completed");
    assertEquals(epic.getStatus(), "Completed");
    assertEquals(Epic.find(epic.getId()).getStatus(), "Completed");
  }

  @Test
  public void Epic_updateDeveloper() {
    Epic epic = new Epic("Title", 1, "In Progress", "This isn't a real epic", 1, 2);
    epic.save();
    epic.updateImplementer(4);
    assertEquals(epic.getImplementerId(), 4);
    assertEquals(Epic.find(epic.getId()).getImplementerId(), 4);
  }

}
