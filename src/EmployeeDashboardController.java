import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * This class controls EmployeeDashboard.fxml. It defines behavior for clicking the buttons
 * @author jennifersaouaf
 *
 */
public class EmployeeDashboardController {

	@FXML
	private TextField employeeName;
	
	private Company company = Company.getInstance();
	
	public void addEmployeeClicked(ActionEvent event) {
		company.newEmployee(employeeName.getText());
	}

	public void dashboardClicked(ActionEvent event) throws IOException{
		Stage primaryStage =  (Stage) ((Node) event.getSource()).getScene().getWindow();
		primaryStage.setTitle("Syme");

		Parent root = FXMLLoader.load(getClass().getResource("DashboardView2.fxml"));
		Scene scene = new Scene(root);

		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
