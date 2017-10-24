package application;
	
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class Main extends Application {
	
	//private static Stage primaryStage;
	private static BorderPane root;
	private static Connection connection;
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		
		initializeDB();

		//Main.primaryStage = primaryStage;
		
		root = new BorderPane();
		
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/views/Login.fxml"));
		root.setCenter(pane);
		
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Tire Shop Application");
		primaryStage.show();
		
	}
	
	private void initializeDB() {

		try {
			// Load the JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver loaded");
			
			// Establish a connection
			connection = DriverManager.getConnection
					  ("jdbc:mysql://localhost/tiregroup", "root", "csc");
			System.out.println("Database connected");
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}

	//public static Stage getPrimaryStage() {
		//return Main.primaryStage;
	//}
	
	public static BorderPane getRoot() {
		return Main.root;
	}
	
	public static Connection getConnection() {
		return Main.connection;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
