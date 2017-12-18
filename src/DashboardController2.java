import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.TreeMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * This class is the controller for the DashBoardView interface. It is the main screen the user interacts with.
 * @author cgui1
 *
 */
public class DashboardController2 {
	//Instance variables to be used in the class
	private LinkedHashMap<Integer, Project> projects;
	private LinkedHashMap<Integer, Task> tasks;
	private LinkedHashMap<Integer, Employee> employees;
	private Integer selectedProjectId;
	private Integer selectedTaskId;
	private Integer selectedTaskIndex;

	//Instance variables for listview items
	private ObservableList<String> projEmpsList;
	private ArrayList<String> selectedProjectEmployees;
	private ObservableList<String> projTasksList;
	private ArrayList<String> selectedProjectTasks;
	private ObservableList<String> selectedTaskEmployees;
	private ArrayList<String> taskEmpsList;
	private ObservableList<String> taskList;
	private ArrayList<String> taskNames;
	private ObservableList<String> taskBoxList;


	//FXML instance variables, used in the view interface fxml
	@FXML
	private Label companyNameLbl;
	@FXML
	private ListView<String> projectListView;
	@FXML
	private ListView<String> tasksListView;
	@FXML
	private ListView<String> projectTasksListView;
	@FXML
	private ListView<String> projectEmployeesListView;
	@FXML
	private ListView<String> taskEmployeesListView;

	//Project View Controls Fields
	@FXML
	private Label nameLbl;
	@FXML
	private Label startDateLbl;
	@FXML
	private Label deadlineLbl;

	//Tasks View Controls Fields
	@FXML
	private Label taskHoursLbl;
	@FXML
	private Label taskProjLbl;
	@FXML
	private ComboBox employeeBox;
	@FXML
	private ComboBox taskBox;
	@FXML
	private ComboBox taskEmployeesBox;
	@FXML
	private Button newTask;
	@FXML
	private TextField hoursToCompleteTask;
	@FXML
	private Button completeTask;

	/**
	 * This method initializes the class.
	 */
	@FXML
	private void initialize(){
		companyNameLbl.setText(Company.getInstance().getName());
		companyNameLbl.setVisible(true);

		//Initializes tasks and projects maps
		tasks = Company.getInstance().getTaskIdMap();
		projects = Company.getInstance().getProjectIdMap();
		employees = Company.getInstance().getEmployeeIdMap();

		//Create the listviews observable lists of projects names and task names
		ArrayList<String> projectNames = new ArrayList<String>();
		taskNames = new ArrayList<String>();
		ArrayList<String> employeeNames = new ArrayList<String>();

		for (Integer i: projects.keySet()){
			projectNames.add(projects.get(i).getName() + " (ID: " + i + ")");
		}

		for (Integer i: tasks.keySet()){
			if(tasks.get(i).isComplete()){
				taskNames.add("COMPLETED: " + tasks.get(i).getName() + " (ID: " + i + ")");
			} else {
				taskNames.add(tasks.get(i).getName() + " (ID: " + i + ")");
			}
		}

		for (Integer i: employees.keySet()){
			employeeNames.add(employees.get(i).getName() + " (ID: " + i + ")");
		}

		ObservableList<String> projectsList = FXCollections.observableArrayList(projectNames);
		ObservableList<String> employeesList = FXCollections.observableArrayList(employeeNames);
		taskList = FXCollections.observableArrayList(taskNames);
		projectListView.setItems(projectsList);
		tasksListView.setItems(taskList);
		employeeBox.setItems(employeesList);
		taskEmployeesBox.setItems(employeesList);

		taskBoxList = FXCollections.observableArrayList(taskNames);
		taskBox.setItems(taskBoxList);

		//Add listener to listen for which project selected
		//Displays project summary when project selected from listview, along with lists of employees and tasks
		projectListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
		selectedProjectId = Integer.parseInt(newValue.replaceAll("[^0-9]", ""));
		if (projects.keySet().contains(selectedProjectId)){
			Project selectedProject = projects.get(selectedProjectId);
			String nameText = selectedProject.getName();
			String startDateText = selectedProject.getStartDate().toString();
			String deadlineText = selectedProject.getDeadline().toString();

			nameLbl.setText(nameText);
			startDateLbl.setText(startDateText);
			deadlineLbl.setText(deadlineText);

			selectedProjectEmployees = new ArrayList<String>();
			selectedProjectTasks = new ArrayList<String>();
			HashSet<Integer> projEmps = selectedProject.getEmployees();
			HashSet<Integer> projTasks = selectedProject.getTasks();

			for (Integer e : projEmps){
				selectedProjectEmployees.add(employees.get(e).getName() + " (ID: " + e + ")");
			}

			for (Integer t : projTasks){
				if(!(tasks.get(t).isComplete())){
					selectedProjectTasks.add(tasks.get(t).getName() + " (ID: " + t + ")");
				}
			}

			projEmpsList = FXCollections.observableArrayList(selectedProjectEmployees);
			projTasksList = FXCollections.observableArrayList(selectedProjectTasks);

			projectEmployeesListView.setItems(projEmpsList);
			projectTasksListView.setItems(projTasksList);
		}

	   nameLbl.setVisible(true);
	   startDateLbl.setVisible(true);
	   deadlineLbl.setVisible(true);

		});

		//Adds listener to listen for which task is selected in task manager
		//Displays task-specific information when task selected from listview
		tasksListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			selectedTaskId = Integer.parseInt(newValue.replaceAll("[^0-9]", ""));
			selectedTaskIndex = tasksListView.getSelectionModel().getSelectedIndex();
			if (tasks.keySet().contains(selectedTaskId)){
				Task selectedTask = tasks.get(selectedTaskId);
				taskHoursLbl.setText(Integer.toString(selectedTask.getEstimatedHours()));
				taskProjLbl.setText(Integer.toString(selectedTask.getProjectId()));
				taskEmpsList = new ArrayList<String>();
				HashSet<Integer> selectedTaskEmpIds = selectedTask.getEmployeeIds();

				for (Integer e : selectedTaskEmpIds){
					taskEmpsList.add(employees.get(e).getName() + " (ID: " + e + ")");
				}

				selectedTaskEmployees = FXCollections.observableArrayList(taskEmpsList);
			}

		taskHoursLbl.setVisible(true);
		taskProjLbl.setVisible(true);
		taskEmployeesListView.setItems(selectedTaskEmployees);
		});
	}


	/**
	 * Transitions to employee manager dashboard window
	 * @param event Manage Employees button clicked on dashboard
	 * @throws IOException
	 */
	public void manageEmployeesClicked(ActionEvent event) throws IOException{
		Stage primaryStage =  (Stage) ((Node) event.getSource()).getScene().getWindow();
		primaryStage.setTitle("Manage Employees");

		Parent root = FXMLLoader.load(getClass().getResource("EmployeeDashboard.fxml"));
		Scene scene = new Scene(root);

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * This method leads to the Project Analysis screen when the analysis button is clicked on the dashboard.
	 * @param event analysis button clicked
	 */
	public void analysisClicked(ActionEvent event){
		// Update task hours log
		Company.getInstance().updateTaskHoursLog();

		// display burndown chart
		Stage primaryStage =  (Stage) ((Node) event.getSource()).getScene().getWindow();
		primaryStage.setTitle("Project Analysis");

        Scene scene = burndownChart(selectedProjectId);

		Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        dialog.setScene(scene);
        dialog.show();
	}

	/**
	 * This method leads to the window to add a project when the Add Project button is clicked
	 * @param event Add Project button clicked
	 * @throws IOException
	 */
	public void addProjectClicked(ActionEvent event) throws IOException {
		Stage primaryStage =  (Stage) ((Node) event.getSource()).getScene().getWindow();
		primaryStage.setTitle("Add Project");

		Parent root = FXMLLoader.load(getClass().getResource("AddProjectView.fxml"));
		Scene scene = new Scene(root);

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * This method adds selected task from the ComboBox to the selected project.
	 * Note that each task can only be added to one project and will be removed from options once added.
	 * @param event Add Task button clicked in project manager section
	 */
	public void addTaskToProjectClicked(ActionEvent event){
		int selectedBoxTaskId = Integer.parseInt(taskBox.getValue().toString().replaceAll("[^0-9]", ""));
		Company.getInstance().setProjectForTask(selectedBoxTaskId, selectedProjectId);
		selectedProjectTasks.add(tasks.get(selectedBoxTaskId).getName() + " (ID: " + selectedBoxTaskId + ")");
		//taskBoxList.set(selectedTaskIndex, "Already Assigned Task");
		//taskBox.setItems(taskBoxList);
		projTasksList = FXCollections.observableArrayList(selectedProjectTasks);
		projectTasksListView.setItems(projTasksList);
	}

	/**
	 * This method removes the selected task from the project.
	 * @param event Remove button clicked for selected task in listview for project
	 */
	public void removeTaskClicked(ActionEvent event){
		projectTasksListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			Integer selectedProjectTaskId = Integer.parseInt(newValue.replaceAll("[^0-9]", ""));
			Company.getInstance().removeProjectFromTask(selectedProjectTaskId, selectedProjectId);
					});
		int selectedProjectTaskIndex = projectTasksListView.getSelectionModel().getSelectedIndex();
		selectedProjectTasks.remove(selectedProjectTaskIndex);
		updateProjTasksListView();
	}

	/**
	 * This method adds the selected employee from the employee ComboBox to the selected project.
	 * @param event Add Employee button clicked
	 */
	public void addEmployeeClicked(ActionEvent event){
		Integer selectedEmployeeId = Integer.parseInt(employeeBox.getValue().toString().replaceAll("[^0-9]", ""));
		Company.getInstance().addEmployeeToProject(selectedEmployeeId, selectedProjectId);
		selectedProjectEmployees.add(employees.get(selectedEmployeeId).getName() + " (ID: " + selectedEmployeeId + ")");
		projEmpsList = FXCollections.observableArrayList(selectedProjectEmployees);
		projectEmployeesListView.setItems(projEmpsList);
	}

	/**
	 * This method removes selected employee from selected project.
	 * NOTE: still needs to remove it from the ArrayList
	 * @param event
	 */
	public void removeEmployeeClicked(ActionEvent event){
		projectEmployeesListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			Integer selectedEmployeeId = Integer.parseInt(newValue.replaceAll("[^0-9]", ""));
			Company.getInstance().removeEmployeeFromProject(selectedEmployeeId, selectedProjectId);
		});
		int selectedProjectEmployeeIndex = projectEmployeesListView.getSelectionModel().getSelectedIndex();
		selectedProjectEmployees.remove(selectedProjectEmployeeIndex);
		projEmpsList = FXCollections.observableArrayList(selectedProjectEmployees);
		projectEmployeesListView.setItems(projEmpsList);
	}

	/**
	 * This method opens the window to add a new task when New Task button is clicked.
	 * @param event Add Task button clicked
	 * @throws IOException
	 */
	public void newTaskClicked(ActionEvent event) throws IOException{
		Stage primaryStage =  (Stage) ((Node) event.getSource()).getScene().getWindow();
		primaryStage.setTitle("New Task");

		Parent root = FXMLLoader.load(getClass().getResource("NewTaskView.fxml"));
		Scene scene = new Scene(root);

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * This method removes the employee from being assigned to the selected task.
	 * @param event Remove Employee button clicked in Task Manager
	 */
	public void removeEmpFromTaskClicked(ActionEvent event){
		taskEmployeesListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			Integer selectedEmployeeId = Integer.parseInt(newValue.replaceAll("[^0-9]", ""));
			Company.getInstance().removeEmployeeFromTask(selectedTaskId, selectedEmployeeId);
		});
		updateTaskEmployeesListView();
	}

	/**
	 * This method assigns an employee to a selected task.
	 * @param event Assign button clicked in task manager
	 */
	public void assignEmpToTaskClicked(ActionEvent event){
		Integer selectedEmployeeId = Integer.parseInt(taskEmployeesBox.getValue().toString().replaceAll("[^0-9]", ""));
		Company.getInstance().addEmployeeToTask(selectedTaskId, selectedEmployeeId);
		taskEmpsList.add(employees.get(selectedEmployeeId).getName() + " (ID " + selectedEmployeeId + ")");
		updateTaskEmployeesListView();
	}

	/**
	 * This method completes the task and logs the amount of hours it took.
	 * @param event Complete Task button clicked in task manager
	 */
	public void completeTaskClicked(ActionEvent event){
		int hours = Integer.parseInt(hoursToCompleteTask.getText());
		tasks.get(selectedTaskId).completeTask(hours);
		taskNames.set(selectedTaskIndex, "Completed - ID: " + (selectedTaskIndex+1));
		updateTasksListView();
	}

	/**
	 * This method is used to update the listview for the employeees assigned to a task.
	 */
	public void updateTaskEmployeesListView(){
		selectedTaskEmployees = FXCollections.observableArrayList(taskEmpsList);
		taskEmployeesListView.setItems(selectedTaskEmployees);
	}

	/**
	 * This method is used to update the listview of all the tasks in the task manager.
	 */
	public void updateTasksListView(){
		taskList = FXCollections.observableArrayList(taskNames);
		tasksListView.setItems(taskList);
	}

	/**
	 * This method is used to update the tasks listview to shows the tasks assigned to a project.
	 */
	public void updateProjTasksListView(){
		projTasksList = FXCollections.observableArrayList(selectedProjectTasks);
		projectTasksListView.setItems(projTasksList);
	}

	/**
	 * This method opens up the setup window to change the company name when clicked.
	 * @param event Company Setup button clicked
	 * @throws IOException
	 */
	public void companySetupClicked(ActionEvent event) throws IOException{
		Stage primaryStage =  (Stage) ((Node) event.getSource()).getScene().getWindow();
		primaryStage.setTitle("Company Setup");

		Parent root = FXMLLoader.load(getClass().getResource("AddCompanyView.fxml"));
		Scene scene = new Scene(root);

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public Scene burndownChart(int projectId) {
		Scene scene;
		Project project = projects.get(projectId);
		TreeMap<LocalDate, Integer> taskHoursLog = project.getTaskHoursLog();
		LocalDate deadline = project.getDeadline();

		// get the data ready for plotting
		TreeMap<LocalDate, Integer> taskHoursToPlot = new TreeMap<>(taskHoursLog);

		// calculate difference between first day and deadline
		// this number will tell us where to plot the deadline in the graph
		int projectDuration = 0;
		LocalDate date = taskHoursToPlot.firstKey();
		while (date.isBefore(deadline)) {
			date = date.plusDays(1);
			projectDuration++;
		}

		// fill in the gaps between last entry in task hours log and deadline for plotting
		if (deadline.isAfter(taskHoursToPlot.lastKey())){
			while (deadline.isAfter(taskHoursToPlot.lastKey())) {
				taskHoursToPlot.put(taskHoursToPlot.lastKey().plusDays(1), 0);
			}
		}

		// create x-axis. the same x-axis will be used for both graphs to ensure match.
		ArrayList<String> xAxisDates = new ArrayList<>();
		for (LocalDate d : taskHoursToPlot.keySet()) {
			xAxisDates.add(d.toString());
		}

		// create y-axis for bar chart
		ArrayList<Integer> yAxisHours = new ArrayList<>();
		for (Integer h : taskHoursToPlot.values()) {
			yAxisHours.add(h);
		}

		// create y-axis for deadline chart
		ArrayList<Double> yAxisDeadline = new ArrayList<>();
		Double initialHours = (double) yAxisHours.get(0);
		yAxisDeadline.add(initialHours);

		for (int i = 1; i < projectDuration; i++) {
			yAxisDeadline.add(initialHours - i * initialHours / projectDuration);
		}

		// if project overdue, add additional deadline y-axis values after deadline
		while (yAxisDeadline.size() < yAxisHours.size()) {
			yAxisDeadline.add(0.0);
		}

		// x-axis and y-axis for both charts:
		final CategoryAxis xAxis = new CategoryAxis();
		xAxis.setLabel("Date");
		final NumberAxis yAxis = new NumberAxis();
		yAxis.setLabel("Task Hours Left");

		// first chart - task hours remaining bar chart
		final BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
		barChart.setTitle(" "); // blank title so that two charts align
		barChart.setLegendVisible(false);
		barChart.setAnimated(false);
		barChart.getStylesheets().addAll(getClass().getResource("MainStyle.css").toExternalForm());
		XYChart.Series<String, Number> series1 = new XYChart.Series<>();
		for (int i = 0; i < xAxisDates.size(); i++) {
			series1.getData().add(new XYChart.Data<>(xAxisDates.get(i), yAxisHours.get(i)));
		}
		barChart.getData().add(series1);

		// second chart - deadline line chart
		final LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
		lineChart.setTitle(project.getName() + " Burndown Chart");
		lineChart.setLegendVisible(false);
		lineChart.setAnimated(false);
		lineChart.setCreateSymbols(false);
		lineChart.setAlternativeRowFillVisible(false);
		lineChart.setAlternativeColumnFillVisible(false);
		lineChart.setHorizontalGridLinesVisible(false);
		lineChart.setVerticalGridLinesVisible(false);
		lineChart.getXAxis().setVisible(false);
		lineChart.getYAxis().setVisible(false);
		lineChart.getStylesheets().addAll(getClass().getResource("MainStyle.css").toExternalForm());
		XYChart.Series<String, Number> series2 = new XYChart.Series<>();

		for (int i = 0; i < xAxisDates.size(); i++) {
			series2.getData().add(new XYChart.Data<>(xAxisDates.get(i), yAxisDeadline.get(i)));
		}

		lineChart.getData().add(series2);

		StackPane root = new StackPane();
		root.getChildren().addAll(barChart, lineChart);
		scene = new Scene(root, 800, 600);

		return scene;
	}
}
