package application;
	
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;

public class TireShop extends Application {
	
//	private static Stage primaryStage;
	private static BorderPane root;
	private static Connection connection;
	private static HostServices hostServices;
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		
		initializeDB();

//		TireShop.primaryStage = primaryStage;
		
		hostServices = getHostServices();
		
		root = new BorderPane();
		root.setStyle("-fx-background-color: black;");
		root.setMinSize(1024, 768);
		
		StackPane pane = FXMLLoader.load(getClass().getResource("/views/Dashboard.fxml"));
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
					  ("jdbc:mysql://localhost/tiregroup", "root", "csc4500");
			System.out.println("Database connected");
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}

//	public static Stage getPrimaryStage() {
//		return TireShop.primaryStage;
//	}
	
	public static BorderPane getRoot() {
		return root;
	}
	
	public static Connection getConnection() {
		return connection;
	}
	
	public static HostServices getHostService() {
		return hostServices;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
