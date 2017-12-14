/**
 * This main class loads the app.
 * @author cgui1
 *
 */
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class App extends Application{

	private Company company = Company.getInstance();
	
	public static void main(String[] args){
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// open company file if it exists
//		try {
//			FileInputStream fileIn = new FileInputStream("company.ser");
//			ObjectInputStream in = new ObjectInputStream(fileIn);
//			company.setCompany((Company) in.readObject());
//			in.close();
//			fileIn.close();
//		} catch (IOException i) {
//			System.out.println("IOException");
//			System.out.println(i.getMessage());
//		} catch (ClassNotFoundException c) {
//			System.out.println("Company class not found");
//			System.out.println(c.getMessage());
//		}
		
		
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
		
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				try {
			          FileOutputStream fileOut =
			          new FileOutputStream("company.ser");
			          ObjectOutputStream out = new ObjectOutputStream(fileOut);
			          out.writeObject(company);
			          out.close();
			          fileOut.close();
			       } catch (IOException i) {
			          System.out.println("IOException");
			          System.out.println(i.getMessage());
			       }
		        
				try {
					stop();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});


	}
	




}