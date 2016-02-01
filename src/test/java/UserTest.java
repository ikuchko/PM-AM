import org.junit.*;
import static org.junit.Assert.*;
import org.junit.Rule;
import java.util.List;

public class UserTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void user_createsNewUser() {
    User newUser = new User("PM", "Nathan", "nathan@pmam.com");
    newUser.save();
    User savedUser = User.find(newUser.getId());
    assertTrue(savedUser.equals(newUser));
    assertEquals(newUser.getRole(), "PM");
    assertEquals(newUser.getName(), "Nathan");
    assertEquals(newUser.getEmail(), "nathan@pmam.com");
  }

  @Test
  public void user_UpdatesUser() {
    User newUser = new User("PM", "Nathan", "nathan@pmam.com");
    newUser.save();
    newUser.update("Developer", "Nathan", "nathan@pmam.com");
    assertEquals("Developer", newUser.getRole());
  }

  @Test
  public void user_deletesUser() {
    User newUser = new User("PM", "Nathan", "nathan@pmam.com");
    newUser.save();
    newUser.delete();
    assertEquals(User.all().size(), 0);
  }

}
