import java.io.IOException;
import java.util.ArrayList;
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

/*
 * To do:
 * 1. Show the tasks and employees associated with each project
 * 2. Add display of task hours, employees on task, option to remove task when clicked
 * 3. Figure out what to allow the user to input in terms of dates, project names, etc.
 * 4. Implement company serialization
 */

public class DashboardController2 {
	private LinkedHashMap<Integer, Project> projects;
	private LinkedHashMap<Integer, Task> tasks;
	private LinkedHashMap<Integer, Employee> employees;
	private Integer selectedProjectId;

	@FXML
	private ListView<String> projectListView;

	@FXML
	private ListView<String> tasksListView;

	@FXML
	private Label nameLbl;
	@FXML
	private Label startDateLbl;
	@FXML
	private Label targetEndDateLbl;
	@FXML
	private Label deadlineLbl;
	@FXML
	private Label taskHoursLbl;
	@FXML
	private ComboBox employeeBox;
	@FXML
	private ComboBox taskBox;
	@FXML
	private Button newTask;

	@FXML
	private void initialize(){
//		ArrayList<String> projectIds = new ArrayList<String>();
//		ArrayList<String> taskIds = new ArrayList<String>();
		//Get tasks and projects maps
		tasks = Company.getInstance().getTaskIdMap();
		projects = Company.getInstance().getProjectIdMap();
		employees = Company.getInstance().getEmployeeIdMap();
//		for (Integer i : projects.keySet()){
//			projectIds.add(Integer.toString(i));
//		}
		//Create the listviews observable lists of projects names and task names
		ArrayList<String> projectNames = new ArrayList<String>();
		ArrayList<String> taskNames = new ArrayList<String>();
		ArrayList<String> employeeNames = new ArrayList<String>();

		for (Integer i: projects.keySet()){
			projectNames.add(projects.get(i).getName() + " (ID: " + i + ")");
		}

		for (Integer i: tasks.keySet()){
			taskNames.add(tasks.get(i).getName() + " (ID: " + i + ")");
		}

		for (Integer i: employees.keySet()){
			employeeNames.add(employees.get(i).getName() + " (ID: " + i + ")");
		}

		ObservableList<String> projectsList = FXCollections.observableArrayList(projectNames);
		ObservableList<String> employeesList = FXCollections.observableArrayList(employeeNames);
		ObservableList<String> taskList = FXCollections.observableArrayList(taskNames);
		projectListView.setItems(projectsList);
		tasksListView.setItems(taskList);
		employeeBox.setItems(employeesList);
		taskBox.setItems(taskList);

		//Displays project summary when project selected from listview
		projectListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
		selectedProjectId = Integer.parseInt(newValue.replaceAll("[^0-9]", ""));
		if (projects.keySet().contains(selectedProjectId)){
			Project selectedProject = projects.get(selectedProjectId);
			String nameText = selectedProject.getName();
			String startDateText = selectedProject.getStartDate().toString();
			String targetEndDateText = selectedProject.getTargetEndDate().toString();
			String deadlineText = selectedProject.getDeadline().toString();

			nameLbl.setText(nameText);
			startDateLbl.setText(startDateText);
			targetEndDateLbl.setText(targetEndDateText);
			deadlineLbl.setText(deadlineText);
		}

	   nameLbl.setVisible(true);
	   startDateLbl.setVisible(true);
	   targetEndDateLbl.setVisible(true);
	   deadlineLbl.setVisible(true);

		});

		tasksListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			Integer selectedTaskId = Integer.parseInt(newValue.replaceAll("[^0-9]", ""));
			if (tasks.keySet().contains(selectedTaskId)){
				Task selectedTask = tasks.get(selectedTaskId);
				taskHoursLbl.setText(Integer.toString(selectedTask.getEstimatedHours()));
			}
		taskHoursLbl.setVisible(true);
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

	public void analysisClicked(ActionEvent event){

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

	public void addTaskClicked(ActionEvent event){
		Integer selectedTaskId = Integer.parseInt(taskBox.getValue().toString().replaceAll("[^0-9]", ""));
		Company.getInstance().setProjectForTask(selectedTaskId, selectedProjectId);
	}

	public void addEmployeeClicked(ActionEvent event){
		Integer selectedEmployeeId = Integer.parseInt(employeeBox.getValue().toString().replaceAll("[^0-9]", ""));
		Company.getInstance().addEmployeeToProject(selectedEmployeeId, selectedProjectId);
	}

	public void newTaskClicked(ActionEvent event) throws IOException{
		Stage primaryStage =  (Stage) ((Node) event.getSource()).getScene().getWindow();
		primaryStage.setTitle("New Task");

		Parent root = FXMLLoader.load(getClass().getResource("NewTaskView.fxml"));
		Scene scene = new Scene(root);

		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
