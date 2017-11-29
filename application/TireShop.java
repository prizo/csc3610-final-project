package application;
	
import java.io.IOException;

import helperclasses.HostServicesProvider;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;

public class TireShop extends Application {
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		
		HostServicesProvider.INSTANCE.init(getHostServices());
		
		BorderPane root = new BorderPane();
		root.setStyle("-fx-background-color: black;");
		root.setMinSize(1024, 768);
		
		StackPane pane = FXMLLoader.load(getClass().getResource("/views/Dashboard.fxml"));
		root.setCenter(pane);
		
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Tire Shop Application");
		primaryStage.show();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
