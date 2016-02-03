import org.junit.*;
import static org.junit.Assert.*;
import org.junit.Rule;
import java.util.List;

public class TaskTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void task_createsNewTask() {
    User newUser = new User("PM", "Nathan", "nathan@pmam.com");
    Task newTask = new Task("Some title", newUser.getId(), "small description", TypeTask.getId("Epic"), newUser.getId());
    Task savedTask = Task.find(newTask.getId());
    assertTrue(savedTask.equals(newTask));
    assertEquals(newTask.getTitle(), "Some title");
    assertEquals(newTask.getStatus(), 1);
    assertEquals(newTask.getDescription(), "small description");
    assertEquals(newTask.getTypeTask(), 2);
    assertEquals(newTask.getCreatorId(), newUser.getId());
  }


  @Test
  public void task_Updated() {
    User newUser = new User("PM", "Nathan", "nathan@pmam.com");
    Task newTask = new Task("Some title", newUser.getId(), "small description", TypeTask.getId("Epic"), newUser.getId());
    newTask.update("Some other title", "Large description");
    assertEquals(newTask.getTitle(), "Some other title");
    assertEquals(newTask.getDescription(), "Large description");
  }

  @Test
  public void task_UpdatesStatus() {
    User newUser = new User("PM", "Nathan", "nathan@pmam.com");
    Task newTask = new Task("Some title", newUser.getId(), "small description", 1, newUser.getId());
    newTask.updateStatus(2);
    assertEquals(newTask.getStatus(), 2);
  }

  @Test
  public void task_UpdatesImplementor() {
    User newUser = new User("PM", "Nathan", "nathan@pmam.com");
    Task newTask = new Task("Some title", newUser.getId(), "small description", 1, newUser.getId());
    User secondUser = new User("PM", "Illia", "illia@pmam.com");
    newTask.updateImplementor(secondUser.getId());
    assertEquals(newTask.getImplementorId(), secondUser.getId());
  }

  @Test
  public void task_deleteTaskWithAnyTypes() {
    User newUser = new User("PM", "Nathan", "nathan@pmam.com");
    Task newTask = new Task("Some title", newUser.getId(), "small description", 1, newUser.getId());
    newTask.delete();
    assertEquals(0, Task.all(1).size());
  }

  @Test
  public void task_statusGetNameReturnsStatusName() {
    User newUser = new User("PM", "Nathan", "nathan@pmam.com");
    Task newTask = new Task("Some title", newUser.getId(), "small description", 1, newUser.getId());
    assertEquals(Status.getStatusName(newTask.getStatus()), "To Do");
    newTask.updateStatus(2);
    assertEquals(Status.getStatusName(newTask.getStatus()), "In Progress");
  }

  @Test
  public void getNextStatus_returnNextStatus_InProgress() {
    User newUser = new User("PM", "Nathan", "nathan@pmam.com");
    Task newTask = new Task("Some title", newUser.getId(), "small description", TypeTask.getId("Task"), newUser.getId());
    assertEquals(Status.getId("In Progress"), Status.getNextStatus(newTask));
  }

  @Test
  public void getNextStatus_returnNextStatus_Test() {
    User newUser = new User("PM", "Nathan", "nathan@pmam.com");
    Task newTask = new Task("Some title", newUser.getId(), "small description", TypeTask.getId("Task"), newUser.getId());
    newTask.updateStatus(Status.getId("In Progress"));
    assertEquals(Status.getId("Test"), Status.getNextStatus(newTask));
  }

  @Test
  public void getNextStatus_returnNextStatus_Done() {
    User newUser = new User("PM", "Nathan", "nathan@pmam.com");
    Task newTask = new Task("Some title", newUser.getId(), "small description", TypeTask.getId("Task"), newUser.getId());
    newTask.updateStatus(Status.getId("Test"));
    assertEquals(Status.getId("Done"), Status.getNextStatus(newTask));
  }

  @Test
  public void status_changedByWorkflow_true() {
    User newUser = new User("PM", "Nathan", "nathan@pmam.com");
    Task newTask = new Task("Some title", newUser.getId(), "small description", TypeTask.getId("Task"), newUser.getId());
    System.out.println(Status.getStatusName(newTask.getStatus()));
    assertTrue(newTask.changeStatus());
    assertEquals(Status.getId("In Progress"), newTask.getStatus());
  }

  @Test
  public void status_TaskCannotBeDoneWithUnresolvedBugs() {
    User newUser = new User("PM", "Nathan", "nathan@pmam.com");
    Task newTask = new Task("Some title", newUser.getId(), "small description", TypeTask.getId("Task"), newUser.getId());
    assertTrue(newTask.changeStatus());
    assertEquals(Status.getId("In Progress"), newTask.getStatus());
  }

  @Test
  public void task_GetAllAssignTask() {
    User newUser = new User("PM", "Nathan", "nathan@pmam.com");
    Task epicTask = new Task("Some title", newUser.getId(), "small description", TypeTask.getId("Epic"), newUser.getId());
    Task taskTask = new Task("Some title", newUser.getId(), "small description", TypeTask.getId("Task"), newUser.getId());
    Task taskTask = new Task("Some title", newUser.getId(), "small description", TypeTask.getId("Story"), newUser.getId());
    assertEquals(2, epicTask.allAssigned().size());
    assertEquals(taskTask, epicTask.allAssigned(TypeTask.getId("Task")));
  }

}
