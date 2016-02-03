import java.util.Arrays;
import java.util.List;

import org.junit.*;
import static org.junit.Assert.*;

public class ReportTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void task_deleteTaskWithAnyTypes() {
    User newUser = new User("PM", "Nathan", "nathan@pmam.com");
    Task newTask = new Task("Some title", newUser.getId(), Status.getId("In Progress"), "small description", TypeTask.getId("Epic"), newUser.getId());
    History history = new History(newTask.getId(), "Change Status", "In Progress", "Done");
    Report report = new Report(newTask.getId());
    assertFalse(report.getStartDate().equals(""));
    assertFalse(report.getFinishDate().equals(""));
    assertFalse(report.getDuration().equals(""));
  }
}
