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
    Story newStory = new Story("Some title", newUser.getId(), "In Progres", "small description", 1, newUser.getId());
    newStory.save();
    Story savedStory = Story.find(newStory.getId());
    assertTrue(savedStory.equals(newStory));
    assertEquals(newStory.getTitle(), "Some title");
    assertEquals(newStory.getStatus(), "In Progres");
    assertEquals(newStory.getDescription(), "small description");
    assertEquals(newStory.getTypeTask(), 1);
    assertEquals(newStory.getCreator().getName(), "Nathan");
  }

  @Test
  public void test() {
    User newUser = new User("PM", "Nathan", "nathan@pmam.com");
    newUser.save();
    Story newStory = new Story("Some title", newUser.getId(), "In Progres", "small description", 1, newUser.getId());
    newStory.save();
    Story savedStory = Story.find(newStory.getId());
    assertEquals(savedStory.getImplementor().getName(), newUser.getName());

  }

  // @Test
  // public void story_Updated() {
  //   User newUser = new User("PM", "Nathan", "nathan@pmam.com");
  //   newUser.save();
  //   Story newStory = new Story("Some title", newUser.getId(), "In Progres", "small description", 1, newUser.getId());
  //   newStory.save();
  //   newStory.update("Some other title", "Large description");
  //   assertEquals(newStory.getTitle(), "Some other title");
  //   assertEquals(newStory.getDescription(), "Large description");
  // }
  //
  // @Test
  // public void story_UpdatesStatus() {
  //   User newUser = new User("PM", "Nathan", "nathan@pmam.com");
  //   newUser.save();
  //   Story newStory = new Story("Some title", newUser.getId(), "In Progres", "small description", 1, newUser.getId());
  //   newStory.save();
  //   newStory.updateStatus("Done");
  //   assertEquals(newStory.getStatus(), "Done");
  // }
  //
  // @Test
  // public void story_UpdatesImplementor() {
  //   User newUser = new User("PM", "Nathan", "nathan@pmam.com");
  //   newUser.save();
  //   Story newStory = new Story("Some title", newUser.getId(), "In Progres", "small description", 1, newUser.getId());
  //   newStory.save();
  //   User secondUser = new User("PM", "Illia", "illia@pmam.com");
  //   secondUser.save();
  //   newStory.updateImplementor(secondUser);
  //   assertEquals(newStory.getImplementor(), secondUser);
  // }
  //
  // @Test
  // public void story_deletesUser() {
  //   User newUser = new User("PM", "Nathan", "nathan@pmam.com");
  //   newUser.save();
  //   Story newStory = new Story("Some title", newUser.getId(), "In Progres", "small description", 1, newUser.getId());
  //   newStory.save();
  //   newStory.delete();
  //   assertEquals(Story.all().size(), 0);
  // }
}
