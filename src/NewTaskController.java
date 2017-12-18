import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class NewTaskController {
	@FXML
	private TextField taskName;

	@FXML
	private TextField taskHours;

	/**
	 * This method creates the task with name and estimated hours if the fields are entered, else an alert box shows.
	 * @param event button clicked to create task
	 * @throws IOException
	 */
	public void createTaskClicked(ActionEvent event) throws IOException{

		if (taskName.getText().equals("") || taskHours.getText().equals("")){
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Complete required fields");
			alert.setHeaderText("Please enter task name and estimated hours.");
			alert.showAndWait();
		} else {
			String name = taskName.getText();
			Integer hours = Integer.parseInt(taskHours.getText());
			Company.getInstance().newTask(hours, name);

			Stage primaryStage =  (Stage) ((Node) event.getSource()).getScene().getWindow();
			primaryStage.setTitle("Syme");

			Parent root = FXMLLoader.load(getClass().getResource("DashboardView2.fxml"));
			Scene scene = new Scene(root);

			primaryStage.setScene(scene);
			primaryStage.show();
		}
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
