import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
/**
 * This class implements the burndown chart.
 * @author zyud
 *
 */
public class BurndownChart extends Application {

	@Override
	public void start(Stage stage) {

		// create sample data to try - in the real case we'd be importing data from Company
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		TreeMap<LocalDate, Integer> taskHoursLog = new TreeMap<>();
		LocalDate day1 = LocalDate.parse("2017-12-16", dtf);
		LocalDate day2 = LocalDate.parse("2017-12-17", dtf);
		LocalDate day3 = LocalDate.parse("2017-12-18", dtf);
		LocalDate day4 = LocalDate.parse("2017-12-19", dtf);
		LocalDate day5 = LocalDate.parse("2017-12-20", dtf);
		LocalDate day6 = LocalDate.parse("2017-12-21", dtf);
		LocalDate day7 = LocalDate.parse("2017-12-22", dtf);
		LocalDate day8 = LocalDate.parse("2017-12-23", dtf);
		LocalDate day9 = LocalDate.parse("2017-12-24", dtf);
		LocalDate day10 = LocalDate.parse("2017-12-25", dtf);

		taskHoursLog.put(day1, 60);
		taskHoursLog.put(day2, 52);
		taskHoursLog.put(day3, 46);
		taskHoursLog.put(day4, 38);
		taskHoursLog.put(day5, 30);
		taskHoursLog.put(day6, 22);
		taskHoursLog.put(day7, 16);
		taskHoursLog.put(day8, 8);
		taskHoursLog.put(day9, 0);
		taskHoursLog.put(day10, 0);

		LocalDate deadline = LocalDate.parse("2017-12-27", dtf);

		// THE REAL PROGRAM STARTS HERE - implement data import

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

		// Plot charts
		stage.setTitle("Burndown Chart");

		// x-axis and y-axis  for both charts:
		final CategoryAxis xAxis = new CategoryAxis();
		xAxis.setLabel("Date");
		final NumberAxis yAxis = new NumberAxis();
		yAxis.setLabel("Task Hours Left");

		// first chart - task hours remaining bar chart
		final BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
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
		Scene scene = new Scene(root, 800, 600);
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}