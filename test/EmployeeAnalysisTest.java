import static org.junit.Assert.*;

import java.util.LinkedHashMap;

import org.junit.Before;
import org.junit.Test;

public class EmployeeAnalysisTest {

	private Company company = Company.getInstance();
	private LinkedHashMap<Integer, Employee> employeeMap = company.getEmployeeIdMap();
	private LinkedHashMap<Integer, Task> taskMap = company.getTaskIdMap();
	private EmployeeAnalysis ea;
	
	
	@Before
	public void setUpEmployee() {
		company.newEmployee("Jane");
		company.newTask(3, "A");
		company.newTask(6, "B");
		company.newTask(15, "C");
		company.newTask(10, "D");
		
		ea = new EmployeeAnalysis(employeeMap.get(1));
		
		//assign the tasks to the employee
		company.addEmployeeToTask(1, 1);
		company.addEmployeeToTask(2, 1);
		company.addEmployeeToTask(3, 1);
		company.addEmployeeToTask(4, 1);
		
		// complete some tasks
		taskMap.get(1).completeTask(4);
		taskMap.get(2).completeTask(10);
		
	}

	@Test
	public void testAssignedHours() {
		assertEquals("The employee has 25 uncompleted hours", 25, ea.getAssignedHours());
	}
	
	@Test
	public void testCompletedHours() {
		assertEquals("The employee has completed 14 hours", 14, ea.getCompletedHours());
	}
	
	@Test
	public void testEfficiency() {
		assertEquals("Effciency is 64%", 0.64286, ea.getEfficiency(), 0.001);
	}

}
