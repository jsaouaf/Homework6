import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.SortedMap;
import java.util.TreeMap;

/** 
 * This class implements a project object.
 * The inner class Task implements a task within a project.
 * @author zyud
 *
 */
public class Project implements Serializable{

    private int id;
    private String name;
    private boolean isActive;
    private HashSet<Integer> employees;
    private HashSet<Integer> tasks;
    private LocalDate startDate;
    private LocalDate actualEndDate;
    private LocalDate deadline;
    
    // TreeMap to store dates with taskHoursLeft for burndown chart
    private TreeMap<LocalDate, Integer> taskHoursLog;
    
    /**
     * Constructor for project.
     * @param id projectId
     * @param name project name
     */
    public Project(int id, String name, LocalDate deadline) {
        this.id = id;
        this.name = name;
        isActive = true;
        employees = new HashSet<>();
        tasks = new HashSet<>();
        
        // start date is set to today
        startDate = LocalDate.now();
        
        // set deadline
        this.deadline = deadline;
        
        // actualEnddate and deadlines are initialized with
        // placeholder MAX date.User shall set deadline to
        // be a real date as soon as possible.
        actualEndDate = LocalDate.MAX;
        
        taskHoursLog = new TreeMap<>();
        taskHoursLog.put(LocalDate.now(), 0);
    }
    
    /**
     * This method sets a project as complete.
     */
    public void completeProject() {
    	actualEndDate = LocalDate.now();
    	isActive = false;
    }
    
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the isActive
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * @return the employees
     */
    public HashSet<Integer> getEmployees() {
        return employees;
    }
    
    /**
     * This method adds an employee to the Set of employees on this project.
     * @param employeeId
     */
    public void addEmployee(int employeeId) {
        employees.add(employeeId);
    }
    
    /**
     * Removes an employee from the Set of employees on this project.
     * @param employeeId
     */
    public void removeEmployee(int employeeId) {
        employees.remove(employeeId);
    }
    
    /**
     * sets the deadline of the project.
     */
    public void setDeadline(String deadline) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(deadline, dtf);
        this.deadline = date;
    }

    /**
     * @return the tasks
     */
    public HashSet<Integer> getTasks() {
        return tasks;
    }
    
    /**
     * Adds a task to project.
     * @param taskId
     */
    public void addTask(int taskId) {
        tasks.add(taskId);
    }
    
    /**
     * Removes a task from project.
     * @param taskId
     */
    public void removeTask(int taskId) {
        tasks.remove(taskId);
    }

    /**
     * @return the startDate
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * @return the actualEndDate
     */
    public LocalDate getActualEndDate() {
        return actualEndDate;
    }

    /**
     * @return the deadline
     */
    public LocalDate getDeadline() {
        return deadline;
    }

	/**
	 * @return the taskHoursLog
	 */
	public TreeMap<LocalDate, Integer> getTaskHoursLog() {
		return taskHoursLog;
	}
}
