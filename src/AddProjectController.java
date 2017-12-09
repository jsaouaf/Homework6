import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddProjectController {

	@FXML
	private TextField projectName;

	@FXML
	private TextField startDate;

	@FXML
	private TextField targetEndDate;

	@FXML
	private TextField deadline;

//Need to handle changing the start/end/deadline dates in this method still if user chooses to enter them
	public void addProjectClicked(ActionEvent event){

		Company.getInstance().newProject(projectName.getText());

//		if(!startDate.getText().isEmpty()){
//
//		}
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
