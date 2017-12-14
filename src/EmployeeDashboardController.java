import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * This class controls EmployeeDashboard.fxml. It defines behavior for clicking the buttons
 * @author jennifersaouaf
 * TODO: Display projects and tasks when an employee id is clicked
 */
public class EmployeeDashboardController {
	private ArrayList<String> employeeNames = new ArrayList<String>();
	private ArrayList<String> employeeIds = new ArrayList<String>();
	private LinkedHashMap<Integer, Employee> employees;
	private Company company = Company.getInstance();
	
	@FXML
	private ListView<String> employeeListView;
	@FXML
	private ListView<String> idListView;
	@FXML
	private TextField employeeName;
	@FXML
	private Label name;
	@FXML
	private Label assignedHours;
	@FXML
	private Label completedHours;
	@FXML
	private Label efficiency;
	@FXML
	private ListView<String> projectListView;
	@FXML
	private ListView<String> taskListView;
	
	/**
	 * This method sets the initial state with the employee names displayed
	 * When an employee ID is clicked, it displays their name, assigned hours, completed hours, and efficiency.
	 */
	@FXML
	private void initialize() {
		employees = Company.getInstance().getEmployeeIdMap();
		for (Integer i : employees.keySet()){
			employeeIds.add(Integer.toString(i));
			employeeNames.add(employees.get(i).getName());
		}
		ObservableList<String> names = FXCollections.observableArrayList(employeeNames);
		ObservableList<String> ids = FXCollections.observableArrayList(employeeIds);
		employeeListView.setItems(names);
		idListView.setItems(ids);
		
		// when an ID is clicked, display data for that employee
		idListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			Employee employee = employees.get(Integer.parseInt(newValue));
			EmployeeAnalysis ea = new EmployeeAnalysis(employee);
			String nameText = employee.getName();
			String assignedHoursText = Integer.toString(ea.getAssignedHours());
			String completedHoursText = Integer.toString(ea.getCompletedHours());
			String efficiencyText = Double.toString(ea.getEfficiency());

			name.setText(nameText);
			assignedHours.setText(assignedHoursText);
			completedHours.setText(completedHoursText);
			efficiency.setText(efficiencyText);

			name.setVisible(true);
			assignedHours.setVisible(true);
			completedHours.setVisible(true);
			efficiency.setVisible(true);
			
			ArrayList<String> projects = new ArrayList<>();
			ArrayList<String> tasks = new ArrayList<>();
			LinkedHashMap<Integer, Project> projIdMap = company.getProjectIdMap();
			LinkedHashMap<Integer, Task> taskIdMap = company.getTaskIdMap();
			
			for(int projId: employee.getProjects()) {
				projects.add(projIdMap.get(projId).getName() + " (ID: " + projId + ")");
			}
			for(int taskId: employee.getTasks()) {
				tasks.add(taskIdMap.get(taskId).getName() + " (ID: " + taskId + ")");
			}
			
			ObservableList<String> projectList = FXCollections.observableArrayList(projects);
			ObservableList<String> taskList = FXCollections.observableArrayList(tasks);
			projectListView.setItems(projectList);
			taskListView.setItems(taskList);
			
		});
	}
	
	/**
	 * When the Add Employee button is clicked, an employee is added to the company 
	 * and the displayed list of employees is updated
	 * @param event add employee button clicked
	 */
	public void addEmployeeClicked(ActionEvent event) {
		company.newEmployee(employeeName.getText());
		employeeNames.add(employeeName.getText());
		
		employees = Company.getInstance().getEmployeeIdMap();
		for (Integer i : employees.keySet()){
			if(!employeeIds.contains(Integer.toString(i)))
				employeeIds.add(Integer.toString(i));
		}
		ObservableList<String> names = FXCollections.observableArrayList(employeeNames);
		ObservableList<String> ids = FXCollections.observableArrayList(employeeIds);
		employeeListView.setItems(names);
		idListView.setItems(ids);
	}

	/**
	 * When the dashboard button is clicked, the Dashboard view is loaded
	 * @param event dashboard button clicked
	 * @throws IOException
	 */
	public void dashboardClicked(ActionEvent event) throws IOException{
		Stage primaryStage =  (Stage) ((Node) event.getSource()).getScene().getWindow();
		primaryStage.setTitle("Syme");

		Parent root = FXMLLoader.load(getClass().getResource("DashboardView2.fxml"));
		Scene scene = new Scene(root);

		primaryStage.setScene(scene);
		primaryStage.show();
	}

}