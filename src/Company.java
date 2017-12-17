import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;

/**
 * This class represents the company. It keeps track of projects, employees, and tasks.
 * This should be the only class the customer interfaces with.
 * @author zyud
 *
 */
public class Company implements Serializable{
    private static Company companyInstance = new Company(); // singleton constructor for the company

	private String name;
    private int projectIdTally;
    private int employeeIdTally;
    private int taskIdTally;

    // These LinkedHashMaps map the object id to their respective objects.
    // Using a LinkedHashMap instead of HashMap allows the object insertion order to
    // be preserved, which is good for printing out a list of objects for user.
    private LinkedHashMap<Integer, Project> projectIdMap;
    private LinkedHashMap<Integer, Employee> employeeIdMap;
    private LinkedHashMap<Integer, Task> taskIdMap;

    // cannot instantiate this class; we only want one company
    private Company() {
        projectIdTally = 0;
        employeeIdTally = 0;
        taskIdTally = 0;
        projectIdMap = new LinkedHashMap<>();
        employeeIdMap = new LinkedHashMap<>();
        taskIdMap = new LinkedHashMap<>();
    }

    public static Company getInstance() {
    		return companyInstance;
    }

    public void setCompany(Company restoredCompany) {
    		companyInstance = restoredCompany;
    }
    
    /**
     * Creates a new project.
     * @param name
     */
    public void newProject(String name, LocalDate deadline) {
        projectIdTally++;
        Project project = new Project(projectIdTally, name, deadline);
        projectIdMap.put(projectIdTally, project);
    }
    
    /**
     * Creates a new employee.
     * @param name
     */
    public void newEmployee(String name) {
        employeeIdTally++;
        Employee employee = new Employee(employeeIdTally, name);
        employeeIdMap.put(employeeIdTally, employee);
    }

    /**
     * Method for creating a new task without specifying project.
     * @param hours expected hours for completion
     * @param name task name
     */
    public void newTask(int hours, String name) {
        taskIdTally++;
        Task task = new Task(taskIdTally, name, hours);
        taskIdMap.put(taskIdTally, task);
    }

    /**
     * Method for creating a new task with a specified project.
     * @param hours expected hours for completion
     * @param name task name
     * @param projectId
     */
    public void newTask(int hours, String name, int projectId) {
        taskIdTally++;
        Task task = new Task(taskIdTally, name, hours);
        taskIdMap.put(taskIdTally, task);
        task.setProjectId(projectId);
        projectIdMap.get(projectId).addTask(taskIdTally);
    }

    /**
     * Sets a project for a specific task
     * @param projectId
     */
    public void setProjectForTask(int taskId, int projectId) {
        Task thisTask = taskIdMap.get(taskId);

        // Add project to task if previously unassigned
        // Replace old project with new if project previously
        // assigned to task.
        if (thisTask.getProjectId() == 0) {
            taskIdMap.get(taskId).setProjectId(projectId);
            projectIdMap.get(projectId).addTask(taskId);
        } else {
            int oldProjectId = thisTask.getProjectId();
            projectIdMap.get(oldProjectId).removeTask(taskId);
            projectIdMap.get(projectId).addTask(taskId);
        }
    }
    
    /**
     * Removes a project from task.
     * @param taskId
     * @param projectId
     */
    public void removeProjectFromTask(int taskId, int projectId) {
        Task thisTask = taskIdMap.get(taskId);

        if (thisTask.getProjectId() != 0) {
            int oldProjectId = thisTask.getProjectId();
            projectIdMap.get(oldProjectId).removeTask(taskId);
            thisTask.setProjectId(0);
        }
    }
    
    /**
     * This method adds an employee to a task.
     * @param taskId
     * @param employeeId
     */
    public void addEmployeeToTask(int taskId, int employeeId) {
        Task thisTask = taskIdMap.get(taskId);
        Employee thisEmployee = employeeIdMap.get(employeeId);

        thisTask.addEmployee(employeeId);
        thisEmployee.addTask(taskId);
    }
    
    /**
     * This method removes an employee from a task.
     * @param taskId
     * @param employeeId
     */
    public void removeEmployeeFromTask(int taskId, int employeeId) {
        Task thisTask = taskIdMap.get(taskId);
        Employee thisEmployee = employeeIdMap.get(employeeId);

        thisTask.removeEmployee(employeeId);
        thisEmployee.removeTask(taskId);
    }

    /**
     * This method adds an employee to a project.
     * @param employeeId
     * @param projectId
     */
    public void addEmployeeToProject(int employeeId, int projectId) {
        projectIdMap.get(projectId).addEmployee(employeeId);
        employeeIdMap.get(employeeId).addProject(projectId);
    }
    
    /**
     * This method removes an employee from a project they are assigned to.
     * @param employeeId
     * @param projectId
     */
    public void removeEmployeeFromProject(int employeeId, int projectId) {
        projectIdMap.get(projectId).removeEmployee(employeeId);
        employeeIdMap.get(employeeId).removeProject(projectId);
    }

    /**
     * This method deletes an employee from the system.
     * @param employeeId
     */
    public void deleteEmployee(int employeeId) {
    	Employee thisEmployee = employeeIdMap.get(employeeId);

    	// remove employee from all tasks
    	for (int taskId : thisEmployee.getTasks()) {
    		removeEmployeeFromTask(employeeId, taskId);
    	}

    	// remove employee from all projects
    	for (int projectId : thisEmployee.getProjects()) {
    		removeEmployeeFromProject(employeeId, projectId);
    	}

    	// removing employee from employeeIdMap
    	employeeIdMap.remove(employeeId);
    }
    
    public void updateTaskHoursLog() {
    	LocalDate today = LocalDate.now();
    	
    	// iterate thru each project and update taskHoursLog
    	for (Project project : projectIdMap.values()) {
    		// Skip inactive projects
    		if (!project.isActive()) {
    			continue;
    		}
    		
    		// calculate task hours left for this project
    		int taskHoursLeft = 0;
    		for (Integer taskId : project.getTasks()) {
    			Task task = taskIdMap.get(taskId);
    			if (!task.isComplete()) {
    				taskHoursLeft++;
    			}
    		}
    		// fill in any gaps in the dates
    		LocalDate yesterday = LocalDate.now().minusDays(1);
    		
    		// while the last entry in the log is earlier than yesterday, fill in
    		// in the info until yesterday has been covered
    		// if there are gaps in the log, that means the tasks hours did not change
    		// for the dates that are missing, so we will fill them with the latest
    		// task hours
    		while (project.getTaskHoursLog().lastKey().isBefore(yesterday)) {
    			LocalDate gapDate = project.getTaskHoursLog().lastKey().plusDays(1);
    			int taskHoursLastLogged = project.getTaskHoursLog().lastEntry().getValue();
    			project.getTaskHoursLog().put(gapDate, taskHoursLastLogged);
    		}

    		// update taskHoursLog for that project
    		project.getTaskHoursLog().put(LocalDate.now(), taskHoursLeft);
    	}
    }
    
    /**
     * This method completes a task and updates the task hours log.
     * @param taskId
     * @param hours
     */
    public void completeTaskAndUpdate(int taskId, int hours) {
    	Task task = taskIdMap.get(taskId);
    	task.completeTask(hours);
    	updateTaskHoursLog();
    }
    
    /**
     * This method deletes a task.
     * @param taskId
     */
    public void deleteTask(int taskId) {
    	Task task = taskIdMap.get(taskId);
    	
    	// deleting task from any projects
    	if (task.getProjectId() != 0) {
    		removeProjectFromTask(taskId, task.getProjectId());
    	}
    	
    	// deleting task from any employees
    	if (!task.getEmployeeIds().isEmpty()) {
    		for (int employeeId : task.getEmployeeIds()) {
    			removeEmployeeFromTask(taskId, employeeId);
    		}
    	}
    	
    	// deleting task from HashMap
    	taskIdMap.remove(taskId);
    }

    public void printAllProjects() {
        for (int id : projectIdMap.keySet()) {
            System.out.println("ID: " + id + ", Name: " + projectIdMap.get(id).getName());
        }
    }

    public void printAllEmployees() {
        for (int id : employeeIdMap.keySet()) {
            System.out.println("ID: " + id + ", Name: " + employeeIdMap.get(id).getName());
        }
    }

    public void printAllTasks() {
        for (int id : taskIdMap.keySet()) {
            System.out.println("ID: " + id + ", Name: " + taskIdMap.get(id).getName());
        }
    }

    public LinkedHashMap<Integer, Employee> getEmployeeIdMap(){
    		return employeeIdMap;
    }

    public LinkedHashMap<Integer, Task> getTaskIdMap(){
    		return taskIdMap;
    }

    public LinkedHashMap<Integer, Project> getProjectIdMap(){
    		return projectIdMap;
    }

    public void setName(String name) {
    		this.name = name;
    }

    public String getName() {
    		return this.name;
    }

}
