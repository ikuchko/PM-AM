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
    History history = new History(newTask.getId(), "Update Status", "In Progress", "Done");
    Report report = new Report(newTask.getId());
    // System.out.println(report.getDuration());
    assertFalse(report.getStartDate().equals(""));
    assertFalse(report.getFinishDate().equals(""));
    assertFalse(report.getDuration().equals(""));
  }

  @Test
  public void formattedDuration_correctlyFormatsDurationOutput() {
    String durationOne = "1 years 0 mons 0 days 0 hours 0 mins 0.00 secs";
    String durationTwo = "0 years 1 mons 0 days 0 hours 0 mins 0.00 secs";
    String durationThree = "0 years 0 mons 1 days 0 hours 0 mins 0.00 secs";
    String durationFour = "0 years 0 mons 0 days 1 hours 0 mins 0.00 secs";
    System.out.println(Report.formattedDuration(durationOne));
    System.out.println(Report.formattedDuration(durationTwo));
    System.out.println(Report.formattedDuration(durationThree));
    System.out.println(Report.formattedDuration(durationFour));
    assertTrue(Report.formattedDuration(durationOne).equals("1 years 0 mons 0 days 0 hours 0 mins 0.00 secs"));
    assertTrue(Report.formattedDuration(durationTwo).equals("1 mons 0 days 0 hours 0 mins 0.00 secs"));
    assertTrue(Report.formattedDuration(durationThree).equals("1 days 0 hours 0 mins 0.00 secs"));
    assertTrue(Report.formattedDuration(durationFour).equals("1 hours 0 mins 0.00 secs"));
  }

}
