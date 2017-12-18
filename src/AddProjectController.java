import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddProjectController {

	@FXML
	private TextField projectName;

	@FXML
	private DatePicker targetEndDate;

	@FXML
	private DatePicker deadline;

	public void addProjectClicked(ActionEvent event) throws IOException{

		Company.getInstance().newProject(projectName.getText(), deadline.getValue());

		Stage primaryStage =  (Stage) ((Node) event.getSource()).getScene().getWindow();
		primaryStage.setTitle("Syme");

		Parent root = FXMLLoader.load(getClass().getResource("DashboardView2.fxml"));
		Scene scene = new Scene(root);

		primaryStage.setScene(scene);
		primaryStage.show();
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
