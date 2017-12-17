import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * This class is the controller for the DashBoardView interface. It is the main screen the user interacts with.
 * @author cgui1
 *
 */
public class DashboardController2 {
	//Instance variables to be used in the class
	private LinkedHashMap<Integer, Project> projects;
	private LinkedHashMap<Integer, Task> tasks;
	private LinkedHashMap<Integer, Employee> employees;
	private Integer selectedProjectId;
	private Integer selectedTaskId;
	private Integer selectedTaskIndex;

	//Instance variables for listview items
	private ObservableList<String> projEmpsList;
	private ArrayList<String> selectedProjectEmployees;
	private ObservableList<String> projTasksList;
	private ArrayList<String> selectedProjectTasks;
	private ObservableList<String> selectedTaskEmployees;
	private ArrayList<String> taskEmpsList;
	private ObservableList<String> taskList;
	private ArrayList<String> taskNames;
	private ObservableList<String> taskBoxList;


	//FXML instance variables, used in the view interface fxml
	@FXML
	private Label companyNameLbl;
	@FXML
	private ListView<String> projectListView;
	@FXML
	private ListView<String> tasksListView;
	@FXML
	private ListView<String> projectTasksListView;
	@FXML
	private ListView<String> projectEmployeesListView;
	@FXML
	private ListView<String> taskEmployeesListView;

	//Project View Controls Fields
	@FXML
	private Label nameLbl;
	@FXML
	private Label startDateLbl;
	@FXML
	private Label deadlineLbl;

	//Tasks View Controls Fields
	@FXML
	private Label taskHoursLbl;
	@FXML
	private Label taskProjLbl;
	@FXML
	private ComboBox employeeBox;
	@FXML
	private ComboBox taskBox;
	@FXML
	private ComboBox taskEmployeesBox;
	@FXML
	private Button newTask;
	@FXML
	private TextField hoursToCompleteTask;
	@FXML
	private Button completeTask;

	/**
	 * This method initializes the class.
	 */
	@FXML
	private void initialize(){
		companyNameLbl.setText(Company.getInstance().getName());
		companyNameLbl.setVisible(true);

		//Initializes tasks and projects maps
		tasks = Company.getInstance().getTaskIdMap();
		projects = Company.getInstance().getProjectIdMap();
		employees = Company.getInstance().getEmployeeIdMap();

		//Create the listviews observable lists of projects names and task names
		ArrayList<String> projectNames = new ArrayList<String>();
		taskNames = new ArrayList<String>();
		ArrayList<String> employeeNames = new ArrayList<String>();

		for (Integer i: projects.keySet()){
			projectNames.add(projects.get(i).getName() + " (ID: " + i + ")");
		}

		for (Integer i: tasks.keySet()){
			if(tasks.get(i).isComplete()){
				taskNames.add("COMPLETED: " + tasks.get(i).getName() + " (ID: " + i + ")");
			} else {
				taskNames.add(tasks.get(i).getName() + " (ID: " + i + ")");
			}
		}

		for (Integer i: employees.keySet()){
			employeeNames.add(employees.get(i).getName() + " (ID: " + i + ")");
		}

		ObservableList<String> projectsList = FXCollections.observableArrayList(projectNames);
		ObservableList<String> employeesList = FXCollections.observableArrayList(employeeNames);
		taskList = FXCollections.observableArrayList(taskNames);
		projectListView.setItems(projectsList);
		tasksListView.setItems(taskList);
		employeeBox.setItems(employeesList);
		taskEmployeesBox.setItems(employeesList);

		taskBoxList = FXCollections.observableArrayList(taskNames);
		taskBox.setItems(taskBoxList);

		//Add listener to listen for which project selected
		//Displays project summary when project selected from listview, along with lists of employees and tasks
		projectListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
		selectedProjectId = Integer.parseInt(newValue.replaceAll("[^0-9]", ""));
		if (projects.keySet().contains(selectedProjectId)){
			Project selectedProject = projects.get(selectedProjectId);
			String nameText = selectedProject.getName();
			String startDateText = selectedProject.getStartDate().toString();
			String deadlineText = selectedProject.getDeadline().toString();

			nameLbl.setText(nameText);
			startDateLbl.setText(startDateText);
			deadlineLbl.setText(deadlineText);

			selectedProjectEmployees = new ArrayList<String>();
			selectedProjectTasks = new ArrayList<String>();
			HashSet<Integer> projEmps = selectedProject.getEmployees();
			HashSet<Integer> projTasks = selectedProject.getTasks();

			for (Integer e : projEmps){
				selectedProjectEmployees.add(employees.get(e).getName() + " (ID: " + e + ")");
			}

			for (Integer t : projTasks){
				if(!(tasks.get(t).isComplete())){
					selectedProjectTasks.add(tasks.get(t).getName() + " (ID: " + t + ")");
				}
			}

			projEmpsList = FXCollections.observableArrayList(selectedProjectEmployees);
			projTasksList = FXCollections.observableArrayList(selectedProjectTasks);

			projectEmployeesListView.setItems(projEmpsList);
			projectTasksListView.setItems(projTasksList);
		}

	   nameLbl.setVisible(true);
	   startDateLbl.setVisible(true);
	   deadlineLbl.setVisible(true);

		});

		//Adds listener to listen for which task is selected in task manager
		//Displays task-specific information when task selected from listview
		tasksListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			selectedTaskId = Integer.parseInt(newValue.replaceAll("[^0-9]", ""));
			selectedTaskIndex = tasksListView.getSelectionModel().getSelectedIndex();
			if (tasks.keySet().contains(selectedTaskId)){
				Task selectedTask = tasks.get(selectedTaskId);
				taskHoursLbl.setText(Integer.toString(selectedTask.getEstimatedHours()));
				taskProjLbl.setText(Integer.toString(selectedTask.getProjectId()));
				taskEmpsList = new ArrayList<String>();
				HashSet<Integer> selectedTaskEmpIds = selectedTask.getEmployeeIds();

				for (Integer e : selectedTaskEmpIds){
					taskEmpsList.add(employees.get(e).getName() + " (ID: " + e + ")");
				}

				selectedTaskEmployees = FXCollections.observableArrayList(taskEmpsList);
			}

		taskHoursLbl.setVisible(true);
		taskProjLbl.setVisible(true);
		taskEmployeesListView.setItems(selectedTaskEmployees);
		});
	}


	/**
	 * Transitions to employee manager dashboard window
	 * @param event Manage Employees button clicked on dashboard
	 * @throws IOException
	 */
	public void manageEmployeesClicked(ActionEvent event) throws IOException{
		Stage primaryStage =  (Stage) ((Node) event.getSource()).getScene().getWindow();
		primaryStage.setTitle("Manage Employees");

		Parent root = FXMLLoader.load(getClass().getResource("EmployeeDashboard.fxml"));
		Scene scene = new Scene(root);

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * This method leads to the Project Analysis screen when the analysis button is clicked on the dashboard.
	 * @param event analysis button clicked
	 */
	public void analysisClicked(ActionEvent event){
		Stage primaryStage =  (Stage) ((Node) event.getSource()).getScene().getWindow();

		BurndownChart burndown = new BurndownChart();
		Scene scene = burndown.getScene();

		Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        dialog.setScene(scene);
        dialog.show();
	}

	/**
	 * This method leads to the window to add a project when the Add Project button is clicked
	 * @param event Add Project button clicked
	 * @throws IOException
	 */
	public void addProjectClicked(ActionEvent event) throws IOException {
		Stage primaryStage =  (Stage) ((Node) event.getSource()).getScene().getWindow();
		primaryStage.setTitle("Add Project");

		Parent root = FXMLLoader.load(getClass().getResource("AddProjectView.fxml"));
		Scene scene = new Scene(root);

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * This method adds selected task from the ComboBox to the selected project.
	 * Note that each task can only be added to one project and will be removed from options once added.
	 * @param event Add Task button clicked in project manager section
	 */
	public void addTaskToProjectClicked(ActionEvent event){
		int selectedBoxTaskId = Integer.parseInt(taskBox.getValue().toString().replaceAll("[^0-9]", ""));
		Company.getInstance().setProjectForTask(selectedBoxTaskId, selectedProjectId);
		selectedProjectTasks.add(tasks.get(selectedBoxTaskId).getName() + " (ID: " + selectedBoxTaskId + ")");
		//taskBoxList.set(selectedTaskIndex, "Already Assigned Task");
		//taskBox.setItems(taskBoxList);
		projTasksList = FXCollections.observableArrayList(selectedProjectTasks);
		projectTasksListView.setItems(projTasksList);
	}

	/**
	 * This method removes the selected task from the project.
	 * @param event Remove button clicked for selected task in listview for project
	 */
	public void removeTaskClicked(ActionEvent event){
		projectTasksListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			int selectedProjectTaskId = Integer.parseInt(newValue.replaceAll("[^0-9]", ""));
			Company.getInstance().removeProjectFromTask(selectedProjectTaskId, selectedProjectId);
					});
		int selectedProjectTaskIndex = projectTasksListView.getSelectionModel().getSelectedIndex();
		selectedProjectTasks.remove(selectedProjectTaskIndex);
		updateProjTasksListView();
	}

	/**
	 * This method adds the selected employee from the employee ComboBox to the selected project.
	 * @param event Add Employee button clicked
	 */
	public void addEmployeeClicked(ActionEvent event){
		Integer selectedEmployeeId = Integer.parseInt(employeeBox.getValue().toString().replaceAll("[^0-9]", ""));
		Company.getInstance().addEmployeeToProject(selectedEmployeeId, selectedProjectId);
		selectedProjectEmployees.add(employees.get(selectedEmployeeId).getName() + " (ID: " + selectedEmployeeId + ")");
		projEmpsList = FXCollections.observableArrayList(selectedProjectEmployees);
		projectEmployeesListView.setItems(projEmpsList);
	}

	/**
	 * This method removes selected employee from selected project.
	 * NOTE: still needs to remove it from the ArrayList
	 * @param event
	 */
	public void removeEmployeeClicked(ActionEvent event){
		projectEmployeesListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			Integer selectedEmployeeId = Integer.parseInt(newValue.replaceAll("[^0-9]", ""));
			Company.getInstance().removeEmployeeFromProject(selectedEmployeeId, selectedProjectId);
		});
		projEmpsList = FXCollections.observableArrayList(selectedProjectEmployees);
		projectEmployeesListView.setItems(projEmpsList);
	}

	/**
	 * This method opens the window to add a new task when New Task button is clicked.
	 * @param event Add Task button clicked
	 * @throws IOException
	 */
	public void newTaskClicked(ActionEvent event) throws IOException{
		Stage primaryStage =  (Stage) ((Node) event.getSource()).getScene().getWindow();
		primaryStage.setTitle("New Task");

		Parent root = FXMLLoader.load(getClass().getResource("NewTaskView.fxml"));
		Scene scene = new Scene(root);

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * This method removes the employee from being assigned to the selected task.
	 * @param event Remove Employee button clicked in Task Manager
	 */
	public void removeEmpFromTaskClicked(ActionEvent event){
		taskEmployeesListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			Integer selectedEmployeeId = Integer.parseInt(newValue.replaceAll("[^0-9]", ""));
			Company.getInstance().removeEmployeeFromTask(selectedTaskId, selectedEmployeeId);
		});
		updateTaskEmployeesListView();
	}

	/**
	 * This method assigns an employee to a selected task.
	 * @param event Assign button clicked in task manager
	 */
	public void assignEmpToTaskClicked(ActionEvent event){
		Integer selectedEmployeeId = Integer.parseInt(taskEmployeesBox.getValue().toString().replaceAll("[^0-9]", ""));
		Company.getInstance().addEmployeeToTask(selectedTaskId, selectedEmployeeId);
		taskEmpsList.add(employees.get(selectedEmployeeId).getName() + " (ID " + selectedEmployeeId + ")");
		updateTaskEmployeesListView();
	}

	/**
	 * This method completes the task and logs the amount of hours it took.
	 * @param event Complete Task button clicked in task manager
	 */
	public void completeTaskClicked(ActionEvent event){
		int hours = Integer.parseInt(hoursToCompleteTask.getText());
		tasks.get(selectedTaskId).completeTask(hours);
		taskNames.set(selectedTaskIndex, "Completed - ID: " + (selectedTaskIndex+1));
		updateTasksListView();
	}

	/**
	 * This method is used to update the listview for the employeees assigned to a task.
	 */
	public void updateTaskEmployeesListView(){
		selectedTaskEmployees = FXCollections.observableArrayList(taskEmpsList);
		taskEmployeesListView.setItems(selectedTaskEmployees);
	}

	/**
	 * This method is used to update the listview of all the tasks in the task manager.
	 */
	public void updateTasksListView(){
		taskList = FXCollections.observableArrayList(taskNames);
		tasksListView.setItems(taskList);
	}

	/**
	 * This method is used to update the tasks listview to shows the tasks assigned to a project.
	 */
	public void updateProjTasksListView(){
		projTasksList = FXCollections.observableArrayList(selectedProjectTasks);
		projectTasksListView.setItems(projTasksList);
	}

	/**
	 * This method opens up the setup window to change the company name when clicked.
	 * @param event Company Setup button clicked
	 * @throws IOException
	 */
	public void companySetupClicked(ActionEvent event) throws IOException{
		Stage primaryStage =  (Stage) ((Node) event.getSource()).getScene().getWindow();
		primaryStage.setTitle("Company Setup");

		Parent root = FXMLLoader.load(getClass().getResource("AddCompanyView.fxml"));
		Scene scene = new Scene(root);

		primaryStage.setScene(scene);
		primaryStage.show();
	}


}
