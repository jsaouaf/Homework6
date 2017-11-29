import java.time.LocalDate;
import java.util.HashSet;

/**
 * WIP!!!
 * This class implements a task object.
 * @author zyud
 *
 * TODO: Need to implement setter methods other than for employees and projects.
 * May not need setter methods for all variables.
 */
public class Task {

    private int id;
    private String name;
    private int projectId;
    private HashSet<Integer> employeeIds;
    private LocalDate startDate;
    private LocalDate targetEndDate;
    private LocalDate actualEndDate;
    private LocalDate deadline;
    private String notes;

    /**
     * Constructor method for a task within a project.
     * @param taskId
     * @param taskName
     * @param employeeId
     */
    public Task(int id, String name) {
        this.id = id;
        this.name = name;
        projectId = 0;
        employeeIds = new HashSet<>();
        startDate = LocalDate.now();
        targetEndDate = LocalDate.MAX;
        actualEndDate = LocalDate.MAX;
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
     * @return the projectId
     */
    public int getProjectId() {
        return projectId;
    }
    
    /**
     * This method sets the projectId.
     */
    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }
    

    /**
     * @return the employeeIds
     */
    public HashSet<Integer> getEmployeeIds() {
        return employeeIds;
    }
    
    /**
     * Adds an employee.
     * @param employeeId
     */
    public void addEmployee(int employeeId) {
        employeeIds.add(employeeId);
    }
    
    /**
     * Removes an employee.
     * @param employeeId
     */
    public void removeEmployee(int employeeId) {
        employeeIds.remove(employeeId);
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

    /**
     * This method replaces task name with input parameter String.
     * @param name
     */
    public void editName(String name) {
        this.name = name;
        System.out.println("Task name updated to \"" + this.name + "\"");
    }
}
