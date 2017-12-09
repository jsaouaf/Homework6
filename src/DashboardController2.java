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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DashboardController2 {
	private LinkedHashMap<Integer, Project> projects;
	private String selectedProjectId;

	@FXML
	private ListView<String> projectListView;

	@FXML
	private Label nameLbl;
	@FXML
	private Label startDateLbl;
	@FXML
	private Label targetEndDateLbl;
	@FXML
	private Label deadlineLbl;
	@FXML
	private TextField employeeIdToAdd;
	@FXML
	private TextField taskIdToAdd;
	@FXML
	private Button newTask;

	@FXML
	private void initialize(){
		ArrayList<String> projectIds = new ArrayList<String>();
		//testing adding a project to see if it will display
		projectIds.add("9120983");
		projects = Company.getInstance().getProjectIdMap();
		for (Integer i : projects.keySet()){
			projectIds.add(Integer.toString(i));
		}
		ObservableList<String> names = FXCollections.observableArrayList(projectIds);
		projectListView.setItems(names);

		projectListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			//int item = projectListView.getSelectionModel().getSelectedIndex();
			selectedProjectId = newValue;
			if (projects.keySet().contains(selectedProjectId)){
				Project selectedProject = projects.get(Integer.parseInt(newValue));
				String nameText = selectedProject.getName();
				String startDateText = selectedProject.getStartDate().toString();
				String targetEndDateText = selectedProject.getTargetEndDate().toString();
				String deadlineText = selectedProject.getDeadline().toString();

				nameLbl.setText(nameText);
				startDateLbl.setText(startDateText);
				targetEndDateLbl.setText(targetEndDateText);
				deadlineLbl.setText(deadlineText);
			}

		   nameLbl.setVisible(true);
		   startDateLbl.setVisible(true);
		   targetEndDateLbl.setVisible(true);
		   deadlineLbl.setVisible(true);
		});
	}

	public void manageEmployeesClicked(ActionEvent event){

	}

	public void analysisClicked(ActionEvent event){

	}

	public void addProjectClicked(ActionEvent event) throws IOException {
		Stage primaryStage =  (Stage) ((Node) event.getSource()).getScene().getWindow();
		primaryStage.setTitle("Add Project");

		Parent root = FXMLLoader.load(getClass().getResource("AddProjectView.fxml"));
		Scene scene = new Scene(root);

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public void addTaskClicked(ActionEvent event){
		Integer taskId = Integer.parseInt(taskIdToAdd.getText());
		Company.getInstance().setProjectForTask(taskId, Integer.parseInt(selectedProjectId));
	}

	public void addEmployeeClicked(ActionEvent event){
		Integer employeeId = Integer.parseInt(employeeIdToAdd.getText());
		Company.getInstance().addEmployeeToProject(employeeId, Integer.parseInt(selectedProjectId));
	}

	public void newTaskClicked(ActionEvent event) throws IOException{
		Stage primaryStage = new Stage();
		primaryStage.setTitle("New Task");
		Parent root = FXMLLoader.load(getClass().getResource("NewTaskView.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		
		primaryStage.initModality(Modality.APPLICATION_MODAL);
		primaryStage.initOwner(newTask.getScene().getWindow());
		primaryStage.showAndWait();
	}
}
