import java.time.LocalDate;
import java.util.HashSet;

/**
 * WIP!!!
 * 
 * This class implements a project object.
 * The inner class Task implements a task within a project.
 * @author zyud
 *
 * TODO: 1. implement methods to set dates from input string for both Project and Task.
 *       2. implement common sense checks (i.e. Can only assign employee to task who is
 *       already part of project. Start date can't be later than end dates and deadlines
 *       etc.)
 *       3. implement setter methods to project notes and task notes.
 */
public class Project {

    private int id;
    private String name;
    private boolean isActive;
    private HashSet<Integer> employees;
    private HashSet<Integer> tasks;
    private LocalDate startDate;
    private LocalDate targetEndDate;
    private LocalDate actualEndDate;
    private LocalDate deadline;
    private String notes;
    
    /**
     * Constructor for project.
     * @param id projectId
     * @param name project name
     */
    public Project(int id, String name) {
        this.id = id;
        this.name = name;
        isActive = true;
        employees = new HashSet<>();
        tasks = new HashSet<>();
        
        // Start date is set to today by default
        // Can be edited
        startDate = LocalDate.now();
        
        // End dates and deadlines are initialized with
        // placeholder MAX date. User shall set them to
        // be real dates as soon as possible.
        actualEndDate = LocalDate.MAX;
        targetEndDate = LocalDate.MAX;
        deadline = LocalDate.MAX;
        notes = new String();
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
     * This method removes an employee from the Set of employees on this project.
     * @param employeeId
     */
    public void removeEmployee(int employeeId) {
        employees.remove(employeeId);
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
     * @return the targetEndDate
     */
    public LocalDate getTargetEndDate() {
        return targetEndDate;
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
     * @return the notes
     */
    public String getNotes() {
        return notes;
    }
}
