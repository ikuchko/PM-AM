import org.junit.*;
import static org.junit.Assert.*;
import org.junit.Rule;
import java.util.List;

public class StoryTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void story_createsNewStory() {
    User newUser = new User("PM", "Nathan", "nathan@pmam.com");
    newUser.save();
    Story newStory = new Story("Some title", newUser.getId(), 1, "small description", 1, newUser.getId());
    newStory.save();
    Story savedStory = Story.find(newStory.getId());
    assertTrue(savedStory.equals(newStory));
    assertEquals(newStory.getTitle(), "Some title");
    assertEquals(newStory.getStatus(), 1);
    assertEquals(newStory.getDescription(), "small description");
    assertEquals(newStory.getTypeTask(), 1);
    assertEquals(newStory.getCreatorId(), newUser.getId());
  }


  @Test
  public void story_Updated() {
    User newUser = new User("PM", "Nathan", "nathan@pmam.com");
    newUser.save();
    Story newStory = new Story("Some title", newUser.getId(), 1, "small description", 1, newUser.getId());
    newStory.save();
    newStory.update("Some other title", "Large description");
    assertEquals(newStory.getTitle(), "Some other title");
    assertEquals(newStory.getDescription(), "Large description");
  }

  @Test
  public void story_UpdatesStatus() {
    User newUser = new User("PM", "Nathan", "nathan@pmam.com");
    newUser.save();
    Story newStory = new Story("Some title", newUser.getId(), 1, "small description", 1, newUser.getId());
    newStory.save();
    newStory.updateStatus(2);
    assertEquals(newStory.getStatus(), 2);
  }

  @Test
  public void story_UpdatesImplementor() {
    User newUser = new User("PM", "Nathan", "nathan@pmam.com");
    newUser.save();
    Story newStory = new Story("Some title", newUser.getId(), 1, "small description", 1, newUser.getId());
    newStory.save();
    User secondUser = new User("PM", "Illia", "illia@pmam.com");
    secondUser.save();
    newStory.updateImplementor(secondUser.getId());
    assertEquals(newStory.getImplementor(), secondUser.getId());
  }

  @Test
  public void story_deletesStory() {
    User newUser = new User("PM", "Nathan", "nathan@pmam.com");
    newUser.save();
    Story newStory = new Story("Some title", newUser.getId(), 1, "small description", 1, newUser.getId());
    newStory.save();
    newStory.delete();
    assertEquals(Story.all().size(), 0);
  }
}
