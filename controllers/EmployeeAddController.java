package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import application.TireShop;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;

public class EmployeeAddController {
	
	BorderPane root = TireShop.getRoot();				// Used to load pages
	Connection connection = TireShop.getConnection();	// Used to query database
	
	@FXML
	private TextField txtFName;	// Hold first name
	
	@FXML
	private TextField txtLName;	// Hold last name
	
	@FXML
	private TextField txtDate;	// Hold hire date
	
	@FXML
	private TextField txtAdmin;	// Hold admin modifier
	
	@FXML
	private Button btnAdd;		// Button to add employee
	
	@FXML
	private Button btnBack;		// Button to go back
	
	@FXML
	private void initialize() {
		
		// Add employee to database
		btnAdd.setOnAction(e -> {
			// Prepare query
			String query = "insert into Employees (firstName, lastName, startDate, password, isAdmin) "
					+ "values(?, ?, ?, ?, ?)";
			
			// If admin field is "Y" or "y" set variable to 1; else set to 0
			byte isAdmin;
			if(txtAdmin.getText().equals("Y") || txtAdmin.getText().equals("y"))
				isAdmin = 1;
			else
				isAdmin = 0;
			
			// Execute prepared statement
			try {
				PreparedStatement preparedStmt = connection.prepareStatement(query);
				preparedStmt.setString(1, txtFName.getText());
				preparedStmt.setString(2, txtLName.getText());
				preparedStmt.setString(3, txtDate.getText());
				preparedStmt.setString(4, null);
				preparedStmt.setByte(5, isAdmin);
				preparedStmt.execute();
				
				// Clear fields
				txtFName.clear();
				txtLName.clear();
				txtDate.clear();
				txtAdmin.clear();
				
				// Alert user of success or failure
				Alert alert = new Alert(AlertType.INFORMATION, "Added employee successfully!");
				alert.showAndWait();
			} catch (SQLException e1) {
				Alert alert = new Alert(AlertType.ERROR, "Adding employee failed!");
				alert.showAndWait();
			}
		});
		
		// Send user back to admin home page
		btnBack.setOnAction(e -> {
			try {
				StackPane pane = FXMLLoader.load(getClass().getResource
				  ("/views/AdminHome.fxml"));
				root.setCenter(pane);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});
		
	}

}