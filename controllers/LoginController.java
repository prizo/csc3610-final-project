package controllers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import helperclasses.JDBCConnector;
import helperclasses.SceneSwitcher;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.control.Alert.AlertType;

public class LoginController {
	
	SceneSwitcher sceneSwitcher = new SceneSwitcher();
	Connection connection = new JDBCConnector().getConnection();
	
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
					if (user.getString("password") == null) {
						sceneSwitcher.switchScene(btnEnter, "/views/PasswordCreate.fxml");
					}
					else {
						sceneSwitcher.switchScene(btnEnter, "/views/PasswordLogin.fxml");
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
			sceneSwitcher.switchScene(btnBack, "/views/Dashboard.fxml");
		});
		
	}
	
	public static ResultSet getUser() {
		return user;
	}

}
