import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewTaskController {
	@FXML
	private TextField taskName;

	@FXML
	private TextField taskHours;

	public void createTaskClicked(ActionEvent event) throws IOException{
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
