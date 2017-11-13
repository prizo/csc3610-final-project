package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import application.TireShop;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;

public class AdminLoginController {
	
	BorderPane root = TireShop.getRoot();
	Connection connection = TireShop.getConnection();
	
	// Access user throughout application
	public static ResultSet user;
	
	@FXML
	private TextField txtID;
	
	@FXML
	private Button btnEnter;
	
	@FXML
	private Button btnBack;
	
	@FXML
	private void initialize() {
		
		txtID.setOnKeyPressed(e ->{

	            if (e.getCode() == (KeyCode.ENTER)) {
	                btnEnter.fire();
	            }
	        
	    });
		
		btnEnter.setOnAction(e -> {
			String id = txtID.getText();
			String query = "select * from employees where employeeID = " + id;
			
			try {
				Statement statement = connection.createStatement();
				
				user = statement.executeQuery(query);
				
				if (user.next()) {
					if (user.getBoolean("isAdmin")) {
						if (user.getString("password") == null) {
							try {
								StackPane pane = FXMLLoader.load(getClass().getResource
								  ("/views/AdminPasswordCreate.fxml"));
								root.setCenter(pane);
							} catch (IOException ex) {
								ex.printStackTrace();
							}
						}
						else {
							try {
								StackPane pane = FXMLLoader.load(getClass().getResource
								  ("/views/AdminPassword.fxml"));
								root.setCenter(pane);
							} catch (IOException ex) {
								ex.printStackTrace();
							}
						}
					}
					else {
						txtID.clear();
						Alert alert = new Alert(AlertType.ERROR, "Access Denied! You are not an Admin.");
						alert.showAndWait();
					}
				}
				else {
					txtID.clear();
					Alert alert = new Alert(AlertType.ERROR, "Invalid ID!");
					alert.showAndWait();
				}
			}
			catch (SQLException ex) {
				Alert alert = new Alert(AlertType.ERROR, "Invalid ID!");
				alert.showAndWait();
			}
		});
		
		btnBack.setOnAction(e -> {
			try {
				StackPane pane = FXMLLoader.load(getClass().getResource
				  ("/views/Dashboard.fxml"));
				root.setCenter(pane);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});
		
	}
	
	public static ResultSet getUser() {
		return user;
	}

}