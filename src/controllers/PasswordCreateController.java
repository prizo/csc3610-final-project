package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.apache.commons.codec.digest.DigestUtils;
import application.TireShop;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class PasswordCreateController {
	
	BorderPane root = TireShop.getRoot();
	Connection connection = TireShop.getConnection();
	ResultSet user = LoginController.getUser();
	
	@FXML
	private PasswordField txtPassword;
	
	@FXML
	private Button btnCreate;
	
	@FXML
	private void initialize() {
		
		btnCreate.setOnAction(e -> {
			String id = "";
			try {
				id = user.getString(1);
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			
			String inputPassword = txtPassword.getText();
			// Hash input password
			String md5Hex = DigestUtils.md5Hex(inputPassword);
			
			
			// Update password in database
			String query = "update Employee set password = ? where employeeId = ?";
			
			try {
				PreparedStatement preparedStmt = connection.prepareStatement(query);
				preparedStmt.setString(1, md5Hex);
				preparedStmt.setString(2, id);
				
				// Execute prepared statement
				preparedStmt.executeUpdate();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			
			try {
				AnchorPane pane = FXMLLoader.load(getClass().getResource
				  ("/views/Home.fxml"));
				root.setCenter(pane);
				
				System.out.println("Employee " + user.getString(1) +
						" logged in on " + new Date());
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
		
	}

}
