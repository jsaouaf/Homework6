/**
 * This main class loads the app.
 * @author cgui1
 *
 */
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application{

	public static void main(String[] args){
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		primaryStage.setTitle("Syme");

		if (Company.getInstance().getName()==null){
			Parent root = FXMLLoader.load(getClass().getResource("AddCompanyView.fxml"));
			Scene scene = new Scene(root);

			primaryStage.setScene(scene);
			primaryStage.show();

		} else {
			Parent root = FXMLLoader.load(getClass().getResource("DashboardView2.fxml"));

			Scene scene = new Scene(root);

			primaryStage.setScene(scene);
			primaryStage.show();
		}


	}
	




}