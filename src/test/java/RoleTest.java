import org.junit.*;
import static org.junit.Assert.*;
import org.junit.Rule;
import java.util.List;

public class RoleTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void role_createsNewRole() {
    Role newRole = new Role("PM");
    newRole.save();
    Role savedRole = Role.find(newRole.getTitle());
    assertEquals(newRole.getTitle(), "PM");
    assertTrue(savedRole.equals(newRole));
  }

}
