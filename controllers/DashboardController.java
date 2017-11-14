package controllers;

import java.io.IOException;

import application.TireShop;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;

public class DashboardController {

BorderPane root = TireShop.getRoot();
	
	@FXML
	private Button logInButton, adminButton, reportsButton, documentButton;
	
	@FXML
	private void initialize() {
		
/*		root.setOnKeyPressed(e ->{
			
			if(e.getCode() == KeyCode.DIGIT1) {
				logInButton.fire();
			}
			else if(e.getCode() == KeyCode.DIGIT2) {
				adminButton.fire();
			}
			else if(e.getCode() == KeyCode.DIGIT3) {
				reportsButton.fire();
			}
			else if(e.getCode() == KeyCode.DIGIT4) {
				documentButton.fire();
			}
			
		});
*/		
		logInButton.setOnAction(e -> {
			try {
				StackPane pane = FXMLLoader.load(getClass().getResource
				  ("/views/Login.fxml"));
				root.setCenter(pane);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});
		
		reportsButton.setOnAction(e -> {
			try {
				StackPane pane = FXMLLoader.load(getClass().getResource
				  ("/reports/EmployeeReport.fxml"));
				root.setCenter(pane);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});
		
		adminButton.setOnAction(e -> {
			try {
				StackPane pane = FXMLLoader.load(getClass().getResource
				  ("/views/AdminLogin.fxml"));
				root.setCenter(pane);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});
		
		documentButton.setOnAction(e -> {
			try {
				StackPane pane = FXMLLoader.load(getClass().getResource
				  ("/views/Documentation.fxml"));
				root.setCenter(pane);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});
		
	}

}

