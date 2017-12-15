import java.io.Serializable;
import java.util.LinkedHashMap;

/**
 * WIP!!!!
 * This class represents the company. It keeps track of projects, employees, and tasks.
 * This should be the only class the customer interfaces with.
 * @author zyud
 *
 * TODO: 1. ID generation system to be modified to generate unique IDs for projects,
 *       employees, and tasks. Right now they are too similar to one another and can
 *       get mixed up.
 *       2. throw exceptions for things like "employee not found", "project not found"
 *       etc.
 *       3. Finish documentation and adding in additional methods.
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

    public void newProject(String name) {
        projectIdTally++;
        Project project = new Project(projectIdTally, name);
        projectIdMap.put(projectIdTally, project);
    }

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

    public void removeProjectFromTask(int taskId, int projectId) {
        Task thisTask = taskIdMap.get(taskId);

        if (thisTask.getProjectId() != 0) {
            int oldProjectId = thisTask.getProjectId();
            projectIdMap.get(oldProjectId).removeTask(taskId);
            thisTask.setProjectId(0);
        }
    }

    public void addEmployeeToTask(int taskId, int employeeId) {
        Task thisTask = taskIdMap.get(taskId);
        Employee thisEmployee = employeeIdMap.get(employeeId);

        thisTask.addEmployee(employeeId);
        thisEmployee.addTask(taskId);
    }

    public void removeEmployeeFromTask(int taskId, int employeeId) {
        Task thisTask = taskIdMap.get(taskId);
        Employee thisEmployee = employeeIdMap.get(employeeId);

        thisTask.removeEmployee(employeeId);
        thisEmployee.removeTask(taskId);
    }

    public void addEmployeeToProject(int employeeId, int projectId) {
        projectIdMap.get(projectId).addEmployee(employeeId);
        employeeIdMap.get(employeeId).addProject(projectId);
    }

    public void removeEmployeeFromProject(int employeeId, int projectId) {
        projectIdMap.get(projectId).removeEmployee(employeeId);
        employeeIdMap.get(employeeId).removeProject(projectId);
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
