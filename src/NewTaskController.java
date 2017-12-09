import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class NewTaskController {
	@FXML
	private TextField taskName;

	@FXML
	private TextField taskHours;

	public void createTaskClicked(ActionEvent event){
		String name = taskName.getText();
		Integer hours = Integer.parseInt(taskHours.getText());
		Company.getInstance().newTask(hours, name);
	}
}
