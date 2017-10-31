package controllers;

import java.io.IOException;

import application.TireShop;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class DashboardController {

BorderPane root = TireShop.getRoot();
	
	@FXML
	private Button logInButton, adminButton, reportsButton, documentButton;
	
	@FXML
	private void initialize() {
		
		logInButton.setOnAction(e -> {
			try {
				AnchorPane pane = FXMLLoader.load(getClass().getResource
				  ("/views/Login.fxml"));
				root.setCenter(pane);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});
		
		reportsButton.setOnAction(e -> {
			try {
				AnchorPane pane = FXMLLoader.load(getClass().getResource
				  ("/views/EmployeeReport.fxml"));
				root.setCenter(pane);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});
		
		adminButton.setOnAction(e -> {
			try {
				AnchorPane pane = FXMLLoader.load(getClass().getResource
				  ("/views/AdminLogin.fxml"));
				root.setCenter(pane);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});
		
	}

}

