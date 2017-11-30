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
    private int estimatedHours; // before completion, estimate the number of hours it will take
    private int actualHours; // after completion, how many hours did it take?
    private boolean complete; // is it complete?
    private String category; // what kind of task is it?

    /**
     * Constructor method for a task within a project.
     * @param taskId
     * @param taskName
     * @param hours expected hours for completion of task
     */
    public Task(int id, String name, int hours) {
        this.id = id;
        this.name = name;
        projectId = 0;
        employeeIds = new HashSet<>();
        startDate = LocalDate.now();
        targetEndDate = LocalDate.MAX;
        actualEndDate = LocalDate.MAX;
        deadline = LocalDate.MAX;
        notes = new String();
        estimatedHours = hours;
        complete = false;
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
    
    public void completeTask(int hours) {
    		complete = true;
    		actualHours = hours;
    }

	/**
	 * @return the estimatedHours
	 */
	public int getEstimatedHours() {
		return estimatedHours;
	}

	/**
	 * @return the hours it took to complete the task
	 */
	public int getActualHours() {
		return actualHours;
	}
	
	/**
	 * Getter for whether the task has been marked as complete
	 * @return true if complete, false if not
	 */
	public boolean isComplete() {
		return complete;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}
}
