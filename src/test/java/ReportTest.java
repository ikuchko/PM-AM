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
    Task newTask = new Task("Some title", newUser.getId(),"small description", TypeTask.getId("Epic"), newUser.getId());
    History history = new History(newTask.getId(), "Change Status", "In Progress", "Done");
    Report report = new Report(newTask.getId());
    // System.out.println(report.getDuration());
    assertFalse(report.getStartDate().equals(""));
    assertFalse(report.getFinishDate().equals(""));
    assertTrue(report.getDuration().equals(""));
  }

  @Test
  public void formattedString_actuallyFormatsString(){
    String duration = "0 years 0 mons 0 days 0 hours 5 mins 0.00 secs";
    System.out.println(Report.formattedDuration(duration));
    assertEquals(Report.formattedDuration(duration), "5 mins 0.00 secs");
  }
}
