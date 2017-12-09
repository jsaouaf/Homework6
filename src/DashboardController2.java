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
import javafx.stage.Stage;

public class DashboardController2 {
	private LinkedHashMap<Integer, Project> projects;

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
	private void initialize(){
		ArrayList<String> projectIds = new ArrayList<String>();
		projectIds.add("9120983");
		projects = Company.getInstance().getProjects();
		for (Integer i : projects.keySet()){
			projectIds.add(Integer.toString(i));
		}
		ObservableList<String> names = FXCollections.observableArrayList(projectIds);
		projectListView.setItems(names);

		projectListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			//int item = projectListView.getSelectionModel().getSelectedIndex();
			String projectId = newValue;
			if (projects.keySet().contains(projectId)){
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

}
