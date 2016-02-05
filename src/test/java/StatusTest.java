import org.junit.*;
import static org.junit.Assert.*;
import org.junit.Rule;
import java.util.List;

public class StatusTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void status_returnAllstatuses() {
    assertEquals(4, Status.all().size());
    assertEquals("To Do", Status.all().get(0).getStatus());
  }
}
