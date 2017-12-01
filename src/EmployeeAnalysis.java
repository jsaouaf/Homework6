import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

/**
 * This class performs analysis on a single employee
 * @author jennifersaouaf
 *
 */
public class EmployeeAnalysis {
	private Employee employee;
	private Company company;
	private LinkedHashMap<Integer, Task> taskIdMap;
	
	/**
	 * The constructor
	 * @param e the employee
	 */
	public EmployeeAnalysis(Employee e) {
		employee = e;
		company = new Company("This Company");
		taskIdMap = company.getTaskIdMap();
	}
	
	/**
	 * Returns the number of hours of work on incomplete tasks that this employee is currently assigned
	 * @return the total hours of incomplete tasks
	 */
	public int getAssignedHours() {
		LinkedHashSet<Integer> taskIds = employee.getTasks();
		int hours = 0;
		
		for(Integer taskId: taskIds) {
			Task task = taskIdMap.get(taskId);
			
			if(!task.isComplete()) {
				hours += task.getEstimatedHours();
			}

		}
		
		return hours;
	}
	
	

}
