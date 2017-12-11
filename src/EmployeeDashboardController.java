import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.sun.xml.internal.ws.org.objectweb.asm.Label;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * This class controls EmployeeDashboard.fxml. 
 * It sets the initial state and defines behavior for clicking the buttons
 * @author jennifersaouaf
 *
 */
public class EmployeeDashboardController {
	private Company company = Company.getInstance();
	private LinkedHashMap<Integer, Employee> employees;
	private ArrayList<String> employeeNames = new ArrayList<String>();
//	private ArrayList<String> employeeIds = new ArrayList<String>();

	@FXML
	private ListView<String> employeeListView;
//	@FXML
//	private ListView<String> employeeId;
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
	
	/**
	 * This method sets the initial state with the employee names displayed
	 */
//	@FXML
//	private void initialize() {
//		company.newEmployee("Jane");
//		company.newEmployee("Joe");
//		employees = Company.getInstance().getEmployeeIdMap();
//		for (Integer i : employees.keySet()){
////			employeeIds.add(Integer.toString(i));
//			employeeNames.add(employees.get(i).getName());
//		}
//		ObservableList<String> names = FXCollections.observableArrayList(employeeNames);
////		ObservableList<String> ids = FXCollections.observableArrayList(employeeIds);
//		employeeListView.setItems(names);
////		employeeId.setItems(ids);
//		
//		//how to display info based on clicking?
//	}
	
	/**
	 * When the Add Employee button is clicked, an employee is added to the company 
	 * and the displayed list of employees is updated
	 * @param event add employee button clicked
	 */
	public void addEmployeeClicked(ActionEvent event) {
		company.newEmployee(employeeName.getText());
		employeeNames.add(employeeName.getText());
		ObservableList<String> names = FXCollections.observableArrayList(employeeNames);
		employeeListView.setItems(names);
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
