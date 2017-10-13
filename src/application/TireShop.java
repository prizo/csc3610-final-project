package application;
	
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class TireShop extends Application {
	
	private static Stage primaryStage;
	private static BorderPane root;
	private static Statement statement;
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		
		initializeDB();

		TireShop.primaryStage = primaryStage;
		
		root = new BorderPane();
		
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/views/Login.fxml"));
		root.setCenter(pane);
		
		Scene scene = new Scene(root, 500, 300);
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
			Connection connection = DriverManager.getConnection
			  ("jdbc:mysql://localhost/", "", "");	// Add your own database credentials
			System.out.println("Database connected");
			
			// Create a statement
			statement = connection.createStatement();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}

	public static Stage getPrimaryStage() {
		return TireShop.primaryStage;
	}
	
	public static BorderPane getRoot() {
		return TireShop.root;
	}
	
	public static Statement getStatement() {
		return TireShop.statement;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
