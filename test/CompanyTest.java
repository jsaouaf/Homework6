import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CompanyTest {
	private Company company;
	private Project project;
	private Employee employee;
	private Task task;
	private LocalDate today;
	
	@Before
	public void setUpCompany() {
		company = Company.getInstance();
		
		company.newEmployee("Talia");
		company.newEmployee("Kirsten");
		company.newEmployee("Julio");
		company.newEmployee("Jie");
		company.newEmployee("Patrick");
		company.newEmployee("Obi");
		
		// add new project
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate deadline = LocalDate.parse("2018-02-12", dtf);
		company.newProject("Interior Design Web App", deadline);
		company.newProject("Ice Cream Ordering App", deadline);
		company.newProject("Sudoku Game App", deadline);
		company.newProject("Baby Fart Simulator", deadline);
		
		// create new tasks for project
		company.newTask(20, "Concept research", 1);
		company.newTask(3, "Meet with clients", 1);
		company.newTask(5, "Unit testing");
		company.newTask(3, "Play squash with a client");
		company.newTask(15, "Learn Haskell for fun");
		
		// add Talia to project and tasks
		company.addEmployeeToProject(1, 1);
		company.addEmployeeToTask(1, 1);
		company.addEmployeeToTask(2, 1);
		
		// set up project object
		project = company.getProjectIdMap().get(1);
		
		// set up employee object
		employee = company.getEmployeeIdMap().get(1);
		
		// set up task object
		task = company.getTaskIdMap().get(3);
		
		// today's date
		today = LocalDate.now();
	}

	@Test
	public void testAddEmployeeToProject() {
		company.addEmployeeToProject(5, 1);
		
		assertEquals("Employee no. 5 should be listed under project 1", true,
				company.getProjectIdMap().get(1).getEmployees().contains(5));
	}

	@Test
	public void testEmployeeIdSystem(){
		Employee obi = company.getEmployeeIdMap().get(6);
		
		assertEquals("Employee no. 6 should be Obi", "Obi", obi.getName());
	}

	@Test
	public void testTaskIdSystem(){
		Task lhff = company.getTaskIdMap().get(5);
		
		assertEquals("Task no. 5 should be \"Learn Haskell for fun\"",
				"Learn Haskell for fun", lhff.getName());
	}

	@Test
	public void testProjectIdSystem(){
		Project bfs = company.getProjectIdMap().get(4);
		
		assertEquals("Project no. 4 should be Baby Fart Simulator",
				"Baby Fart Simulator", bfs.getName());
	}

	@Test
	public void testSetProjectForTask() {
		company.setProjectForTask(2, 1);
		
		Task task = company.getTaskIdMap().get(2);
		
		assertEquals("The project ID the task has been assigned to should be 1",
				1, task.getProjectId());
	}

	@Test
	public void testRemoveEmployeeFromTask() {
		company.removeEmployeeFromTask(1, 1);
		company.removeEmployeeFromTask(2, 1);
		
		assertEquals("Employee task list should be empty", true,
				employee.getTasks().isEmpty());
	}

	@Test
	public void testRemoveEmployeeFromProject() {
		company.removeEmployeeFromProject(1, 1);
		
		assertEquals("Employee project list should be empty", true,
				employee.getProjects().isEmpty());
	}

	@Test
	public void testDeleteTask() {
		company.deleteTask(1);
		
		assertEquals("Task list should not contain task 1", false,
				company.getTaskIdMap().containsKey(1));
	}
}
