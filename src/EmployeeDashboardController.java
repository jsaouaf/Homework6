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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * This class controls EmployeeDashboard.fxml. It defines behavior for clicking the buttons
 * @author jennifersaouaf
 *
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
	
	/**
	 * This method sets the initial state with the employee names displayed
	 */
	@FXML
	private void initialize() {
		company.newEmployee("Jane");
		company.newEmployee("Joe");
		employees = Company.getInstance().getEmployeeIdMap();
		for (Integer i : employees.keySet()){
			employeeIds.add(Integer.toString(i));
			employeeNames.add(employees.get(i).getName());
		}
		ObservableList<String> names = FXCollections.observableArrayList(employeeNames);
		ObservableList<String> ids = FXCollections.observableArrayList(employeeIds);
		employeeListView.setItems(names);
		idListView.setItems(ids);
		
		//how to display info based on clicking?
	}
	
	/**
	 * When the Add Employee button is clicked, an employee is added to the company 
	 * and the displayed list of employees is updated
	 * @param event add employee button clicked
	 */
	public void addEmployeeClicked(ActionEvent event) {
		company.newEmployee(employeeName.getText());
		employeeIds.clear();
		employeeNames.clear();
		employees = Company.getInstance().getEmployeeIdMap();
		for (Integer i : employees.keySet()){
			employeeIds.add(Integer.toString(i));
			employeeNames.add(employees.get(i).getName());
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