import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

/**
 * This class performs analysis on a single employee. It tells how many hours they are currently assigned,
 * how many in total they have completed, and gives the ratio of estimated hours to assigned hours.
 * @author jennifersaouaf
 *
 */
public class EmployeeAnalysis {
	private Employee employee;
	private Company company;
	private LinkedHashMap<Integer, Task> taskIdMap;
	LinkedHashSet<Integer> taskIds;
	
	/**
	 * The constructor
	 * @param e the employee
	 */
	public EmployeeAnalysis(Employee e) {
		employee = e;
		company = Company.getInstance();
		taskIdMap = company.getTaskIdMap();
		taskIds = employee.getTasks();
	}
	
	/**
	 * Returns the number of hours of work on incomplete tasks that this employee is currently assigned
	 * @return the total hours of incomplete tasks
	 */
	public int getAssignedHours() {
		int hours = 0;
		
		for(Integer taskId: taskIds) {
			Task task = taskIdMap.get(taskId);
			
			if(!task.isComplete()) {
				hours += task.getEstimatedHours();
			}
		}

		return hours;
	}
	
	/**
	 * Returns the total number of hours the employee has completed on all tasks
	 * @return the total number of actual hours worked on tasks
	 */
	public int getCompletedHours() {
		int hours = 0;
		
		for(Integer taskId: taskIds) {
			Task task = taskIdMap.get(taskId);
			if(task.isComplete()) {
				hours += task.getActualHours();
			}
		}
		
		return hours;
	}
	
	/**
	 * This returns the ratio of estimated hours to actual hours completed.
	 * @return total estimated hours/total actual hours
	 */
	public double getEfficiency() {
		int completedHours = getCompletedHours();
		int estimatedHours = 0;
		
		for(Integer taskId: taskIds) {
			Task task = taskIdMap.get(taskId);
			if(task.isComplete()) {
				estimatedHours += task.getEstimatedHours();
			}
		}
		
		return (double)estimatedHours/completedHours;
	}
	
	/**
	 * Setter for the employee to be analyzed
	 * @param e employee
	 */
	public void changeEmployee(Employee e) {
		employee = e;
	}

}
