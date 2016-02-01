import org.junit.*;
import static org.junit.Assert.*;
import org.junit.Rule;
import java.util.List;

public class RoleTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void roleNameById() {
    assertEquals(1, Role.getId("PM"));
  }
}
