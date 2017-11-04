package controllers;

import java.io.IOException;
import application.TireShop;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;

public class AppointmentController {
	
BorderPane root = TireShop.getRoot();
	
	@FXML
	private Button btnBack;
	
	@FXML
	private void initialize() {
		
		btnBack.setOnAction(e -> {
			try {
				StackPane pane = FXMLLoader.load(getClass().getResource
				  ("/views/Home.fxml"));
				root.setCenter(pane);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});
		
	}

}
