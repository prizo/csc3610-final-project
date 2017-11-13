package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.codec.digest.DigestUtils;
import application.TireShop;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class AdminPasswordCreateController {
	
	BorderPane root = TireShop.getRoot();
	Connection connection = TireShop.getConnection();
	ResultSet user = AdminLoginController.getUser();
	
	@FXML
	private PasswordField txtPassword;
	
	@FXML
	private Button btnCreate;
	
	@FXML
	private Button btnBack;
	
	@FXML
	private void initialize() {
		txtPassword.setOnKeyPressed(e ->{

            if (e.getCode() == (KeyCode.ENTER)) {
                btnCreate.fire();
            }
        
		});
		btnCreate.setOnAction(e -> {
			String id = "";
			try {
				id = user.getString("employeeID");
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			
			String inputPassword = txtPassword.getText();
			Pattern passwordCheck = Pattern.compile	("((?=.*\\d)" + //Checks for a number
													"(?=.*[a-z])" + //Checks for a lowercase letter
													"(?=.*[A-Z])" + //Checks for a uppercase letter
													".{6,20})");    //Checks that the length is between 6-20
			Matcher match = passwordCheck.matcher(inputPassword);
			if(match.matches()) {
				// Hash input password
				String md5Hex = DigestUtils.md5Hex(inputPassword);					
				
				// Update password in database
				try {
					Statement statement = connection.createStatement();
					String insert = "update employees set password = '" + md5Hex + "' where employeeID = " + id;
					statement.executeUpdate(insert);
				
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
				
				try {
					StackPane pane = FXMLLoader.load(getClass().getResource
					  ("/views/AdminPage.fxml"));
					root.setCenter(pane);
					
					System.out.println("Admin " + user.getString("firstName") + " " + user.getString("lastName") +
							" logged in on " + new Date());
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			else {
				Alert alert = new Alert(AlertType.ERROR, 	"Password must be between 6-20 characters, contain a number," +
															" and an uppercase letter");
				alert.showAndWait();
			}
			
		});
		
		btnBack.setOnAction(e -> {
			try {
				StackPane pane = FXMLLoader.load(getClass().getResource
				  ("/views/AdminLogin.fxml"));
				root.setCenter(pane);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});
		
	}

}
