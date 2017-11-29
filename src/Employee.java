import java.time.LocalDate;
import java.util.LinkedHashSet;

/**
 * This class represents an employee object.
 * @author zyud
 *
 */
public class Employee {

    private int id;
    private String name;
    private LinkedHashSet<Integer> projects;
    private LinkedHashSet<Integer> tasks;

    /**
     * Constructor method for employee.
     * @param id
     * @param name
     */
    public Employee(int id, String name) {
        this.id = id;
        this.name = name;
        projects = new LinkedHashSet<>();
        tasks = new LinkedHashSet<>();
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the projects
     */
    public LinkedHashSet<Integer> getProjects() {
        return projects;
    }
    
    /**
     * Add a project.
     * @param projectId
     */
    public void addProject(int projectId) {
        projects.add(projectId);
    }
    
    /**
     * Remove a project.
     * @param projectId
     */
    public void removeProject(int projectId) {
        projects.remove(projectId);
    }

    /**
     * @return the tasks
     */
    public LinkedHashSet<Integer> getTasks() {
        return tasks;
    }
    
    /**
     * Add task.
     * @param taskId
     */
    public void addTask(int taskId) {
        tasks.add(taskId);
    }
    
    /**
     * Remove task.
     * @param taskId
     */
    public void removeTask(int taskId) {
        tasks.remove(taskId);
    }
}
