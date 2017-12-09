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
 * This is the controller for the scene that creates the new company.
 * @author cgui1
 *
 */
public class AddCompanyController {
	@FXML
	private TextField companyName;
	private String company;

	public void createCompanyClicked(ActionEvent event) throws IOException{
		company = companyName.getText();
		Company.getInstance().setName(company);
		Stage primaryStage =  (Stage) ((Node) event.getSource()).getScene().getWindow();
		primaryStage.setTitle("Syme");

		Parent root = FXMLLoader.load(getClass().getResource("DashboardView2.fxml"));
		Scene scene = new Scene(root);

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * This method gets the company name from the user's text input.
	 * @return
	 */
	public String getCompanyName(){
		return company;
	}

}
