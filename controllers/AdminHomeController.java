package controllers;

import java.io.IOException;
import application.TireShop;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class AdminHomeController {
	
	BorderPane root = TireShop.getRoot();
	
	@FXML
	private Button btnAdd;
	
	@FXML
	private Button btnView;
	
	@FXML
	private void initialize() {
		
		btnAdd.setOnAction(e -> {
			try {
				AnchorPane pane = FXMLLoader.load(getClass().getResource
				  ("/views/EmployeeAdd.fxml"));
				root.setCenter(pane);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});
		
		btnView.setOnAction(e -> {
			try {
				AnchorPane pane = FXMLLoader.load(getClass().getResource
				  ("/views/EmployeeView.fxml"));
				root.setCenter(pane);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});
		
	}

}
